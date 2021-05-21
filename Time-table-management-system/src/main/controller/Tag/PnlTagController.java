package main.controller.Tag;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import main.controller.Student.PanelStudentsController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PnlTagController implements Initializable {


    public BorderPane pnlTag;
    public Button btnTag;
    public static final Logger log = Logger.getLogger(PnlTagController.class.getName());
    public Button btnSubTag;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            pnlTag.getChildren().removeAll();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/Tag/Tag.fxml"));
            Parent root = loader.load();
            pnlTag.setCenter(root);



        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnSubTag) {
                pnlTag.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Tag/Tag.fxml"));
                Parent root = loader.load();
                pnlTag.setCenter(root);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
