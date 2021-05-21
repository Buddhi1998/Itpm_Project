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

public class RoomMainRowController implements Initializable {

    @FXML
    private Button btnViewRoom;

    @FXML
    private Button btnSearchRoom;

    @FXML
    private Button btnAddRoom;

    @FXML
    private BorderPane pnlMainRoom;

    public static final Logger log = Logger.getLogger(RoomMainRowController.class.getName());

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnAddRoom) {
                pnlMainRoom.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/AddRoom.fxml"));
                Parent root = loader.load();
                pnlMainRoom.setCenter(root);

            } else if (event.getSource() == btnSearchRoom) {
                pnlMainRoom.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/SearchRoom.fxml"));
                Parent root = loader.load();
                pnlMainRoom.setCenter(root);

            } else if (event.getSource() == btnViewRoom) {
                pnlMainRoom.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Location/ViewRoom.fxml"));
                Parent root = loader.load();
                pnlMainRoom.setCenter(root);

            }
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            pnlMainRoom.getChildren().removeAll();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/Location/AddRoom.fxml"));
            Parent root = loader.load();
            pnlMainRoom.setCenter(root);
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}
