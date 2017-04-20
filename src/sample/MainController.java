package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;


public class MainController {
    HashMap<LocalDateTime, Double> dateVal;
    ObservableList<DataValue> listForTable = FXCollections.observableArrayList();
    XYChart.Series series = new XYChart.Series();
    int a = 1;

    @FXML
    private TableView<DataValue> dataTable;
    @FXML
    private TableColumn<DataValue, LocalDateTime> leftColumn;
    @FXML
    private TableColumn<DataValue, Double> rightColumn;
    @FXML
    private RadioButton autoUpdateButton;
    @FXML
    private LineChart<LocalDateTime, Double> lineChart;


    // Reference to the main application.
    private Main mainApp;


    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }


    @FXML
    private void initialize() {
        leftColumn.setCellValueFactory(cellData -> cellData.getValue().getDTProp());
        rightColumn.setCellValueFactory(cellData -> cellData.getValue().getValProp().asObject());
    }


    @FXML
    private void handleSettings() {
        boolean saveClicked = mainApp.showChartSettingsDialog();
        if (saveClicked) {
            updateData();
            dateVal = mainApp.getDateVal();
            updateData();
        }
    }

    public void updateData(){
        if (dateVal != null)
            return;

        lineChart.setTitle(mainApp.getSettings().getControllerType());
        lineChart.getData().clear();

        series = new XYChart.Series();
        for (LocalDateTime localDateTime : mainApp.getDateVal().keySet())
            series.getData().add(new XYChart.Data(localDateTime.toString(), 23));
        lineChart.getData().add(series);

        dataTable.getItems().removeAll();
        listForTable.removeAll();
        fillDataInTable();
    }

    private void fillDataInTable(){
        for (LocalDateTime time : dateVal.keySet())
            listForTable.add(new DataValue(time, dateVal.get(time)));
        dataTable.setItems(listForTable);
    }
}
