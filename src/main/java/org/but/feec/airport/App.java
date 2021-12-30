package org.but.feec.airport;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

import org.but.feec.airport.controller.LoginController;
import org.but.feec.airport.exceptions.ExceptionHandler;

public class App extends Application {
    private FXMLLoader loader;
    private VBox mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
        	FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(getClass().getResource("Login.fxml"));
        	Parent content = loader.load(); 
        	System.out.println("APP/start");
            primaryStage.setTitle("Airport Login");
            Scene scene = new Scene(content);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex) {
           ExceptionHandler.handleException(ex);
        }
    }

}

