package com.weatherapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class WeatherController {
    @FXML private TextField cityInputField;
    @FXML private ProgressIndicator loadingSpinner;
    @FXML private VBox resultContainer;
    @FXML private Label cityNameLabel;
    @FXML private Label tempLabel;
    @FXML private Label conditionsLabel;
    @FXML private Label humidityLabel;

    private final WeatherService weatherService = new WeatherService();
    private final WeatherParser parser = new WeatherParser();

    @FXML
    private void handleSearchAction() {
        String city = cityInputField.getText();
        if (city == null || city.strip().isEmpty()) return;

        loadingSpinner.setVisible(true);
        loadingSpinner.setManaged(true);
        resultContainer.setVisible(false);

        Thread networkWorker = new Thread(() -> {
            try {
                String rawJson = weatherService.fetchRawWeatherData(city);
                WeatherData data = parser.parse(rawJson);

                Platform.runLater(() -> updateUI(data));

            } catch (Exception error) {
                Platform.runLater(() -> displayError(error.getMessage()));
            }
        });

        networkWorker.setDaemon(true);
        networkWorker.start();
    }

    private void updateUI(WeatherData data) {
        hideLoadingState();
        cityNameLabel.setText(data.cityName());
        tempLabel.setText("Temperature: " + data.temperature() + " °C");
        conditionsLabel.setText("Conditions: " + data.conditions());
        humidityLabel.setText("Humidity: " + data.humidity() + "%");
        resultContainer.setVisible(true);
    }

    private void displayError(String alertMessage) {
        hideLoadingState();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Query Failure Exception");
        alert.setHeaderText("Execution Problem Encountered");
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    private void hideLoadingState() {
        loadingSpinner.setVisible(false);
        loadingSpinner.setManaged(false);
    }
}
