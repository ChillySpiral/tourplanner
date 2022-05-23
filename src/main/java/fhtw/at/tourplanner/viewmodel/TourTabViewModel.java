package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.model.TourModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourTabViewModel {

    private TourModel data;
    private volatile boolean isInitialValue = false;

    private final StringProperty title = new SimpleStringProperty();

    public TourTabViewModel(){
        title.addListener( (arg, oldVal, newVal)->updateTourModel());
    }

    public String getTitle(){
        return title.get();
    }
    public StringProperty titleProperty(){
        return title;
    }

    public void setTourModel(TourModel selectedItem){
        isInitialValue = true;
        if(selectedItem == null){
            title.set("New Tour");
            return;
        }

        this.data = selectedItem;
        title.setValue(selectedItem.getTitle());
        isInitialValue = false;
    }

    public void updateTourModel(){
        data.setTitle(this.getTitle());
    }
}
