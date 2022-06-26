package fhtw.at.tourplanner.BL.pdfGenerator.implementation;

import fhtw.at.tourplanner.BL.pdfGenerator.ReportGenerator;
import fhtw.at.tourplanner.DAL.DalFactory;
import fhtw.at.tourplanner.DAL.helper.ConfigurationLoader;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;

import java.time.LocalDateTime;

public class ReportGeneratorImpl implements ReportGenerator {
    @Override
    public boolean generateReport(TourModel tour, java.util.List<TourLog> logs) {
        try {

            PdfWriter writer = new PdfWriter(ConfigurationLoader.getConfig("PdfFolder") + "tourReport_" + tour.getTourId() + ".pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Paragraph tourTitle = new Paragraph(tour.getTitle())
                    .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                    .setFontSize(24)
                    .setBold()
                    .setFontColor(ColorConstants.BLACK);
            document.add(tourTitle);

            try {
                ImageData imageData = ImageDataFactory.create(ConfigurationLoader.getConfig("ImageFolder") + DalFactory.GetFileSystem().findFile(tour).aObject);
                document.add(new Image(imageData));
            }catch(NullPointerException e){
                e. printStackTrace();
                document.add(new Paragraph("<Image could not be found>").setItalic());
            }

            Paragraph listHeader = new Paragraph("Details")
                    .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                    .setFontSize(18)
                    .setBold()
                    .setFontColor(ColorConstants.BLACK);
            List list = new List()
                    .setSymbolIndent(12)
                    .setListSymbol("\u2022")
                    .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
            list.add(new ListItem("From: " + tour.getFrom()))
                    .add(new ListItem("To: " +  tour.getTo()))
                    .add(new ListItem("Distance: " + tour.getTourDistance() + "km"))
                    .add(new ListItem("Estimated Time: " + tour.getEstimatedTime())) //ToDo: Format
                    .add(new ListItem("Transport Type: " + tour.getTransportType()));
            document.add(listHeader);
            document.add(list);

            Paragraph descriptionHeader = new Paragraph("Description")
                    .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                    .setFontSize(18)
                    .setBold()
                    .setFontColor(ColorConstants.BLACK);
            document.add(descriptionHeader);
            document.add(new Paragraph(tour.getDescription()));

            document.add(new AreaBreak());

            Paragraph tableHeader = new Paragraph("Tour Logs")
                    .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                    .setFontSize(18)
                    .setBold()
                    .setFontColor(ColorConstants.BLACK);
            document.add(tableHeader);

            Table table = new Table(5);
            table.addHeaderCell(getHeaderCell("Date and Time"));
            table.addHeaderCell(getHeaderCell("Comment"));
            table.addHeaderCell(getHeaderCell("Difficulty"));
            table.addHeaderCell(getHeaderCell("Rating"));
            table.addHeaderCell(getHeaderCell("Total Time"));
            table.setFontSize(12).setBackgroundColor(ColorConstants.WHITE);
            for (var log: logs) {
                table.addCell(new Paragraph(log.getDateTime().toString()).setMaxWidth(100));
                table.addCell(new Paragraph(log.getComment()).setMaxWidth(150));
                table.addCell(new Paragraph(log.getDifficulty()).setMaxWidth(90));
                table.addCell(new Paragraph(log.getRating()).setMaxWidth(90));
                table.addCell(new Paragraph(log.getTotalTime().toString()).setMaxWidth(100));
            }
            document.add(table);

            document.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private static Cell getHeaderCell(String s) {
        return new Cell().add(new Paragraph(s)).setBold().setBackgroundColor(ColorConstants.GRAY);
    }
}
