package fhtw.at.tourplanner.BL;

import fhtw.at.tourplanner.BL.appManager.TourAppManager;
import fhtw.at.tourplanner.BL.appManager.implementation.TourAppManagerImpl;
import fhtw.at.tourplanner.BL.calculator.Calculator;
import fhtw.at.tourplanner.BL.calculator.implementation.CalculatorImpl;
import fhtw.at.tourplanner.BL.jsonGenerator.JsonGenerator;
import fhtw.at.tourplanner.BL.jsonGenerator.implementation.JsonGeneratorImpl;
import fhtw.at.tourplanner.BL.pdfGenerator.ReportGenerator;
import fhtw.at.tourplanner.BL.pdfGenerator.implementation.ReportGeneratorImpl;
import fhtw.at.tourplanner.BL.searchHelper.SearchHelper;
import fhtw.at.tourplanner.BL.searchHelper.implementation.SearchHelperImpl;
import fhtw.at.tourplanner.Configuration.AppConfigurationLoader;
import fhtw.at.tourplanner.DAL.DalFactory;

public final class BLFactory {

    private static TourAppManager tourAM;
    private static ReportGenerator reportGenerator;
    private static JsonGenerator jsonGenerator;
    private static SearchHelper searchHelper;

    private static Calculator calculator;

    public static TourAppManager getTourAppManager(){
        if(tourAM == null){
            tourAM = new TourAppManagerImpl(getReportGenerator(), DalFactory.GetMapQuestRepository(), getJsonGenerator(), DalFactory.GetTourModelDao(), DalFactory.GetTourLogDao(), getSearchHelper(), getCalculator());
        }
        return tourAM;
    }

    public static ReportGenerator getReportGenerator(){
        if(reportGenerator == null){
            reportGenerator = new ReportGeneratorImpl(AppConfigurationLoader.getInstance().getAppConfiguration(), DalFactory.GetFileSystem(), getCalculator());
        }
        return reportGenerator;
    }

    public static JsonGenerator getJsonGenerator(){
        if(jsonGenerator == null){
            jsonGenerator = new JsonGeneratorImpl();
        }
        return jsonGenerator;
    }

    public static SearchHelper getSearchHelper(){
        if(searchHelper == null){
            searchHelper = new SearchHelperImpl();
        }
        return searchHelper;
    }

    public static Calculator getCalculator(){
        if(calculator == null){
            calculator = new CalculatorImpl();
        }
        return calculator;
    }
}
