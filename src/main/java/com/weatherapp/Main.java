package com.weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import stage.Stage;

public class Main extends Application {

    @Override
    public void start(javafx.stage.Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui.fxml"));
            primaryStage.setTitle("Secure Portfolio Forecast Portal");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Fatal System Window Layout Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
