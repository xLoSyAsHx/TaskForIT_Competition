package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ChartSettingsController {

    private Stage dialogStage;
    private Settings settings;
    private boolean saveChanges = false;

    @FXML
    private RadioButton rb_Temperature;
    @FXML
    private RadioButton rb_Humidity;
    @FXML
    private RadioButton rb_Illumination;
    @FXML
    private RadioButton rb_Concentration_of_CO2;


    @FXML
    private MenuButton mb_ChangeRegion;
    @FXML
    private MenuButton mb_ChangeControllerType;
    @FXML
    private MenuButton mb_ChangeControllerLocation;
    @FXML
    private MenuButton mb_From;
    @FXML
    private MenuButton mb_To;


    @FXML
    private void initialize() {
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }


    public boolean isApplyChangesClicked() {
        return saveChanges;
    }

    public Settings getSettings(){
        return settings;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке OK.
     */
    @FXML
    private void handleApplyChanges() {
            saveChanges = true;
            dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}