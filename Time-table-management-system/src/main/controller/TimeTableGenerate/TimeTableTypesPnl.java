package main.controller.TimeTableGenerate;

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

public class TimeTableTypesPnl implements Initializable {

    @FXML
    private Button btnStudent;

    @FXML
    private Button btnLecturer;

    @FXML
    private Button btnRoom;

    @FXML
    private BorderPane pnlMain;

    public static final Logger log = Logger.getLogger(TimeTableTypesPnl.class.getName());

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnLecturer) {
                pnlMain.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/TimeTableGenerate/Lecturer.fxml"));
                Parent root = loader.load();
                pnlMain.setCenter(root);

            } else if (event.getSource() == btnStudent) {
                loadMainPanel();

            } else if (event.getSource() == btnRoom) {
                pnlMain.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/TimeTableGenerate/Rooms.fxml"));
                Parent root = loader.load();
                pnlMain.setCenter(root);
            }
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       loadMainPanel();
    }

    public void loadMainPanel(){
        try {
            pnlMain.getChildren().removeAll();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/TimeTableGenerate/Student.fxml"));
            Parent root = loader.load();
            pnlMain.setCenter(root);
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}
