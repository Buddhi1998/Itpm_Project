package main.controller.Session;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionMainController {

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnAdd;

    @FXML
    private BorderPane pnlMain;

    public static final Logger log = Logger.getLogger(SessionMainController.class.getName());

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnAdd) {
                pnlMain.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Lecturer/MainSessions.fxml"));
                Parent root = loader.load();
                pnlMain.setCenter(root);

            } else if (event.getSource() == btnSearch) {
                pnlMain.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Lecturer/ViewSearchSessions.fxml"));
                Parent root = loader.load();
                pnlMain.setCenter(root);

            }
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}
