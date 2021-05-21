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

public class BuildingMainRowController implements Initializable {

    @FXML
    private Button btnViewBuilding;

    @FXML
    private Button btnSearchBuilding;

    @FXML
    private Button btnAddBuilding;

    @FXML
    private BorderPane pnlMainBuilding;

    public static final Logger log = Logger.getLogger(BuildingMainRowController.class.getName());

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnAddBuilding) {
                pnlMainBuilding.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/AddBuilding.fxml"));
                Parent root = loader.load();
                pnlMainBuilding.setCenter(root);

            } else if (event.getSource() == btnSearchBuilding) {
                pnlMainBuilding.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/SearchBuilding.fxml"));
                Parent root = loader.load();
                pnlMainBuilding.setCenter(root);

            } else if (event.getSource() == btnViewBuilding) {
                pnlMainBuilding.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/ViewBuilding.fxml"));
                Parent root = loader.load();
                pnlMainBuilding.setCenter(root);

            }
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            pnlMainBuilding.getChildren().removeAll();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/Location/AddBuilding.fxml"));
            Parent root = loader.load();
            pnlMainBuilding.setCenter(root);

        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }

    }
}
