package main.controller.Subject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.model.Subject;
import main.model.YearAndSemester;
import main.service.SubjectService;
import main.service.YearandSemesterService;
import main.service.impl.SubjectServiceImpl;
import main.service.impl.YearAndServiceImpl;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddSubjectController implements Initializable {

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtSubName;

    @FXML
    private TextField txtSubID;

    @FXML
    private TextField txtLecHours;

    @FXML
    private TextField txtEvalHours;
    @FXML
    public TextField txtLabHours;
    @FXML
    private TextField txtYear;


    @FXML
    private ComboBox<String> subType;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtTutHours;
    private ObservableList<String> subjectsLists = FXCollections.observableArrayList("Compulsory", "Optional");
    private ArrayList<YearAndSemester> yearAndSemesters = new ArrayList<>();
    private ArrayList<String> yearSemName = new ArrayList<>();
    int yId = 0;
    public static final Logger log = Logger.getLogger(AddSubjectController.class.getName());
    int yearCount = 0;

    @FXML
    void saveDetails(ActionEvent event) {

        String subId = txtSubID.getText();
        String subName = txtSubName.getText();

        String offeredYearSem = txtYear.getText();
        String subjectType = subType.getValue();
        String categoryName = null;
        categoryName = txtCategory.getText();


        for (YearAndSemester y : this.yearAndSemesters) {
            if (offeredYearSem.equals(y.getFullName())) {
                yId = y.getId();
                yearCount++;
            }
        }

        try {
            int noLecHrs = Integer.parseInt(txtLecHours.getText());
            try {
                int noTutHrs = Integer.parseInt(txtTutHours.getText());
                try {
                    int noEvalHrs = Integer.parseInt(txtEvalHours.getText());
                    try {
                        int noLabHrs = Integer.parseInt(txtLabHours.getText());

                        if (!subId.equalsIgnoreCase("")) {
                            if (!subName.equalsIgnoreCase("")) {
                                if (!offeredYearSem.equalsIgnoreCase("")) {
                                    if (noLecHrs != 0) {
                                        if (subjectType != null) {
                                            try {
                                                if (categoryName.equalsIgnoreCase("")) {
                                                    categoryName = null;
                                                }
                                                Subject subject = new Subject(subId, subName, yId, noLecHrs, noTutHrs, noEvalHrs,noLabHrs, subjectType, categoryName);
                                                SubjectService subjectService = new SubjectServiceImpl();
                                                boolean res = subjectService.saveSubject(subject);
                                                if (res) {
                                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                    alert.setTitle(null);
                                                    alert.setHeaderText(null);
                                                    alert.setContentText("Success Adding Subject!");

                                                    alert.showAndWait();
                                                    txtSubID.setText("");
                                                    txtSubName.setText("");
                                                    txtYear.setText("");
                                                    txtLecHours.setText("");
                                                    txtTutHours.setText("");
                                                    txtEvalHours.setText("");
                                                    txtCategory.setText("");
                                                    subType.setValue("");
                                                    txtLabHours.setText("");
                                                } else {
                                                    Alert al = new Alert(Alert.AlertType.ERROR);
                                                    al.setTitle(null);
                                                    al.setContentText("Error Adding Subject!");
                                                    al.setHeaderText(null);
                                                    al.showAndWait();
                                                }
                                            } catch (SQLException ex) {
                                                log.log(Level.SEVERE, ex.getMessage());
                                            }
                                        } else {
                                            Alert al = new Alert(Alert.AlertType.ERROR);
                                            al.setTitle(null);
                                            al.setContentText("Select Subject Type!");
                                            al.setHeaderText(null);
                                            al.showAndWait();
                                        }

                                    } else {
                                        Alert al = new Alert(Alert.AlertType.ERROR);
                                        al.setTitle(null);
                                        al.setContentText("Number of Lecture Hours Empty!");
                                        al.setHeaderText(null);
                                        al.showAndWait();
                                    }

                                } else {
                                    Alert al = new Alert(Alert.AlertType.ERROR);
                                    al.setTitle(null);
                                    al.setContentText("Offered Year and Semester Empty!");
                                    al.setHeaderText(null);
                                    al.showAndWait();
                                }

                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Subject Name is Empty!");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }

                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Subject Code is Empty!");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    } catch (NumberFormatException ex) {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Enter in Correct Format for Lab of Lecturer hours!");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }

                } catch (NumberFormatException ex) {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Enter in Correct Format for Evaluation of Lecturer hours!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Enter in Correct Format for Tute of Lecturer hours!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (NumberFormatException ex) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Enter in Correct Format for Number of Lecturer hours!");
            al.setHeaderText(null);
            al.showAndWait();
        }


    }

    public void setSubjectType() {

        subType.setItems(subjectsLists);

    }

    public void getYearSem() {
        try {
            YearandSemesterService yearandSemesterService = new YearAndServiceImpl();
            ArrayList<YearAndSemester> list = yearandSemesterService.getAllDetails();
            for (YearAndSemester yearAndSemester : list
            ) {
                yearAndSemesters.add(yearAndSemester);
                yearSemName.add(yearAndSemester.getFullName());
            }
            TextFields.bindAutoCompletion(txtYear, yearSemName);

        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
    }

    @FXML
    void setText(ActionEvent event) {
        String type = subType.getValue();
        if (type.equalsIgnoreCase("Compulsory")) {
            txtCategory.setDisable(true);
        } else {
            txtCategory.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getYearSem();
        this.setSubjectType();
    }
}
