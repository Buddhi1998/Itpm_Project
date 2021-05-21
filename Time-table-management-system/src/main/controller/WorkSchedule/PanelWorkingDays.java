package main.controller.WorkSchedule;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class PanelWorkingDays implements Initializable {
    @FXML
    private Button btnWorkingDays;

    @FXML
    private BorderPane pnlShedule;

    public static final Logger log = Logger.getLogger(PanelWorkingDays.class.getName());

    public PanelWorkingDays() {
    }

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            pnlShedule.getChildren().removeAll(new Node[0]);
            if (event.getSource() == btnWorkingDays) {
               
                FXMLLoader loader = new FXMLLoader();
            	loader.setLocation(getClass().getResource("/main/views/WorkSchedule/pnlDays.fxml"));
            	Parent root = loader.load();
            	pnlShedule.setCenter(root);
            }
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        pnlShedule.getChildren().removeAll(new Node[0]);
        Parent root = null;
        try {        
           FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/WorkSchedule/pnlDays.fxml"));	
            root = loader.load();
            pnlShedule.setCenter(root);
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
        pnlShedule.setCenter(root);
    }


}