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

public class PanelLocationController implements Initializable {

    public PanelLocationController() {
     //Panel location controller Constructor
    }

    @FXML
    private Button btnBuilding;

    @FXML
    private Button btnRoom;

    @FXML
    private Button btnOptions;

    @FXML
    private BorderPane pnlLocation;

    public static final Logger log = Logger.getLogger(PanelLocationController.class.getName());


    @FXML
    void handleEvents(ActionEvent event) {

        try {
            if (event.getSource() == btnBuilding) {
                pnlLocation.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/BuildingMainRow.fxml"));
                Parent root = loader.load();
                pnlLocation.setCenter(root);

            } else if (event.getSource() == btnRoom) {
                pnlLocation.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/RoomMainRow.fxml"));
                Parent root = loader.load();
                pnlLocation.setCenter(root);

            } else if (event.getSource() == btnOptions) {
                pnlLocation.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/OptionsMainRow.fxml"));
                Parent root = loader.load();
                pnlLocation.setCenter(root);

            }

        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            pnlLocation.getChildren().removeAll();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/Location/BuildingMainRow.fxml"));
            Parent root = loader.load();
            pnlLocation.setCenter(root);

        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}
