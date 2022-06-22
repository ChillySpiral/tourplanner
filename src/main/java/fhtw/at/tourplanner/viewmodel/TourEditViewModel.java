package fhtw.at.tourplanner.viewmodel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourEditViewModel {
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();

    public TourEditViewModel(String title, String description, String from, String to){
        this.title.setValue(title);
        this.description.setValue(description);
        this.from.setValue(from);
        this.to.setValue(to);
    }

    public String getTitle() {
        return title.get();
    }
    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }
    public StringProperty descriptionProperty() {
        return description;
    }

    public String getFrom() {
        return from.get();
    }
    public StringProperty fromProperty() {
        return from;
    }

    public String getTo() {
        return to.get();
    }
    public StringProperty toProperty() {
        return to;
    }

    @Override
    public String toString(){
        return getTitle() + " " + getDescription() + " " + getFrom() + " " + getTo();
    }
}
