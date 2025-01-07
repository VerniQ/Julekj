package me.juleK.application.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import me.juleK.application.database.HealthEntry;
import me.juleK.application.database.HealthEntryService;

import java.time.LocalDate;
import java.util.List;

public class CalendarController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ListView<HealthEntry> entryListView;

    @FXML
    private Label noEntriesLabel;
    private final HealthEntryService healthEntryService;

    public CalendarController() {
        this.healthEntryService = new HealthEntryService();
    }

    @FXML
    public void initialize() {
        setupCalendar();
        loadEntries();
    }

    private void setupCalendar() {
        datePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                showEntriesForDate(newDate);
            }
        });

        datePicker.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (!empty) {
                    List<HealthEntry> entries = healthEntryService.getAllHealthEntries();
                    boolean hasEntry = entries.stream().anyMatch(entry -> entry.getDate().isEqual(date));
                    if (hasEntry) {
                        setStyle("-fx-background-color: #b9e1d2;");
                    }
                }
            }
        });
    }

    private void loadEntries() {
        entryListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(HealthEntry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    setText(String.format("Czas: %s, Waga: %.2f, Samopoczucie: %s", entry.getTime(), entry.getWeight(), entry.getMood()));
                }
            }
        });
        showEntriesForDate(LocalDate.now());
    }

    private void showEntriesForDate(LocalDate date) {
        List<HealthEntry> entries = healthEntryService.getAllHealthEntries();
        List<HealthEntry> filteredEntries = entries.stream().filter(entry -> entry.getDate().isEqual(date)).toList();

        if (filteredEntries.isEmpty()) {
            entryListView.setVisible(false);
            noEntriesLabel.setVisible(true);
            noEntriesLabel.setText("Brak wpisów dla wybranej daty");
        } else {
            entryListView.setVisible(true);
            noEntriesLabel.setVisible(false);
            ObservableList<HealthEntry> observableEntries = FXCollections.observableArrayList(filteredEntries);
            entryListView.setItems(observableEntries);
        }
    }
}
