package me.verni.application.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainPane;

    @FXML
    public void initialize() {
        loadDashboard();
    }

    @FXML
    public void loadDashboard() {
        loadView("DashboardView.fxml");
    }

    @FXML
    public void loadAddEntryView(){
        loadView("AddEntryView.fxml");
    }

    @FXML
    public void loadCalendarView(){
        loadView("CalendarView.fxml");
    }

    @FXML
    public void loadChartView(){
        loadView("ChartView.fxml");
    }

    private void loadView(String fxmlPath){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlPath));
            Parent root = loader.load();
            mainPane.setCenter(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}