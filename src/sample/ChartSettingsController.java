package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;


public class ChartSettingsController {

    private Stage dialogStage;
    private Settings settings;
    private boolean saveChanges = false;
    final ToggleGroup radioButtonGroup = new ToggleGroup();
    String currentControllerType = null;

    RadioButton[] radioButtons;

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
    private MenuButton mb_ChangeControllerLocation;
    @FXML
    private MenuButton mb_ChangeCity;
    @FXML
    private MenuButton mb_ChangeDistrict;
    @FXML
    private MenuButton mb_From;
    @FXML
    private MenuButton mb_To;


    @FXML
    private void initialize() {

    }

    private void updateDistrictMenuButtons(){
        ObservableList<MenuItem> list = mb_ChangeDistrict.getItems();
        for (MenuItem item : list) {
            item.setOnAction(e -> {
                        mb_ChangeDistrict.setText(item.getText());
                    }
            );
        }
    }

    private void updateCitiesMenuButtons(){
        ObservableList<MenuItem> list = mb_ChangeCity.getItems();
        for (MenuItem item : list) {
            item.setOnAction(e -> {
                        mb_ChangeCity.setText(item.getText());
                        mb_ChangeDistrict.setDisable(false);

                        if (item.getText().equals("all")){
                            mb_ChangeDistrict.setText("all");
                            mb_ChangeDistrict.setDisable(true);
                        }
                        else {
                            mb_ChangeDistrict.setText("Choose district");
                            mb_ChangeDistrict.getItems().clear();
                            for (String district : settings.getAvalibleDistricts(currentControllerType, mb_ChangeRegion.getText(), item.getText()))
                                mb_ChangeDistrict.getItems().add(new MenuItem(district));
                            if (mb_ChangeDistrict.getItems().size() > 1)
                                mb_ChangeDistrict.getItems().add(new MenuItem("all"));
                        }
                        updateDistrictMenuButtons();
                    }
            );
        }
    }

    private void updateRegionMenuButtons(){
        ObservableList<MenuItem> list = mb_ChangeRegion.getItems();
        for (MenuItem item : list) {
            item.setOnAction(e -> {
                        mb_ChangeRegion.setText(item.getText());
                        mb_ChangeCity.setDisable(false);
                        if (item.getText().equals("all")) {
                            mb_ChangeCity.setText("all");
                            mb_ChangeDistrict.setText("all");
                            mb_ChangeCity.setDisable(true);
                            mb_ChangeDistrict.setDisable(true);
                        }
                        else {
                            mb_ChangeCity.setText("Choose city");
                            mb_ChangeCity.getItems().clear();
                            mb_ChangeDistrict.setText("Choose district");
                            mb_ChangeDistrict.getItems().clear();
                            mb_ChangeDistrict.setDisable(true);

                            for (String city : settings.getAvalibleCities(currentControllerType, item.getText()))
                                mb_ChangeCity.getItems().add(new MenuItem(city));
                            if (mb_ChangeCity.getItems().size() > 1)
                                mb_ChangeCity.getItems().add(new MenuItem("all"));
                        }
                        updateCitiesMenuButtons();
                    }
            );
        }
    }

    private void rbOnAction(){
        mb_ChangeCity.setDisable(true);
        mb_ChangeDistrict.setDisable(true);

        ObservableList<MenuItem> menuItems = mb_ChangeRegion.getItems();
        menuItems.clear();
        for (String district : settings.getAvalibleRegions(currentControllerType))
            menuItems.add(new MenuItem(district));
        if (menuItems.size() > 1)
            menuItems.add(new MenuItem("all"));

        updateRegionMenuButtons();
    }

    public void init(){

        radioButtons = new RadioButton[4];
        radioButtons[0] = rb_Temperature;
        radioButtons[1] = rb_Humidity;
        radioButtons[2] = rb_Illumination;
        radioButtons[3] = rb_Concentration_of_CO2;

        Set<String> types = settings.getAvalibleControllerTypes();

        for (RadioButton rb : radioButtons) {
            rb_Temperature.setDisable(false);
            if (!types.contains(rb.getText()))
                rb.setDisable(true);
            rb.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    rbOnAction();
                }
            });
            rb.setToggleGroup(radioButtonGroup);
        }
        rb_Temperature.setDisable(false);

        mb_ChangeRegion.getItems().add(new MenuItem("all"));
        mb_ChangeCity.getItems().add(new MenuItem("all"));



        radioButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (radioButtonGroup.getSelectedToggle() != null) {
                    mb_ChangeRegion.setDisable(false);
                    mb_ChangeRegion.setText("Choose region");
                    mb_ChangeCity.setDisable(true);
                    mb_ChangeCity.setText("Choose city");
                    mb_ChangeDistrict.setDisable(true);
                    mb_ChangeDistrict.setText("Choose district");

                    currentControllerType = ((RadioButton)(radioButtonGroup.getSelectedToggle())).getText();
                    // Do something here with the userData of newly selected radioButton

                }
                else
                    mb_ChangeRegion.setDisable(true);

            }
        });

        mb_ChangeRegion.setDisable(true);
        mb_ChangeCity.setDisable(true);
        mb_ChangeDistrict.setDisable(true);
        //



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