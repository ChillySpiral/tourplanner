package fhtw.at.tourplanner.BL;

import fhtw.at.tourplanner.DAL.DataLogicFactory;
import fhtw.at.tourplanner.model.TourModel;

import java.util.List;

public class TourBusinessLogicImplementation implements TourBusinessLogic{
    @Override
    public List<TourModel> GetAllTours() {
        var dlFactory =  DataLogicFactory.GetFactoryDL();

        return dlFactory.GetAllTours();
    }
}
