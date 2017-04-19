package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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
    @FXML
    private LineChart<LocalDate, Double> lineChart;


    // Reference to the main application.
    private Main mainApp;


    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }


    @FXML
    private void initialize() {

        lineChart.setTitle("Stock Monitoring, 2010");

        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");

        series.getData().add(new XYChart.Data("Jan", 23));
        series.getData().add(new XYChart.Data("Feb", 14));
        series.getData().add(new XYChart.Data("Mar", 15));
        series.getData().add(new XYChart.Data("Apr", 24));
        series.getData().add(new XYChart.Data("May", 34));
        series.getData().add(new XYChart.Data("Jun", 36));
        series.getData().add(new XYChart.Data("Jul", 22));
        series.getData().add(new XYChart.Data("Aug", 45));
        series.getData().add(new XYChart.Data("Sep", 43));
        series.getData().add(new XYChart.Data("Oct", 17));
        series.getData().add(new XYChart.Data("Nov", 29));
        series.getData().add(new XYChart.Data("Dec", 25));

        lineChart.getData().add(series);
        //


        series = new XYChart.Series();
        series.setName("My portfolio2");

        series.getData().add(new XYChart.Data("Jan", 11));
        series.getData().add(new XYChart.Data("Feb", 12));
        series.getData().add(new XYChart.Data("Mar", 13));
        series.getData().add(new XYChart.Data("Apr", 24));
        series.getData().add(new XYChart.Data("May", 10));
        series.getData().add(new XYChart.Data("Jun", 22));
        series.getData().add(new XYChart.Data("Jul", 24));
        series.getData().add(new XYChart.Data("Aug", 28));
        series.getData().add(new XYChart.Data("Sep", 35));
        series.getData().add(new XYChart.Data("Oct", 37));
        series.getData().add(new XYChart.Data("Nov", 36));
        series.getData().add(new XYChart.Data("Dec", 35));

        lineChart.getData().add(series);

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
