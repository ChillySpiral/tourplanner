package fhtw.at.tourplanner.PL.view.dialog;

import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.PL.viewmodel.dialog.TourEditViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Window;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Objects;
//ToDo: Refactor
@Log4j2
public class TourEditDialog extends Dialog<TourModel> {
    public TourEditDialog(Window owner, TourEditViewModel tourEditViewModel){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fhtw/at/tourplanner/tourEditDialog.fxml"));
        try {
            loader.setControllerFactory(controllerClass -> new TourEditController(tourEditViewModel));
            DialogPane dialogPane = loader.load();
            initOwner(owner);
            initModality(Modality.APPLICATION_MODAL);
            setResizable(true);
            setTitle("Edit Tour");
            setDialogPane(dialogPane);

            setResultConverter(buttonType -> {
                if(!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())){
                    return null;
                }else {
                    var result = new TourModel();
                    return result;
                }
            });

        } catch (IOException e) {
            log.error("Tour Edit Dialog failed. [ error: " + e.getMessage() + " ]");
        }
    }
}
