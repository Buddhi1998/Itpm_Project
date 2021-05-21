package main.controller.Lecturer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainGroupController implements Initializable {

    @FXML
    private Button btnView;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnAdd;

    @FXML
    private BorderPane pnlMain;

    public static final Logger log = Logger.getLogger(MainGroupController.class.getName());

    @FXML
    void handleEvents(ActionEvent event) {

        try {
            if (event.getSource() == btnAdd) {
                pnlMain.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Lecturer/AddMainLecturer.fxml"));
                Parent root = loader.load();
                pnlMain.setCenter(root);
            } else if (event.getSource() == btnSearch) {
                pnlMain.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Lecturer/SearchLecturer.fxml"));
                Parent root = loader.load();
                pnlMain.setCenter(root);

            } else if (event.getSource() == btnView) {
                pnlMain.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Lecturer/ViewLecturer.fxml"));
                Parent root = loader.load();
                pnlMain.setCenter(root);

            }
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/Lecturer/AddMainLecturer.fxml"));
            Parent root = loader.load();
            pnlMain.setCenter(root);

        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}
