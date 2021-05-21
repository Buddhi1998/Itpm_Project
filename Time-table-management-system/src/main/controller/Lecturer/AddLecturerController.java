package main.controller.Lecturer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import main.model.Building;
import main.model.Department;
import main.model.Lecturer;
import main.service.BuildingService;
import main.service.DepartmentService;
import main.service.LecturerService;
import main.service.impl.BuildingServiceImpl;
import main.service.impl.DepartmentServiceImpl;
import main.service.impl.LectureServiceImpl;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class AddLecturerController implements Initializable{

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtDepartment;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtEmpID;


    @FXML
    private ComboBox<String> txtCenter;

    @FXML
    private ComboBox<String> txtFaculty;

    @FXML
    private TextField txtBuilding;

    @FXML
    private ComboBox<String> txtDesignation;
    int level=0;
    int dId =0;
    int bId =0;
    int dCount=0;
    int bCount=0;
    @FXML
    private TextField txtrank;
    private ArrayList<Department> departmentsId = new ArrayList<>();
    private ArrayList<String> departmentName = new ArrayList<>();
    private ArrayList<Building> buildingsId = new ArrayList<>();
    private ArrayList<String> buildingName = new ArrayList<>();
    private ObservableList<String> list= FXCollections.observableArrayList("Computing","Engineering","Business Management","Architecture","Quantity Surveying","Science","Hospitality");
    private ObservableList<String> centerList= FXCollections.observableArrayList("Malabe","Metro","Kurunegala","Kandy","Matara","SLIIT Academy","Jaffna");
    private ObservableList<String> designationList= FXCollections.observableArrayList("Professor","Assistant Professor","Senior Lecturer(HG)","Senior Lecturer","Lecturer","Assistant Lecturer","Instructors");
    public static final Logger log = Logger.getLogger(AddLecturerController.class.getName());

    @FXML
    void saveDetails(ActionEvent event) {
        try {
            int empId=Integer.parseInt(txtEmpID.getText());
            String name=txtEmpName.getText();
            String faculty=txtFaculty.getValue();
            String department=txtDepartment.getText();
            String center=txtCenter.getValue();
            String building=txtBuilding.getText();
            String designation=txtDesignation.getValue();
            txtrank.setText(level+"."+empId);
            String rank=level+"."+empId;

            for (Department department1 : this.departmentsId) {
                if (department.equals(department1.getDepartmentName())) {
                    dId = department1.getDepartmentId();
                    dCount++;
                }
            }
            for (Building building1 : this.buildingsId) {
                if (building.equals(building1.getBuilding())) {
                    bId = building1.getBid();
                    bCount++;
                }
            }


            if (empId != 0 && String.valueOf(empId).length()==6) {
            if(name!=null){
                if(faculty!=null){
                    if(department!=null){
                        if(center!=null){
                            if(building!=null){
                                if(designation!=null){
                                    Lecturer lecturer = new Lecturer(empId, name, faculty, dId, center, designation, bId, level, rank);
                                    LecturerService lecturerService = new LectureServiceImpl();

                                    boolean res = lecturerService.saveLecturer(lecturer);
                                    if(res){
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle(null);
                                        alert.setHeaderText(null);
                                        alert.setContentText("Success Adding Employee!");

                                        alert.showAndWait();
                                        txtEmpID.setText("");
                                        txtEmpName.setText("");
                                        txtFaculty.setValue("");
                                        txtDepartment.setText("");
                                        txtDesignation.setValue("");
                                        txtDepartment.setText("");
                                        txtrank.setText("");
                                        txtCenter.setValue("");
                                        txtBuilding.setText("");
                                    }else{
                                        Alert al = new Alert(Alert.AlertType.ERROR);
                                        al.setTitle(null);
                                        al.setContentText("Error Adding Employee!");
                                        al.setHeaderText(null);
                                        al.showAndWait();
                                    }
                                }else{
                                    Alert al = new Alert(Alert.AlertType.ERROR);
                                    al.setTitle(null);
                                    al.setContentText("Employee Designation is Empty!");
                                    al.setHeaderText(null);
                                    al.showAndWait();
                                }

                            }else{
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Building is Empty!");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }

                        }else{
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Employee Center is Empty!");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }

                    }else{
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Employee Department is Empty!");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }

                }else{
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Employee Faculty is Empty!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }

            }else{
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Employee Name is Empty!");
                al.setHeaderText(null);
                al.showAndWait();
            }


        }    else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Employee ID is Empty or Wrong!");
            al.setHeaderText(null);
            al.showAndWait();
        }
        } catch (NumberFormatException exception) {
            log.log(Level.SEVERE,exception.getMessage());
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Enter Six digit Numeric Value for Employee ID!");
            al.setHeaderText(null);
            al.showAndWait();
        }catch (Exception ex){
            log.log(Level.SEVERE,ex.getMessage());
        }
    }

    @FXML
    void getFaculty(ActionEvent event) {
        String faculty=txtFaculty.getValue();
        log.log(Level.SEVERE,faculty);
    }


    public void setRank() {
        String empId=txtEmpID.getText();
        txtrank.setText(level+"."+empId);
    }
    private void getAllDepartmentDetails() {
        departmentName.clear();
        departmentsId.clear();
        try {
            DepartmentService departmentService=new DepartmentServiceImpl();
            ArrayList<Department> listDept = departmentService.getAllDetails();
            for (Department department : listDept
            ) {
                departmentsId.add(department);
                departmentName.add(department.getDepartmentName());
            }
            TextFields.bindAutoCompletion(txtDepartment, departmentName);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }


    public void setFaculty(){

        txtFaculty.setItems(list);

    }
    public void setCenter(){

        txtCenter.setItems(centerList);

    }
    public void setdesignation(){

        txtDesignation.setItems(designationList);

    }
    public void getDesignation(){
        String designation=txtDesignation.getValue();
        if(designation.equalsIgnoreCase("Professor")){
            level=1;
        }else if(designation.equalsIgnoreCase("Assistant Professor")){
            level=2;
        }else if(designation.equalsIgnoreCase("Senior Lecturer(HG)")){
            level=3;
        }else if(designation.equalsIgnoreCase("Senior Lecturer")){
            level=4;
        }else if(designation.equalsIgnoreCase("Lecturer")){
            level=5;
        }else if(designation.equalsIgnoreCase("Assistant Lecturer")){
            level=6;
        }else if(designation.equalsIgnoreCase("Instructors")) {
            level = 7;
        }
        setRank();
    }
    private AutoCompletionBinding<String> autoCompletionBinding;
    @FXML
    void getCenter(ActionEvent event) {
        buildingsId.clear();
        buildingName.clear();
        String center=txtCenter.getValue();
        log.log(Level.SEVERE,center);

        try{
            BuildingService buildingService=new BuildingServiceImpl();
            ArrayList<Building> listBuild =buildingService.searchBuildingDetailsByUsingCenter(center);
            for (Building building : listBuild
            ) {
                buildingsId.add(building);
                buildingName.add(building.getBuilding());
                log.log(Level.SEVERE,building.getBuilding());
            }
            if(autoCompletionBinding!=null){
                autoCompletionBinding.dispose();
            }
            autoCompletionBinding=TextFields.bindAutoCompletion(txtBuilding, buildingName);

        }catch (SQLException ex){
            log.log(Level.SEVERE,ex.getMessage());
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAllDepartmentDetails();
        setFaculty();
        setCenter();
        setdesignation();
    }
}
