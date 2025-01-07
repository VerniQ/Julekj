package me.juleK.application.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import me.juleK.application.database.HealthEntry;
import me.juleK.application.database.HealthEntryService;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class ChartController {

    @FXML
    private LineChart<String, Number> numberChart;

    @FXML
    private LineChart<String, String> stringChart;
    @FXML
    private CategoryAxis xAxisNumber;
    @FXML
    private CategoryAxis xAxisString;


    @FXML
    private NumberAxis yNumberAxis;

    @FXML
    private CategoryAxis yCategoryAxis;
    HashMap<String, String> translations = new HashMap<>();


    @FXML
    private ChoiceBox<String> parameterChoiceBox;
    private final HealthEntryService healthEntryService;

    public ChartController() {
        this.healthEntryService = new HealthEntryService();
        translations.put("weight", "Waga");
        translations.put("systolicPressure", "Ciśnienie skurczowe");
        translations.put("diastolicPressure", "Ciśnienie rozkurczowe");
        translations.put("mood", "Samopoczucie");

    }

    @FXML
    public void initialize() {
        setupChoiceBox();
        loadChartData("weight");
    }

    private void setupChart() {
        xAxisNumber.setLabel("Data");
        xAxisString.setLabel("Data");
    }


    private void setupChoiceBox() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "weight", "systolicPressure", "diastolicPressure", "mood");
        parameterChoiceBox.setItems(options);
        parameterChoiceBox.setValue("weight");
        parameterChoiceBox
                .valueProperty()
                .addListener(
                        (obs, oldVal, newVal) -> {
                            loadChartData(newVal);
                        });
    }


    private void loadChartData(String selectedParameter) {
        numberChart.setVisible(false);
        stringChart.setVisible(false);
        setupChart();
        switch (selectedParameter) {
            case "weight", "systolicPressure", "diastolicPressure" -> {
                yNumberAxis.setLabel(translations.get(selectedParameter));
                numberChart.setVisible(true);
                loadNumericalData(selectedParameter);
            }
            case "mood" -> {
                yCategoryAxis.setLabel(translations.get(selectedParameter)); // tłumaczenie
                stringChart.setVisible(true);
                loadMoodData();
            }
            default -> {
                yNumberAxis.setLabel(translations.get("weight"));
                numberChart.setVisible(true);
                loadNumericalData("weight");
            }
        }
    }
    private void loadNumericalData(String selectedParameter) {
        numberChart.getData().clear();
        List<HealthEntry> healthEntries = healthEntryService.getAllHealthEntries();
        ObservableList<XYChart.Data<String, Number>> chartData = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (HealthEntry entry : healthEntries) {
            String date = entry.getDate().format(formatter);
            double value;

            switch (selectedParameter) {
                case "weight" -> value = entry.getWeight();
                case "systolicPressure" -> value = entry.getSystolicPressure();
                case "diastolicPressure" -> value = entry.getDiastolicPressure();
                default -> value = entry.getWeight();
            }
            chartData.add(new XYChart.Data<>(date, value));
        }
        XYChart.Series<String, Number> series = new XYChart.Series<>(translations.get(selectedParameter), chartData);
        numberChart.getData().add(series);
    }


    private void loadMoodData() {
        stringChart.getData().clear();
        List<HealthEntry> healthEntries = healthEntryService.getAllHealthEntries();
        ObservableList<XYChart.Data<String, String>> chartData = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (HealthEntry entry : healthEntries) {
            String date = entry.getDate().format(formatter);
            String mood = entry.getMood();
            chartData.add(new XYChart.Data<>(date, mood));
        }
        XYChart.Series<String,String> series = new XYChart.Series<>(translations.get("mood"), chartData);
        stringChart.getData().add(series);
    }
}