package com.quiz.layoutPDF.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Service;
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

            document.add(new LineSeparator());
            document.add(new Paragraph("\n")); // Space before questions

            Font questionFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font optionFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            Font marksFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            int questionNumber = 1;
            for (Question question : quiz.getQuestions()) {

                // Check if a new page is needed before adding a question
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

    /**
     * Ensures "Name" and "SID" are added when a new page starts.
     */
    private void checkAndAddNewPage(Document document, PdfWriter writer, Font subTitleFont) throws Exception {
        float yPosition = writer.getVerticalPosition(true); // Get current Y position

        if (yPosition < 100) { // If less than 100 units left, move to a new page
            document.newPage();
            addNameSid(document, subTitleFont);
        }
    }

    /**
     * Adds "Name" and "SID" with 8 SID boxes.
     */
    private void addNameSid(Document document, Font subTitleFont) throws DocumentException {
        PdfPTable nameSidTable = new PdfPTable(4);
        nameSidTable.setWidthPercentage(100);
        nameSidTable.setWidths(new int[]{10, 30, 8, 52}); // Keeps SID section properly aligned

        PdfPCell nameCell = getCell("Name: ", subTitleFont, Rectangle.NO_BORDER);
        PdfPCell nameBoxCell = getInvisibleCell(); // Invisible name box
        PdfPCell sidCell = getCell("SID: ", subTitleFont, Rectangle.NO_BORDER);

        // SID Box with 8 equal-sized squares and spacing
        PdfPTable sidBoxTable = new PdfPTable(15); // 8 boxes + 7 spacers
        sidBoxTable.setWidthPercentage(100);
        float[] boxWidths = {25f, 8f, 25f, 8f, 25f, 8f, 25f, 8f, 25f, 8f, 25f, 8f, 25f, 8f, 25f}; // Adding spacers
        sidBoxTable.setWidths(boxWidths);

        for (int i = 0; i < 8; i++) {
            PdfPCell smallBox = new PdfPCell();
            smallBox.setFixedHeight(25f);
            smallBox.setBorder(Rectangle.BOX);
            sidBoxTable.addCell(smallBox);

            if (i < 7) { // Add a spacer after each box except the last one
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



    /**
     * Returns an invisible table cell (used for Name box).
     */
    private PdfPCell getInvisibleCell() {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER); // No border, completely invisible
        return cell;
    }


    // Helper method to create text cells
    private PdfPCell getCell(String text, Font font, int border) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(border);
        return cell;
    }

    // Helper method to create a blank box for Name
    private PdfPCell getInvisibleBoxCell() {
        PdfPCell boxCell = new PdfPCell();
        boxCell.setFixedHeight(20f);
        boxCell.setMinimumHeight(20f);
        boxCell.setBorder(Rectangle.BOX);
        return boxCell;
    }
}
