package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.viewmodel.LogEditViewModel;
import fhtw.at.tourplanner.viewmodel.TourEditViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;

//ToDo: Refactor
public class LogEditDialog extends Dialog<TourLog> {
    public LogEditDialog(Window owner, LogEditViewModel logEditViewModel){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fhtw/at/tourplanner/logEditDialog.fxml"));
        try {
            loader.setControllerFactory(controllerClass -> new LogEditController(logEditViewModel));
            DialogPane dialogPane = loader.load();
            initOwner(owner);
            initModality(Modality.APPLICATION_MODAL);
            setResizable(true);
            setTitle("Edit Log");
            setDialogPane(dialogPane);

            setResultConverter(buttonType -> {
                if(!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())){
                    return null;
                }else {
                    var result = new TourLog();
                    return result;
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
