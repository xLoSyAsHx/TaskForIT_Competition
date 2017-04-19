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
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;


public class ChartSettingsController {

    private Stage dialogStage;
    private boolean saveChanges = false;
    final ToggleGroup radioButtonGroup = new ToggleGroup();
    String currentControllerType = null;
    RadioButton[] radioButtons;
    HashMap<LocalDate, Double> dateVal = null;

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
    private Button b_Apply;


    @FXML
    private void initialize() {

    }

    private void onLocationChoosed(){
        ObservableList<MenuItem> list = mb_ChangeControllerLocation.getItems();
        for (MenuItem item : list) {
            item.setOnAction(e -> {
                        t_region.setText(item.getText());
                        t_city.setText(item.getText());
                        t_district.setText(item.getText());

                        mb_IndoorOutdoor.setDisable(false);
                        dp_From.setDisable(true);
                        dp_To.setDisable(true);
                        b_Apply.setDisable(true);
                    }
            );
        }
    }

    private void rbOnAction(){
        onLocationChoosed();
    }

    public void init(){

        radioButtons = new RadioButton[4];
        radioButtons[0] = rb_Temperature;
        radioButtons[1] = rb_Humidity;
        radioButtons[2] = rb_Illumination;
        radioButtons[3] = rb_Concentration_of_CO2;

        for (RadioButton rb : radioButtons) {
            rb.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    rbOnAction();
                }
            });
            rb.setToggleGroup(radioButtonGroup);
        }
        rb_Temperature.setDisable(false);

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


                    currentControllerType = ((RadioButton)(radioButtonGroup.getSelectedToggle())).getText();
                    // Do something here with the userData of newly selected radioButton

                }
                else
                {
                    mb_ChangeControllerLocation.setDisable(true);
                    mb_IndoorOutdoor.setDisable(true);
                    t_region.setDisable(true);
                    t_city.setDisable(true);
                    t_district.setDisable(true);
                    dp_From.setDisable(true);
                    dp_To.setDisable(true);
                    b_Apply.setDisable(true);
                }

            }
        });
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isApplyChangesClicked() {
        return saveChanges;
    }

    @FXML
    private void handleApplyChanges() {
        saveChanges = true;

        Vector<Character> controllerType = new Vector<>();
        Vector<String> controllerLocation = new Vector<>();
        String indoorOutdoor;
        LocalDate from, to;


        switch (((RadioButton)(radioButtonGroup.getSelectedToggle())).getText())
        {
            case "Temperature": controllerType.add('t') ; break;
            case "Humidity": controllerType.add('h'); break;
            case "Illumination": controllerType.add('i'); break;
            case "Concentration of CO2": controllerType.add('c'); break;
        }


        controllerLocation.add(t_region.getText());
        controllerLocation.add(t_city.getText());
        controllerLocation.add(t_district.getText());

        indoorOutdoor = mb_IndoorOutdoor.getText();

        from = dp_From.getValue();
        to = dp_To.getValue();
        //
        /*
        //
        //Здесь должен быть напрос на сервер и запись ответа в dateVal
        //
        if (данные пришли)
        {
            запись ответа в dateVal
        }
        else { // иначе, здесь будет выведено сообщение о том, какой запрост предлагает сервер
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText();
            alert.setHeaderText();
            alert.setTitle();
        }
        */
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public HashMap<LocalDate, Double> getValues(){
        return dateVal;
    }

}