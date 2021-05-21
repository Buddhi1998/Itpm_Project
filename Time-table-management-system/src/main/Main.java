package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/views/Main.fxml"));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, e.getMessage());
            alert.showAndWait();
//            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Time Table Management System");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
