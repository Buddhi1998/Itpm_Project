package main.controller.Student;

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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PanelStudentsController implements Initializable {

    public Button btnAllDetails;

    public PanelStudentsController() {
        //Constructor

    }

    @FXML
    private Button btnSubGroup;

    @FXML
    private Button btnNotAvailable;

    @FXML
    private Button btnAcademicYear;

    @FXML
    private Button btnMainGroup;

    @FXML
    private BorderPane pnlStudent;

    @FXML
    private Button btnProgramme;

    public static final Logger log = Logger.getLogger(PanelStudentsController.class.getName());

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnAcademicYear) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Students/AcademicYear.fxml"));
                Parent root = loader.load();
                pnlStudent.setCenter(root);
                String currentUrl = MainController.urlName;
                MainController.urlName = currentUrl + "/" + "academicyear&semester";

            } else if (event.getSource() == btnMainGroup) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Students/NewMainGroup.fxml"));
                Parent root = loader.load();
                pnlStudent.setCenter(root);

            } else if (event.getSource() == btnSubGroup) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Students/NewSubGroup.fxml"));
                Parent root = loader.load();
                pnlStudent.setCenter(root);
//                pnlStudent.set
            } else if (event.getSource() == btnNotAvailable) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Students/NotAvailableGroup.fxml"));
                Parent root = loader.load();
                pnlStudent.setCenter(root);

            } else if (event.getSource() == btnProgramme) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Students/Programme.fxml"));
                Parent root = loader.load();
                pnlStudent.setCenter(root);
            }else if (event.getSource() == btnAllDetails) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Students/detail-view.fxml"));
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
            pnlStudent.getChildren().removeAll();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/Students/detail-view.fxml"));
            Parent root = loader.load();
            pnlStudent.setCenter(root);



        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }


}
