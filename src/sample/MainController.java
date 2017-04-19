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
    HashMap<LocalDate, Double> dateVal;
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
        boolean saveClicked = mainApp.showChartSettingsDialog(dateVal);
        if (saveClicked) {
            updateDataInTable();
        }
    }

    //Получаем данные с сервера
    public void updateData(){
        if (dateVal != null)
            return;
    }

    public void updateDataInTable(){
        dataTable.getItems().removeAll();
        fillDataInTable();
    }

    //Инициализируем таблицу данными, полученными с сервера
    private void fillDataInTable(){
        for (LocalDate time : dateVal.keySet())
        {
            leftColumn.setCellValueFactory(new PropertyValueFactory<>(time.toString()));
            rightColumn.setCellValueFactory(new PropertyValueFactory<>(dateVal.get(time).toString()));
        }
    }
}
