package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;


public class ChartSettingsController {

    private Stage dialogStage;
    private boolean saveChanges = false;
    final ToggleGroup radioButtonGroup = new ToggleGroup();
    String currentControllerType = null;
    Integer controllerLocation;

    HashMap<LocalDate, Double> dateVal = null;
    Settings settings = null;


    @FXML
    private RadioButton rb_Temperature;
    @FXML
    private RadioButton rb_Humidity;
    @FXML
    private RadioButton rb_Illumination;
    @FXML
    private RadioButton rb_Concentration_of_CO2;


    @FXML
    private MenuButton mb_ChangeControllerLocation;
    @FXML
    private MenuButton mb_IndoorOutdoor;

    @FXML
    private Text t_region;
    @FXML
    private Text t_city;
    @FXML
    private Text t_district;

    @FXML
    private DatePicker dp_From;
    @FXML
    private DatePicker dp_To;

    @FXML
    private TextField tf_f_hr;
    @FXML
    private TextField tf_f_min;
    @FXML
    private TextField tf_f_sec;
    @FXML
    private TextField tf_t_hr;
    @FXML
    private TextField tf_t_min;
    @FXML
    private TextField tf_t_sec;

    @FXML
    private Button b_Apply;


    @FXML
    private void initialize() {

    }

    private void initMenuItems(){
        ObservableList<MenuItem> list = mb_ChangeControllerLocation.getItems();
        for (MenuItem item : list) {
            item.setOnAction(e -> {
                        controllerLocation = mb_ChangeControllerLocation.getItems().indexOf(item);
                        t_region.setDisable(false);
                        t_city.setDisable(false);
                        t_district.setDisable(false);
                        t_region.setText(ControllerLocations.getRegion(controllerLocation));
                        t_city.setText(ControllerLocations.getCity(controllerLocation));
                        t_district.setText(ControllerLocations.getDistrict(controllerLocation));

                        mb_IndoorOutdoor.setDisable(false);
                    }
            );
        }

        list = mb_IndoorOutdoor.getItems();
        for (MenuItem item : list) {
            item.setOnAction(e -> {
                            mb_IndoorOutdoor.setText("Controller type: " + item.getText());
                    }
            );
        }
    }

    private void disableAllAboveType(){
        mb_ChangeControllerLocation.setDisable(true);
        mb_IndoorOutdoor.setDisable(true);
        t_region.setDisable(true);
        t_city.setDisable(true);
        t_district.setDisable(true);
        dp_From.setDisable(true);
        dp_To.setDisable(true);
        tf_f_hr.setDisable(true);
        tf_f_min.setDisable(true);
        tf_f_sec.setDisable(true);
        tf_t_hr.setDisable(true);
        tf_t_min.setDisable(true);
        tf_t_sec.setDisable(true);
        b_Apply.setDisable(true);
    }

    private void enableAll(){
        t_region.setDisable(false);
        t_city.setDisable(false);
        t_district.setDisable(false);
        tf_f_hr.setDisable(false);
        tf_f_min.setDisable(false);
        tf_f_sec.setDisable(false);
        tf_t_hr.setDisable(false);
        tf_t_min.setDisable(false);
        tf_t_sec.setDisable(false);
    }


    @FXML
    private void handleApplyChanges() {
        saveChanges = true;

        Character kind = null;
        String settingsKind = null;

        Integer indoorOutdoor;
        LocalDateTime from = null, to = null;

        int f_hr;
        int f_min;
        int f_sec;
        int t_hr;
        int t_min;
        int t_sec;


        switch (((RadioButton)(radioButtonGroup.getSelectedToggle())).getText())
        {
            case "Temperature": kind = 't'; settingsKind = "Temperature"; break;
            case "Humidity": kind = 'h'; settingsKind = "Humidity"; break;
            case "Illumination": kind = 'i'; settingsKind = "Illumination"; break;
            case "Concentration of CO2": kind = 'c'; settingsKind = "Concentration of CO2"; break;
        }

        indoorOutdoor = mb_IndoorOutdoor.getText().equals("Indoors") ? 1 : 0;


        try {
            f_hr = Integer.parseInt(!tf_f_hr.getText().isEmpty() ? tf_f_hr.getText() : "0");
            f_min = Integer.parseInt(!tf_f_min.getText().isEmpty() ? tf_f_min.getText() : "0");
            f_sec = Integer.parseInt(!tf_f_sec.getText().isEmpty() ? tf_f_sec.getText() : "0");
            t_hr = Integer.parseInt(!tf_t_hr.getText().isEmpty() ? tf_t_hr.getText() : "0");
            t_min = Integer.parseInt(!tf_t_min.getText().isEmpty() ? tf_t_min.getText() : "0");
            t_sec = Integer.parseInt(!tf_t_sec.getText().isEmpty() ? tf_t_sec.getText() : "0");

        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong time (hr, min, sec)");
            alert.setContentText("Please, check correctness of your input and try again.");
            alert.showAndWait();
            saveChanges = false;
            return;
        }
            LocalTime time = LocalTime.of(f_hr, f_min, f_sec);
            from = LocalDateTime.of(dp_From.getValue(), time);

            time = LocalTime.of(t_hr, t_min, t_sec);
            to = LocalDateTime.of(dp_From.getValue(), time);


        if (to.isBefore(from)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong period");
            alert.setContentText("Please, check borders and try again.");
            alert.showAndWait();
            saveChanges = false;
            return;
        }



        settings =  new Settings(settingsKind, controllerLocation, indoorOutdoor,
                from.toLocalDate(), f_hr, f_min, f_sec,
                to.toLocalDate(), t_hr, t_min, t_sec);
        //
        /*
        //
        //Здесь должен быть напрос на сервер и запись ответа в dateVal
        //
        if (данные пришли)
        {
            settings =  new Settings(settingsKind, controllerLocation, indoorOutdoor, from, to);

            запись ответа в dateVal
        }
        else { // иначе, здесь будет выведено сообщение о том, какой запрост предлагает сервер
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText();
            alert.setHeaderText();
            alert.setTitle();
            saveChanges = true;
        }
        */

        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


    public void init(){
        rb_Temperature.setToggleGroup(radioButtonGroup);
        rb_Humidity.setToggleGroup(radioButtonGroup);
        rb_Illumination.setToggleGroup(radioButtonGroup);
        rb_Concentration_of_CO2.setToggleGroup(radioButtonGroup);

        initMenuItems();

        disableAllAboveType();

        radioButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (radioButtonGroup.getSelectedToggle() != null) {
                    mb_ChangeControllerLocation.setDisable(false);
                    mb_IndoorOutdoor.setDisable(false);
                    t_region.setDisable(false);
                    t_city.setDisable(false);
                    t_district.setDisable(false);
                    dp_From.setDisable(false);
                    dp_To.setDisable(false);
                    b_Apply.setDisable(false);
                    tf_f_hr.setDisable(false);
                    tf_f_min.setDisable(false);
                    tf_f_sec.setDisable(false);
                    tf_t_hr.setDisable(false);
                    tf_t_min.setDisable(false);
                    tf_t_sec.setDisable(false);

                    currentControllerType = ((RadioButton)(radioButtonGroup.getSelectedToggle())).getText();
                }
                else
                    disableAllAboveType();

            }
        });

        if (settings != null) {
            String controllerType = settings.getControllerType();
            for (RadioButton rb : radioButtonGroup.getToggles().toArray(new RadioButton[4]))
                if (rb.getText().equals(controllerType)) {
                    radioButtonGroup.selectToggle(rb);
                    break;
                }

            int index = settings.getLocation();
            t_region.setText(ControllerLocations.getRegion(index));
            t_city.setText(ControllerLocations.getCity(index));
            t_district.setText(ControllerLocations.getDistrict(index));
            controllerLocation = index;

            mb_IndoorOutdoor.setText("Controller type: " + (settings.getIndoorOutdoor() == 0  ? "Indoor" : "Outdoors"));

            dp_From.setValue(settings.getFrom());
            dp_To.setValue(settings.getTo());

            tf_f_hr.setText(Integer.toString(settings.getF_hr()));
            tf_f_min.setText(Integer.toString(settings.getF_min()));
            tf_f_sec.setText(Integer.toString(settings.getF_sec()));
            tf_t_hr.setText(Integer.toString(settings.getT_hr()));
            tf_t_min.setText(Integer.toString(settings.getT_min()));
            tf_t_sec.setText(Integer.toString(settings.getT_sec()));

            enableAll();
        }
    }

    public HashMap<LocalDate, Double> getValues(){
        return dateVal;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isApplyChangesClicked() {
        return saveChanges;
    }

    public Settings getSettings() {
        return settings;
    }

}