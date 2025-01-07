package me.juleK.application.ui;

import me.juleK.application.database.HealthEntry;
import me.juleK.application.database.HealthEntryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEntryController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField systolicPressureField;

    @FXML
    private TextField diastolicPressureField;

    @FXML
    private TextField moodField;

    private final HealthEntryService healthEntryService;

    public AddEntryController() {
        this.healthEntryService = new HealthEntryService();
    }

    @FXML
    public void addEntry(ActionEvent event) {
        try {
            LocalDate date = datePicker.getValue();
            LocalTime time;
            try {
                String timeText = timeField.getText();
                Pattern pattern = Pattern.compile("^(\\d{1,2})(:(\\d{2})(:(\\d{2}(\\.\\d{1,3})?)?)?)?$");
                Matcher matcher = pattern.matcher(timeText);

                if (matcher.matches()) {
                    int hour = Integer.parseInt(matcher.group(1));
                    int minute = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;
                    int second = matcher.group(5) != null ? Integer.parseInt(matcher.group(5).split("\\.")[0]) : 0;
                    String fractionalPart = matcher.group(6);
                    int nano = 0;
                    if (fractionalPart != null) {
                        double fractionalSeconds = Double.parseDouble("0" + fractionalPart);
                        nano = (int) (fractionalSeconds * 1_000_000_000);
                    }
                    time = LocalTime.of(hour,minute,second,nano);

                } else{
                    time = LocalTime.parse(timeText, DateTimeFormatter.ofPattern("HH:mm:ss"));

                }
            } catch (DateTimeParseException | NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Błąd!", "Niepoprawny format godziny, wprowadź format HH, HH:mm lub HH:mm:ss");
                e.printStackTrace();
                return;
            }


            double weight = Double.parseDouble(weightField.getText());
            int systolicPressure = Integer.parseInt(systolicPressureField.getText());
            int diastolicPressure = Integer.parseInt(diastolicPressureField.getText());
            String mood = moodField.getText();

            HealthEntry entry = new HealthEntry(date, time, weight, systolicPressure, diastolicPressure, mood);
            healthEntryService.addHealthEntry(entry);
            showAlert(Alert.AlertType.INFORMATION, "Sukces!", "Dodano nowy wpis.");
            clearFields();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Błąd!", "Niepoprawny format danych.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Błąd!", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Błąd!", "Wystąpił błąd podczas dodawania wpisu.");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        datePicker.setValue(null);
        timeField.clear();
        weightField.clear();
        systolicPressureField.clear();
        diastolicPressureField.clear();
        moodField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
