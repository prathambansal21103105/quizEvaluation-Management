package com.quiz.layoutPDF.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.quiz.layoutPDF.Repository.QuestionImageRepository;
import com.quiz.layoutPDF.models.PdfRequest;
import com.quiz.layoutPDF.models.QuestionImage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;
import com.quiz.layoutPDF.models.Quiz;
import com.quiz.layoutPDF.models.Question;
import javax.imageio.ImageIO;
import java.util.List;

@Service
public class PdfService {

    private final QuestionImageRepository questionImageRepository;
    public PdfService(QuestionImageRepository questionImageRepository) {
        this.questionImageRepository = questionImageRepository;
    }

    public byte[] generateQuizPdf(Quiz quiz) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("Quiz " + quiz.getId() + ": " + quiz.getTitle(), titleFont));

            PdfPTable courseTable = new PdfPTable(2);
            courseTable.setWidthPercentage(100);
            courseTable.setWidths(new int[]{78, 22});
            courseTable.addCell(getCell("Course: " + quiz.getCourse() + " (" + quiz.getCourseCode() + ")", subTitleFont, Rectangle.NO_BORDER));
            courseTable.addCell(getCell("Max Marks: " + quiz.getMaxMarks(), subTitleFont, Rectangle.NO_BORDER));
            document.add(courseTable);

            addNameSid(document, subTitleFont);

            document.add(new LineSeparator());
            document.add(new Paragraph("\n"));

            Font questionFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font optionFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            Font marksFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            int questionNumber = 1;
            List<Question> sortedQuestions = quiz.getQuestions()
                    .stream()
                    .sorted(Comparator.comparingLong(Question::getQuestionNum))
                    .toList();
            for (Question question : sortedQuestions) {

                checkAndAddNewPage(document, writer, subTitleFont);

                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);
                table.setWidths(new int[]{5, 80, 15});
                table.setSpacingBefore(8f);

                PdfPCell questionNumberCell = new PdfPCell(new Phrase(questionNumber + ".", questionFont));
                questionNumberCell.setBorder(Rectangle.NO_BORDER);
                questionNumberCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
                questionNumberCell.setPaddingTop(5f);
                table.addCell(questionNumberCell);

                PdfPCell questionTextCell = new PdfPCell();
                questionTextCell.setBorder(Rectangle.NO_BORDER);
                questionTextCell.setPaddingTop(-3f);
                questionTextCell.addElement(new Paragraph(question.getQuestion(), questionFont));

                if (question.getImageId() != null && !question.getImageId().isEmpty()) {
                    BufferedImage bufferedImage = fetchImage(question.getImageId());
                    if (bufferedImage != null) {
                        try {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(bufferedImage, "png", baos);
                            byte[] imageBytes = baos.toByteArray();

                            Image questionImage = Image.getInstance(imageBytes);
                            questionImage.scaleToFit(200, 200);
                            questionTextCell.addElement(questionImage);
                        } catch (Exception e) {
                            throw new RuntimeException("Error processing image for PDF", e);
                        }
                    }
                }

                int optionNumber = 1;
                for (String option : question.getOptions()) {
                    questionTextCell.addElement(new Paragraph("   " + optionNumber + ". " + option, optionFont));
                    optionNumber++;
                }
                table.addCell(questionTextCell);

                PdfPTable marksAndBoxTable = new PdfPTable(1);
                marksAndBoxTable.setWidthPercentage(100);

                PdfPCell marksCell = new PdfPCell(new Phrase("Marks: " + question.getMarks(), marksFont));
                marksCell.setBorder(Rectangle.NO_BORDER);
                marksCell.setPaddingBottom(5f);
                marksAndBoxTable.addCell(marksCell);

                PdfPCell answerBoxCell = new PdfPCell();
                answerBoxCell.setFixedHeight(25f);
                marksAndBoxTable.addCell(answerBoxCell);

                PdfPCell combinedCell = new PdfPCell();
                combinedCell.addElement(marksAndBoxTable);
                combinedCell.setBorder(Rectangle.NO_BORDER);
                combinedCell.setPaddingLeft(5f);
                table.addCell(combinedCell);

                document.add(table);
                document.add(new Paragraph("\n"));

                questionNumber++;
            }

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    private BufferedImage fetchImage(String imageId) {
        Optional<QuestionImage> image = questionImageRepository.findById(imageId);
        if (image.isPresent()) {
            QuestionImage questionImage = image.get();
//            System.out.println(questionImage);
            byte[] imageData = questionImage.getImageData();
            try {
                return ImageIO.read(new ByteArrayInputStream(imageData));
            } catch (IOException e) {
                throw new RuntimeException("Error reading image data", e);
            }
        } else {
            throw new EntityNotFoundException("Image not found for ID: " + imageId);
        }
    }

    private void checkAndAddNewPage(Document document, PdfWriter writer, Font subTitleFont) throws Exception {
        float yPosition = writer.getVerticalPosition(true);

        if (yPosition < 100) {
            document.newPage();
            addNameSid(document, subTitleFont);
        }
    }

    private void addNameSid(Document document, Font subTitleFont) throws DocumentException {
        PdfPTable nameSidTable = new PdfPTable(4);
        nameSidTable.setWidthPercentage(100);
        nameSidTable.setWidths(new int[]{10, 30, 8, 52});

        PdfPCell nameCell = getCell("Name: ", subTitleFont, Rectangle.NO_BORDER);
        PdfPCell nameBoxCell = getInvisibleCell();
        PdfPCell sidCell = getCell("SID: ", subTitleFont, Rectangle.NO_BORDER);

        PdfPTable sidBoxTable = new PdfPTable(15);
        sidBoxTable.setWidthPercentage(100);
        float[] boxWidths = {25f, 8f, 25f, 8f, 25f, 8f, 25f, 8f, 25f, 8f, 25f, 8f, 25f, 8f, 25f};
        sidBoxTable.setWidths(boxWidths);

        for (int i = 0; i < 8; i++) {
            PdfPCell smallBox = new PdfPCell();
            smallBox.setFixedHeight(25f);
            smallBox.setBorder(Rectangle.BOX);
            sidBoxTable.addCell(smallBox);

            if (i < 7) {
                PdfPCell spacer = new PdfPCell();
                spacer.setBorder(Rectangle.NO_BORDER);
                sidBoxTable.addCell(spacer);
            }
        }

        PdfPCell sidBoxCell = new PdfPCell();
        sidBoxCell.addElement(sidBoxTable);
        sidBoxCell.setBorder(Rectangle.NO_BORDER);

        nameSidTable.addCell(nameCell);
        nameSidTable.addCell(nameBoxCell);
        nameSidTable.addCell(sidCell);
        nameSidTable.addCell(sidBoxCell);

        document.add(nameSidTable);
        document.add(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 5)));
        document.add(new LineSeparator());
    }

    private PdfPCell getInvisibleCell() {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell getCell(String text, Font font, int border) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(border);
        return cell;
    }

    public byte[] generateCustomQuizPdf(Quiz quiz, PdfRequest format) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font questionFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font optionFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            Font marksFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            // Title and quiz details
            document.add(new Paragraph("Quiz " + quiz.getId() + ": " + quiz.getTitle(), titleFont));

            PdfPTable courseTable = new PdfPTable(2);
            courseTable.setWidthPercentage(100);
            courseTable.setWidths(new int[]{78, 22});
            courseTable.addCell(getCell("Course: " + quiz.getCourse() + " (" + quiz.getCourseCode() + ")", subTitleFont, Rectangle.NO_BORDER));
            courseTable.addCell(getCell("Max Marks: " + quiz.getMaxMarks(), subTitleFont, Rectangle.NO_BORDER));
            document.add(courseTable);

            addNameSid(document, subTitleFont);

            document.add(new LineSeparator());
            document.add(new Paragraph("\n"));

            List<Question> sortedQuestions = quiz.getQuestions().stream()
                    .sorted(Comparator.comparingLong(Question::getQuestionNum))
                    .toList();

            int questionIndex = 0;
            int questionNumber = 1;
            for (int pageSize : format.getQuestionsPerPage()) {
                if (questionIndex >= sortedQuestions.size()) break;

                if (questionIndex > 0) {
                    document.newPage();
                    addNameSid(document, subTitleFont);
                    document.add(new Paragraph("\n"));
                }

                for (int i = 0; i < pageSize && questionIndex < sortedQuestions.size(); i++) {
                    Question question = sortedQuestions.get(questionIndex++);

                    PdfPTable table = new PdfPTable(3);
                    table.setWidthPercentage(100);
                    table.setWidths(new int[]{5, 80, 15});
                    table.setSpacingBefore(8f);

                    PdfPCell questionNumberCell = new PdfPCell(new Phrase(questionNumber + ".", questionFont));
                    questionNumberCell.setBorder(Rectangle.NO_BORDER);
                    questionNumberCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
                    questionNumberCell.setPaddingTop(5f);
                    table.addCell(questionNumberCell);

                    PdfPCell questionTextCell = new PdfPCell();
                    questionTextCell.setBorder(Rectangle.NO_BORDER);
                    questionTextCell.setPaddingTop(-3f);
                    questionTextCell.addElement(new Paragraph(question.getQuestion(), questionFont));

                    if (question.getImageId() != null && !question.getImageId().isEmpty()) {
                        BufferedImage bufferedImage = fetchImage(question.getImageId());
                        if (bufferedImage != null) {
                            try {
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                ImageIO.write(bufferedImage, "png", baos);
                                byte[] imageBytes = baos.toByteArray();

                                Image questionImage = Image.getInstance(imageBytes);
                                questionImage.scaleToFit(200, 200);
                                questionTextCell.addElement(questionImage);
                            } catch (Exception e) {
                                throw new RuntimeException("Error processing image for PDF", e);
                            }
                        }
                    }

                    int optionNumber = 1;
                    for (String option : question.getOptions()) {
                        questionTextCell.addElement(new Paragraph("   " + optionNumber + ". " + option, optionFont));
                        optionNumber++;
                    }
                    table.addCell(questionTextCell);

                    PdfPTable marksAndBoxTable = new PdfPTable(1);
                    marksAndBoxTable.setWidthPercentage(100);

                    PdfPCell marksCell = new PdfPCell(new Phrase("Marks: " + question.getMarks(), marksFont));
                    marksCell.setBorder(Rectangle.NO_BORDER);
                    marksCell.setPaddingBottom(5f);
                    marksAndBoxTable.addCell(marksCell);

                    PdfPCell answerBoxCell = new PdfPCell();
                    answerBoxCell.setFixedHeight(25f);
                    marksAndBoxTable.addCell(answerBoxCell);

                    PdfPCell combinedCell = new PdfPCell();
                    combinedCell.addElement(marksAndBoxTable);
                    combinedCell.setBorder(Rectangle.NO_BORDER);
                    combinedCell.setPaddingLeft(5f);
                    table.addCell(combinedCell);

                    document.add(table);
                    document.add(new Paragraph("\n"));

                    questionNumber++;
                }
            }

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating custom PDF", e);
        }
    }
}
