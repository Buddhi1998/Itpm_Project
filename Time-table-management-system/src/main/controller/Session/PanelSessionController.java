package main.controller.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import main.controller.MainController;

public class PanelSessionController implements Initializable {

    @FXML
    private Button btnSession;

    @FXML
    private Button btnNotAvailable;

    @FXML
    private BorderPane pnlSession;

    @FXML
    private Button btnConsectiveSession;

    @FXML
    private Button btnRequetsSession;

    @FXML
    private Button btnParallelSession;

    public static final Logger log = Logger.getLogger(PanelSessionController.class.getName());

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnSession) {
                pnlSession.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Session/MainSessions.fxml"));
                Parent root = loader.load();
                pnlSession.setCenter(root);
                String currentUrl = MainController.urlName;
                MainController.urlName = currentUrl + "/" + "SessionDetails";
            } else if (event.getSource() == btnNotAvailable) {
                pnlSession.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Session/NotAvailableSession.fxml"));
                Parent root = loader.load();
                pnlSession.setCenter(root);
                String currentUrl = MainController.urlName;
                MainController.urlName = currentUrl + "/" + "NotAvailableSession";
            } else if (event.getSource() == btnConsectiveSession) {
                pnlSession.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Session/ConsectiveSession.fxml"));
                Parent root = loader.load();
                pnlSession.setCenter(root);
                String currentUrl = MainController.urlName;
                MainController.urlName = currentUrl + "/" + "ConsectiveSession";
            } else if (event.getSource() == btnRequetsSession) {
                pnlSession.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Session/ViewSearchSessions.fxml"));
                Parent root = loader.load();
                pnlSession.setCenter(root);
                String currentUrl = MainController.urlName;
                MainController.urlName = currentUrl + "/" + "Search Session";
            } else if (event.getSource() == btnParallelSession) {
                pnlSession.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Session/ParallelSessions.fxml"));
                Parent root = loader.load();
                pnlSession.setCenter(root);
                String currentUrl = MainController.urlName;
                MainController.urlName = currentUrl + "/" + "ParallelSession";
            }
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            pnlSession.getChildren().removeAll();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/Session/MainSessions.fxml"));
            Parent root = loader.load();
            pnlSession.setCenter(root);
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}
