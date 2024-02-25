package com.bion.project2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            URL resource = getClass().getResource("views/LoginForm.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Form");
            primaryStage.setResizable(false);
            primaryStage.setMinWidth(610);
            primaryStage.setMinHeight(410);
            primaryStage.centerOnScreen();
            primaryStage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
