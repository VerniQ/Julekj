package me.juleK.application.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import me.juleK.application.database.HealthEntry;
import me.juleK.application.database.HealthEntryService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableView<HealthEntry> tableView;

    @FXML
    private TableColumn<HealthEntry, LocalDate> dateColumn;

    @FXML
    private TableColumn<HealthEntry, LocalTime> timeColumn;

    @FXML
    private TableColumn<HealthEntry, Double> weightColumn;

    @FXML
    private TableColumn<HealthEntry, Integer> systolicPressureColumn;

    @FXML
    private TableColumn<HealthEntry, Integer> diastolicPressureColumn;

    @FXML
    private TableColumn<HealthEntry, String> moodColumn;

    private final HealthEntryService healthEntryService;

    public DashboardController() {
        this.healthEntryService = new HealthEntryService();
    }

    @FXML
    public void initialize() {
        welcomeLabel.setText("Witaj w aplikacji zdrowotnej!");
        setupTable();
        loadData();
    }

    private void setupTable() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        systolicPressureColumn.setCellValueFactory(new PropertyValueFactory<>("systolicPressure"));
        diastolicPressureColumn.setCellValueFactory(new PropertyValueFactory<>("diastolicPressure"));
        moodColumn.setCellValueFactory(new PropertyValueFactory<>("mood"));
    }

    private void loadData() {
        List<HealthEntry> healthEntries = healthEntryService.getAllHealthEntries();
        ObservableList<HealthEntry> observableList = FXCollections.observableArrayList(healthEntries);
        tableView.setItems(observableList);
    }
}