package fhtw.at.tourplanner.BL;

import fhtw.at.tourplanner.BL.appManager.TourAppManager;
import fhtw.at.tourplanner.BL.appManager.implementation.TourAppManagerImpl;
import fhtw.at.tourplanner.BL.jsonGenerator.JsonGenerator;
import fhtw.at.tourplanner.BL.jsonGenerator.implementation.JsonGeneratorImpl;
import fhtw.at.tourplanner.BL.pdfGenerator.ReportGenerator;
import fhtw.at.tourplanner.BL.pdfGenerator.implementation.ReportGeneratorImpl;
import fhtw.at.tourplanner.Configuration.AppConfigurationLoader;
import fhtw.at.tourplanner.DAL.DalFactory;

public final class BLFactory {

    private static TourAppManager tourAM;
    private static ReportGenerator reportGenerator;
    private static JsonGenerator jsonGenerator;

    public static TourAppManager getTourAppManager(){
        if(tourAM == null){
            tourAM = new TourAppManagerImpl(getReportGenerator(), DalFactory.GetMapQuestRepository(), getJsonGenerator(), DalFactory.GetTourModelDao(), DalFactory.GetTourLogDao());
        }
        return tourAM;
    }

    public static ReportGenerator getReportGenerator(){
        if(reportGenerator == null){
            reportGenerator = new ReportGeneratorImpl(AppConfigurationLoader.getInstance().getAppConfiguration(), DalFactory.GetFileSystem());
        }
        return reportGenerator;
    }

    public static JsonGenerator getJsonGenerator(){
        if(jsonGenerator == null){
            jsonGenerator = new JsonGeneratorImpl();
        }
        return jsonGenerator;
    }
}
