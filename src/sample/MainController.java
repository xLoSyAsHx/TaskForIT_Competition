package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.HashMap;


public class MainController {
    Settings settings;
    CountersDate countersDate;
    Timeline timeline;
    int a = 1;

    @FXML
    private TableView<HashMap<LocalDate, Double>> dataTable;
    @FXML
    private TableColumn<HashMap<LocalDate, Double>, String> leftColumn;
    @FXML
    private TableColumn<HashMap<LocalDate, Double>, String> rightColumn;
    @FXML
    private RadioButton autoUpdateButton;


    // Reference to the main application.
    private Main mainApp;


    public MainController() {
        HashMap<String, HashMap<String, HashMap<String, String[]>>> types = new HashMap<>();
        HashMap<String, HashMap<String, String[]>> regions = new HashMap<>();
        HashMap<String, String[]> cities = new HashMap<>();
        String districtsNN[] = {"districtNN_1", "districtNN_2", "districtNN_3"};
        String districtsBor[] = {"districtBor_1", "districtBor_2"};
        String districtsHZkakoy[] = {"districtHZ_1", "districtHZ_2", "districtHZ_3", "districtHZ_4"};
        String districtsSpecial[] = {"districtSpecial_1", "districtSpecial_2"};




        cities.put("NN", districtsNN);
        cities.put("Bor", districtsBor);

        regions.put("NijObl", cities);

        cities = new HashMap<>();

        cities.put("HZ", districtsHZkakoy);

        regions.put("HZkakoy", cities);

        types.put("Temperature", regions);

        regions = new HashMap<>();

        cities = new HashMap<>();

        cities.put("specialCity", districtsSpecial);

        regions.put("specialRegion", cities);

        types.put("Concentration of CO2", regions);

        settings = new Settings(types);
    }

    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }


    @FXML
    private void initialize() {
        /*
        updateData();
        fillDataInTable();
        */

        timeline = new Timeline (
                new KeyFrame(
                        Duration.millis(1000 * 2), //1000 мс * 30 = 30 сек
                        ae -> {
                            updateData();
                            mainApp.setTitle("NEW TITLE!!!" + a);
                            a++;
                            timeline.playFromStart();
                        }
                )
        );

        //timeline.setAutoReverse(true);
        timeline.playFromStart(); //Запускаем
    }


    @FXML
    private void handleSettings() {
        boolean saveClicked = mainApp.showChartSettingsDialog(settings);
        if (saveClicked) {
            updateDataInTable();
        }
    }

    //Получаем данные с сервера
    public void updateData(){
        if (countersDate != null)
            return;

       // LocalDate starttime = new LocalDate(1,2,3);
        HashMap<LocalDate, Double> timestamp;
        HashMap<String, String> meta;


        //countersDate = new CountersDate();
    }

    public void updateDataInTable(){
        dataTable.getItems().removeAll();
        fillDataInTable();
    }

    //Инициализируем таблицу данными, полученными с сервера
    private void fillDataInTable(){
        for (LocalDate time : countersDate.getTimes())
        {
            leftColumn.setCellValueFactory(new PropertyValueFactory<>(time.toString()));
            rightColumn.setCellValueFactory(new PropertyValueFactory<>(countersDate.getValAsString(time)));
        }
    }
}
