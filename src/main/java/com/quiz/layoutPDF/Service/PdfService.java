package com.quiz.layoutPDF.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Service;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import com.quiz.layoutPDF.models.Quiz;
import com.quiz.layoutPDF.models.Question;

@Service
public class PdfService {

    public byte[] generateQuizPdf(Quiz quiz) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // Add Quiz Title
            document.add(new Paragraph("Quiz " + quiz.getId() + ": " + quiz.getTitle(), titleFont));

            // Course and Max Marks Table
            PdfPTable courseTable = new PdfPTable(2);
            courseTable.setWidthPercentage(100);
            courseTable.setWidths(new int[]{78, 22});

            courseTable.addCell(getCell("Course: " + quiz.getCourse() + " (" + quiz.getCourseCode() + ")", subTitleFont, Rectangle.NO_BORDER));
            courseTable.addCell(getCell("Max Marks: " + quiz.getMaxMarks(), subTitleFont, Rectangle.NO_BORDER));
            document.add(courseTable);

            // Add Name and SID on the first page
            addNameSid(document, subTitleFont);

            // Line Separator
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setOffset(-5);
            document.add(lineSeparator);

            document.add(new Paragraph("\n")); // Space before questions

            Font questionFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font optionFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            Font marksFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            int questionNumber = 1;
            for (Question question : quiz.getQuestions()) {
                // Before adding new content, check if we need to add a new page
                if (document.getPageNumber() > 1) {
                    // If we're on a new page, manually add "Name" and "SID"
                    addNameSid(document, subTitleFont);
                }

                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);
                table.setWidths(new int[]{5, 80, 15});
                table.setSpacingBefore(10f);

                PdfPCell questionNumberCell = new PdfPCell(new Phrase(questionNumber + ".", questionFont));
                questionNumberCell.setBorder(Rectangle.NO_BORDER);
                questionNumberCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
                questionNumberCell.setPaddingTop(5f);
                table.addCell(questionNumberCell);

                PdfPCell questionTextCell = new PdfPCell();
                questionTextCell.setBorder(Rectangle.NO_BORDER);
                questionTextCell.setPaddingTop(-3f);
                questionTextCell.addElement(new Paragraph(question.getQuestion(), questionFont));

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

                // Add page break if content overflows
                if (document.getPageNumber() > 1) {
                    document.newPage();
                }

                questionNumber++;
            }

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    private void addNameSid(Document document, Font subTitleFont) throws Exception {
        // Name and SID in One Row with Aligned Boxes
        PdfPTable nameSidTable = new PdfPTable(4);
        nameSidTable.setWidthPercentage(100);
        nameSidTable.setWidths(new int[]{10, 68, 8, 14});

        PdfPCell nameCell = getCell("Name: ", subTitleFont, Rectangle.NO_BORDER); // No Border for Name Label
        PdfPCell nameBoxCell = getInvisibleBoxCell(); // No Border for Name Box
        PdfPCell sidCell = getCell("SID: ", subTitleFont, Rectangle.NO_BORDER);
        PdfPCell sidBoxCell = getAnswerBoxCell(); // Box with Border for SID

        nameSidTable.addCell(nameCell);
        nameSidTable.addCell(nameBoxCell);
        nameSidTable.addCell(sidCell);
        nameSidTable.addCell(sidBoxCell);
        document.add(nameSidTable);
    }

    // Helper Method to Create Text Cells
    private PdfPCell getCell(String text, Font font, int border) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(border);
        return cell;
    }

    // Helper Method to Create Rectangular Box with Border
    private PdfPCell getAnswerBoxCell() {
        PdfPCell boxCell = new PdfPCell();
        boxCell.setFixedHeight(25f);
        boxCell.setMinimumHeight(25f);
        return boxCell;
    }

    // Helper Method to Create Invisible Rectangular Box
    private PdfPCell getInvisibleBoxCell() {
        PdfPCell boxCell = new PdfPCell();
        boxCell.setFixedHeight(25f);
        boxCell.setMinimumHeight(25f);
        boxCell.setBorder(Rectangle.NO_BORDER); // Make it invisible
        return boxCell;
    }
}
