package main.controller.Location;

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

public class OptionsMainRowController implements Initializable {

    @FXML
    private Button btnTagOptions;

    @FXML
    private BorderPane pnlMainOptions;

    @FXML
    private Button btnSubjectOptions;

    @FXML
    private Button btnLecturerOptions;

    @FXML
    private Button btnGroupOptions;

    @FXML
    private Button btnReservedOptions;

    @FXML
    private Button btnSessionOptions;

    public static final Logger log = Logger.getLogger(OptionsMainRowController.class.getName());

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnTagOptions) {
                pnlMainOptions.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/Tag.fxml"));
                Parent root = loader.load();
                pnlMainOptions.setCenter(root);

            } else if (event.getSource() == btnSubjectOptions) {
                pnlMainOptions.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/Subject.fxml"));
                Parent root = loader.load();
                pnlMainOptions.setCenter(root);
            } else if (event.getSource() == btnLecturerOptions) {
                pnlMainOptions.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/Lecturer.fxml"));
                Parent root = loader.load();
                pnlMainOptions.setCenter(root);

            } else if (event.getSource() == btnGroupOptions) {
                pnlMainOptions.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/Group.fxml"));
                Parent root = loader.load();
                pnlMainOptions.setCenter(root);

            } else if (event.getSource() == btnSessionOptions) {
                pnlMainOptions.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/Session.fxml"));
                Parent root = loader.load();
                pnlMainOptions.setCenter(root);

            } else if (event.getSource() == btnReservedOptions) {
                pnlMainOptions.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/Reserved.fxml"));
                Parent root = loader.load();
                pnlMainOptions.setCenter(root);

            }
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            pnlMainOptions.getChildren().removeAll();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/Location/Tag.fxml"));
            Parent root = loader.load();
            pnlMainOptions.setCenter(root);
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }

    }
}
