package sample;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;


public class ChartSettingsController {

    private Stage dialogStage;
    private boolean saveChanges = false;
    final ToggleGroup radioButtonGroup = new ToggleGroup();
    String currentControllerType = null;
    Integer controllerLocation = null;
    Boolean toWasChoosed = false, fromWasChoosed = false;

    HashMap<LocalDateTime, Double> dateVal = null;
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
                            //controllerLocation = mb_ChangeControllerLocation.getItems().indexOf(item);
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

    private HashMap<LocalDateTime, Double> sendPostRequest(char kind_typename, int location_id, int deployment_id, long time_start, long time_end) {
        PostedData dataObj = new PostedData(kind_typename, location_id, deployment_id, time_start, time_end);
        Gson gson = new Gson();
        HttpClient httpClient = HttpClientBuilder.create().build();
        String responseString = null;
        try {
            HttpPost request = new HttpPost("http://91.215.169.120:9000/drop_jsons/request_post/");
            StringEntity params = new StringEntity(gson.toJson(dataObj));

            // передаём данные на сервер
            request.setEntity(params);

//        request.setHeader("Content-type", "application/json");

            // ждём ответ
            HttpResponse response = httpClient.execute(request);

            // обработка ответа сервера
            responseString = new BasicResponseHandler().handleResponse(response);

            // ecли пришёл пустой ответ
            if (responseString.length() <= 2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("No data");
                alert.setContentText("There is no data for your request. Try to change the settings.");
                alert.showAndWait();
                saveChanges = false;
                return null;
            }
        }
        catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("No data");
            alert.setContentText("There is no data for your request. Try to change the settings.");
            alert.showAndWait();
            saveChanges = false;
        }

        // достаём данные из json в HashMap
        HashMap<String, Double> map = gson.fromJson(responseString, new TypeToken<HashMap<String, Double>>() {
        }.getType());

        HashMap<LocalDateTime, Double> result = new HashMap<>();

        // преобразуем String к LocalDateTime и заносим в новый HashMap
        Iterator itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, Double> pair = (Map.Entry)itr.next();
            LocalDateTime dateKey = LocalDateTime.ofEpochSecond(Long.valueOf(pair.getKey()), 0, ZoneOffset.UTC);
            result.putIfAbsent(dateKey, pair.getValue());
            itr.remove();
        }


        return  result;
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
            case "Illumination": kind = 'l'; settingsKind = "Illumination"; break;
            case "Concentration of CO2": kind = 'c'; settingsKind = "Concentration of CO2"; break;
        }

        indoorOutdoor = mb_IndoorOutdoor.getText().equals("Controller type: Indoors") ? 0 : 1;


        try {
            f_hr = Integer.parseInt(!tf_f_hr.getText().isEmpty() ? tf_f_hr.getText() : "0");
            f_min = Integer.parseInt(!tf_f_min.getText().isEmpty() ? tf_f_min.getText() : "0");
            f_sec = Integer.parseInt(!tf_f_sec.getText().isEmpty() ? tf_f_sec.getText() : "0");
            t_hr = Integer.parseInt(!tf_t_hr.getText().isEmpty() ? tf_t_hr.getText() : "0");
            t_min = Integer.parseInt(!tf_t_min.getText().isEmpty() ? tf_t_min.getText() : "0");
            t_sec = Integer.parseInt(!tf_t_sec.getText().isEmpty() ? tf_t_sec.getText() : "0");


            LocalTime time = LocalTime.of(f_hr, f_min, f_sec);
            from = LocalDateTime.of(dp_From.getValue(), time);

            time = LocalTime.of(t_hr, t_min, t_sec);
            to = LocalDateTime.of(dp_To.getValue(), time);

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




        if (to.isBefore(from) || !toWasChoosed || !fromWasChoosed){
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

        dateVal = sendPostRequest(kind, controllerLocation + 1, indoorOutdoor + 1, from.toEpochSecond(ZoneOffset.UTC), to.toEpochSecond(ZoneOffset.UTC));

        if (dateVal == null)
            return;

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

        dp_From.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fromWasChoosed = true;
            }
        });

        dp_To.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toWasChoosed = true;
            }
        });

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

    public HashMap<LocalDateTime, Double> getValues(){
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