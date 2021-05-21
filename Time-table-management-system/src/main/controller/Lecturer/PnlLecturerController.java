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

public class PnlLecturerController implements Initializable {

    @FXML
    private Button btnLecturer;

    @FXML
    private Button btnDepartment;

    @FXML
    private Button btnNotAvailable;

    @FXML
    private BorderPane pnlStudent;

    public static final Logger log = Logger.getLogger(PnlLecturerController.class.getName());

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnDepartment) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Lecturer/Department.fxml"));
                Parent root = loader.load();
                pnlStudent.setCenter(root);
            } else if (event.getSource() == btnLecturer) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Lecturer/MainLecturer.fxml"));
                Parent root = loader.load();
                pnlStudent.setCenter(root);
            } else if (event.getSource() == btnNotAvailable) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Lecturer/NotAvailableLecturer.fxml"));
                Parent root = loader.load();
                pnlStudent.setCenter(root);
            }
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/Lecturer/MainLecturer.fxml"));
            Parent root = loader.load();
            pnlStudent.setCenter(root);

        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

}
