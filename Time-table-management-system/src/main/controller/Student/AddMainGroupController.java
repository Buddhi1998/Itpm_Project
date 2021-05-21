package main.controller.Student;


import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.model.MainGroup;
import main.model.Programme;
import main.model.YearAndSemester;
import main.service.MainGroupService;
import main.service.ProgrammeService;
import main.service.YearandSemesterService;
import main.service.impl.MainGroupServiceImpl;
import main.service.impl.ProgrammeServiceImpl;
import main.service.impl.YearAndServiceImpl;
import org.controlsfx.control.textfield.TextFields;


public class AddMainGroupController implements Initializable {

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtGroupCount;

    @FXML
    private TextField txtAYear;

    @FXML
    private TextField txtAProgramme;

    private ProgrammeService programmeService;
    private YearandSemesterService yearandSemesterService;
    private ArrayList<String> pName = new ArrayList<>();
    private ArrayList<Programme> pId = new ArrayList<>();
    private ArrayList<YearAndSemester> yearSemId = new ArrayList<>();
    private ArrayList<String> yearSemesterName = new ArrayList<>();
    private MainGroupService mainGroupService;
    public static final Logger log = Logger.getLogger(AddMainGroupController.class.getName());

    public AddMainGroupController() {
        this.programmeService = new ProgrammeServiceImpl();
        this.yearandSemesterService = new YearAndServiceImpl();
        this.mainGroupService = new MainGroupServiceImpl();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAllProgrammeDetails();
        getAllYearSemesterDetails();
    }

    private void getAllYearSemesterDetails() {
        try {
            ArrayList<YearAndSemester> list = this.yearandSemesterService.getAllDetails();
            for (YearAndSemester y : list
            ) {
                yearSemId.add(y);
                yearSemesterName.add(y.getFullName());
            }
            TextFields.bindAutoCompletion(txtAYear, yearSemesterName);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void getAllProgrammeDetails() {
        try {
            ArrayList<Programme> list = this.programmeService.getAllDetails();
            for (Programme p : list
            ) {
                pName.add(p.getProgrammeName());
                pId.add(p);
            }
            TextFields.bindAutoCompletion(txtAProgramme, pName);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @FXML
    void saveDetails(ActionEvent event) {

        String pName = txtAProgramme.getText();
        int pId = 0;
        int yId = 0;
        int yearCount = 0;
        int programmeCount = 0;
        String yName = txtAYear.getText();
        try {
            int groupCount = Integer.parseInt(txtGroupCount.getText());
            for (Programme p : this.pId
            ) {
                if (pName.equals(p.getProgrammeName())) {
                    pId = p.getProgrammeId();
                    programmeCount++;
                }
            }
            for (YearAndSemester y : this.yearSemId) {
                if (yName.equals(y.getFullName())) {
                    yId = y.getId();
                    yearCount++;
                }
            }

            if (pName != null) {
                if (yName != null) {
                    if (programmeCount != 0) {
                        if (yearCount != 0) {
                            boolean isAdded = false;
                            int count = this.mainGroupService.getCountAccordingToName(yName + "." + pName);
                            for (int i = 0; i < groupCount; i++) {
                                MainGroup m = new MainGroup();
                                String generateId = null;
                                if (count < 9) {
                                    generateId = yName + "." + pName + ".0" + (count + 1);
                                } else {
                                    generateId = yName + "." + pName + "." + (count + 1);
                                }

                                m.setGroupid(generateId);
                                m.setProgrammeid(pId);
                                m.setSemid(yId);
                                m.setMgroupName(yName + "." + pName);
                                m.setGroupNumber(count + 1);
                                isAdded = this.mainGroupService.saveMainGroupId(m);
                                count++;
                            }
                            if (isAdded) {
                                Alert al = new Alert(Alert.AlertType.INFORMATION);
                                al.setTitle(null);
                                al.setContentText("Added Successfully ");
                                al.setHeaderText(null);
                                al.showAndWait();
                                count = 0;
                                txtAProgramme.setText(null);
                                txtAYear.setText(null);
                                txtGroupCount.setText(null);
                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Added Fail  ");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("This Year&Semester Name Is not Exists In this System");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("This Programme Is not Exists In this System");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Year Field is Empty");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("ProgrammeName Field is Empty");
                al.setHeaderText(null);
                al.showAndWait();
            }

        } catch (NumberFormatException e) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Enter Numeric Value To This Field");
            al.setHeaderText(null);
            al.showAndWait();
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }

    }

}
