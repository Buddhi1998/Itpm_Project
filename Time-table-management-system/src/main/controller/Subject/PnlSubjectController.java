package main.controller.Subject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import main.controller.MainController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PnlSubjectController implements Initializable {

    @FXML
    private Button btnSubject;

    @FXML
    private Button btnTag;

    @FXML
    private BorderPane pnlSubject;

    public static final Logger log = Logger.getLogger(PnlSubjectController.class.getName());

    @FXML
    void handleEvents(ActionEvent event) {
        try {

            if (event.getSource() == btnSubject) {
                pnlSubject.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Subject/MainSubject.fxml"));
                Parent root = loader.load();
                pnlSubject.setCenter(root);

            }
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            pnlSubject.getChildren().removeAll();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/Subject/MainSubject.fxml"));
            Parent root = loader.load();
            pnlSubject.setCenter(root);
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}
