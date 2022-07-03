package fhtw.at.tourplanner.BL.pdfGenerator.implementation;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import fhtw.at.tourplanner.BL.pdfGenerator.ReportGenerator;
import fhtw.at.tourplanner.BL.pdfGenerator.helper.Calculator;
import fhtw.at.tourplanner.Configuration.AppConfiguration;
import fhtw.at.tourplanner.DAL.DalFactory;
import fhtw.at.tourplanner.DAL.FileSystem.FileSystem;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.fileSystem.Pair;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Log4j2
public class ReportGeneratorImpl implements ReportGenerator {

    private final AppConfiguration appConfiguration;
    private final FileSystem fileSystem;
    public ReportGeneratorImpl(AppConfiguration appConfiguration, FileSystem fileSystem){
        this.appConfiguration = appConfiguration;
        this.fileSystem = fileSystem;
    }

    @Override
    public boolean generateReport(TourModel tour, java.util.List<TourLog> logs, File pdfFile) {
        try {

            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Paragraph tourTitle = new Paragraph(tour.getTitle())
                    .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                    .setFontSize(24)
                    .setBold()
                    .setFontColor(ColorConstants.BLACK);
            document.add(tourTitle);

            try {
                ImageData imageData = ImageDataFactory.create(appConfiguration.getImageFolder() + fileSystem.findFile(tour));
                document.add(new Image(imageData));
            }catch(NullPointerException e){
                log.warn("Generate Report failed because image could not b found. [ error: " + e + " ]"); // TODO: ok?
                e. printStackTrace();
                document.add(new Paragraph("Image could not be found").setItalic());
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
                    .add(new ListItem("Distance: " + String.format("%.1f", tour.getTourDistance()) + "km"))
                    .add(new ListItem("Estimated Time: " + tour.getEstimatedTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))) //ToDo: Format
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
                table.addCell(new Paragraph(log.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).setMaxWidth(100));
                table.addCell(new Paragraph(log.getComment()).setMaxWidth(150));
                table.addCell(new Paragraph(String.valueOf(log.getDifficulty())).setMaxWidth(90));
                table.addCell(new Paragraph(String.valueOf(log.getRating())).setMaxWidth(90));
                table.addCell(new Paragraph(log.getTotalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).setMaxWidth(100));
            }
            document.add(table);

            document.close();
            return true;
        }catch(Exception e){
            log.warn("Generate Report failed because ???. [ error: " + e + " ]"); // TODO: Add reason
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean generateSummary(java.util.List<Pair<TourModel, java.util.List<TourLog>>> allTours, File pdfFile) {
        try {
            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Paragraph tourTitle = new Paragraph("Summary Report " +  LocalDate.now())
                    .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                    .setFontSize(24)
                    .setBold()
                    .setFontColor(ColorConstants.BLACK);
            document.add(tourTitle);

            Table table = new Table(4);
            table.addHeaderCell(getHeaderCell("Tour"));
            table.addHeaderCell(getHeaderCell("Average Time"));
            table.addHeaderCell(getHeaderCell("Average Difficulty"));
            table.addHeaderCell(getHeaderCell("Average Rating"));
            table.setFontSize(12).setBackgroundColor(ColorConstants.WHITE);

            for (var tour: allTours) {
                table.addCell(new Paragraph(tour.aObject.getTitle()).setMaxWidth(100));
                var avgTime = Calculator.calculateAverageTime(tour.bObject.stream().map(TourLog::getTotalTime).collect(Collectors.toList()));
                if(avgTime != null){
                    table.addCell(new Paragraph(avgTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))).setMaxWidth(150));
                } else {
                    table.addCell(new Paragraph("Not enough data").setMaxWidth(150));
                }
                var avgDifficulty = Calculator.calculateAverageDifficulty(tour.bObject.stream().map(TourLog::getDifficulty).collect(Collectors.toList()));
                if(avgDifficulty != null){
                    table.addCell(new Paragraph(avgDifficulty.toString()).setMaxWidth(150));
                } else {
                    table.addCell(new Paragraph("Not enough data").setMaxWidth(150));
                }
                var avgRating = Calculator.calculateAverageRating(tour.bObject.stream().map(TourLog::getRating).collect(Collectors.toList()));
                if(avgRating != null){
                    table.addCell(new Paragraph(avgRating.toString()).setMaxWidth(150));
                } else {
                    table.addCell(new Paragraph("Not enough data").setMaxWidth(150));
                }
            }
            document.add(table);
            document.close();
            return true;

        }catch(Exception e){
            log.warn("Generate Summary failed because ???. [ error: " + e + " ]"); // TODO: Add reason
            e.printStackTrace();
            return false;
        }
    }

    private static Cell getHeaderCell(String s) {
        return new Cell().add(new Paragraph(s)).setBold().setBackgroundColor(ColorConstants.GRAY);
    }
}
