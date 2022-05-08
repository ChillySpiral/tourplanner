package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.listener.BindListener;
import fhtw.at.tourplanner.model.TourModel;

import java.util.ArrayList;
import java.util.List;

public class TourTabViewModel {

    public TourModel data = new TourModel("", "");
    private List<BindListener> reBindListenerList = new ArrayList<BindListener>();

    public void setTourModel(TourModel selectedItem){
        for (var listener: this.reBindListenerList) {
            listener.requestUnBind();
        }

        data = selectedItem;

        for (var listener: this.reBindListenerList) {
            listener.requestReBind();
        }
    }

    public void addListener(BindListener listener) {
        this.reBindListenerList.add(listener);
    }
}
