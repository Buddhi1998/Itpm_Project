package main.controller.Lecturer;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.Building;
import main.model.Department;
import main.model.Lecturer;
import main.service.BuildingService;
import main.service.DepartmentService;
import main.service.LecturerService;
import main.service.impl.BuildingServiceImpl;
import main.service.impl.DepartmentServiceImpl;
import main.service.impl.LectureServiceImpl;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewLecturerController implements Initializable {

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Lecturer> tblMainGroup;

    @FXML
    private TableColumn<Lecturer, Boolean> colEdit;

    @FXML
    private TableColumn<Lecturer, Boolean> colDelete;

    @FXML
    private TextField txtDepartment;

    @FXML
    private TextField txtName;



    @FXML
    private ComboBox<String> txtCenter;

    @FXML
    private ComboBox<String> txtDesignation;

    @FXML
    private TextField txtFaculty;

    @FXML
    private TextField txtBuilding;
    int empId=0;
    int level=0;
    String center ="";
    String rank="";
    int dId=0;
    int bId=0;
    int dCount=0;
    int bCount=0;
    private ArrayList<Department> departmentsId = new ArrayList<>();
    private ArrayList<String> departmentName = new ArrayList<>();
    private ArrayList<Building> buildingsId = new ArrayList<>();
    private ArrayList<String> buildingName = new ArrayList<>();
    private ObservableList<String> designationList= FXCollections.observableArrayList("Professor","Assistant Professor","Senior Lecturer(HG)","Senior Lecturer","Lecturer","Assistant Lecturer","Instructors");
    private ObservableList<String> centerList= FXCollections.observableArrayList("Malabe","Metro","Kurunegala","Kandy","Matara","SLIIT Academy","Jaffna");
    public static final Logger log = Logger.getLogger(MainGroupController.class.getName());


    @FXML
    void getDesignation(ActionEvent event) {

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
        }else if(designation.equalsIgnoreCase("Instructors")){
            level=7;
        }
        rank=level+"."+empId;
    }


    @FXML
    void updateLecturer(ActionEvent event) {
        try{

            String empName=txtName.getText();
            String faculty=txtFaculty.getText();
            String department=txtDepartment.getText();
            String center1=txtCenter.getValue();
            String building=txtBuilding.getText();
            String designation=txtDesignation.getValue();


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


            if(!empName.equalsIgnoreCase("")){
                if(!faculty.equalsIgnoreCase("")){
                    if(!department.equalsIgnoreCase("")){
                        if(!center.equalsIgnoreCase("")){
                            if(!building.equalsIgnoreCase("")){
                                if(!designation.equalsIgnoreCase("")){
                                    Lecturer lecturer=new Lecturer(empId,empName,faculty,dId,center1,designation,bId,level,rank);

                                    LecturerService lecturerService=new LectureServiceImpl();
                                    boolean res =lecturerService.updateLecturer(lecturer);
                                    if(res){
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle(null);
                                        alert.setHeaderText(null);
                                        alert.setContentText("Success Updating Lecturer!");

                                        alert.showAndWait();
                                        txtName.setText(" ");
                                        txtFaculty.setText(" ");
                                        txtDepartment.setText(" ");
                                        txtDesignation.setValue(" ");
                                        txtDepartment.setText(" ");
                                        txtCenter.setValue(" ");
                                        txtBuilding.setText(" ");
                                    }else{
                                        Alert al = new Alert(Alert.AlertType.ERROR);
                                        al.setTitle(null);
                                        al.setContentText("Error Updating Lecturer!");
                                        al.setHeaderText(null);
                                        al.showAndWait();
                                    }
                                    this.setTableProperties();
                                    getAllLecturers();

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
        }catch (SQLException ex){
            log.log(Level.SEVERE,ex.getMessage());
        }

    }
    public void setTableProperties() {
        tblMainGroup.getSelectionModel().getTableView().getItems().clear();
        tblMainGroup.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("empId"));
        tblMainGroup.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("empName"));
        tblMainGroup.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Faculty"));
        colEdit.setCellFactory(cellFactoryBtnEdit);
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        getAllLecturers();
        setDesignation();
        getAllDepartmentDetails();
        setCenter();

    }
    public void getAllLecturers(){

        try {
            LecturerService lecturerService=new LectureServiceImpl();
            ArrayList<Lecturer> list = lecturerService.getAllLecturerDetails();

            tblMainGroup.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
    Callback<TableColumn<Lecturer, Boolean>, TableCell<Lecturer, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<Lecturer, Boolean>, TableCell<Lecturer, Boolean>>() {
                @Override
                public TableCell<Lecturer, Boolean> call(TableColumn<Lecturer, Boolean> param) {
                    final TableCell<Lecturer, Boolean> cell = new TableCell<Lecturer, Boolean>() {
                        FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        final Button btnEdit = new Button();

                        @Override
                        public void updateItem(Boolean check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnEdit.setOnAction(e -> {
                                    Lecturer lecturer = getTableView().getItems().get(getIndex());
                                    setMainGroupDetailsToTheField(lecturer);
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

                        private void setMainGroupDetailsToTheField(Lecturer m) {
                            empId=m.getEmpId();
                            txtName.setText(m.getEmpName());
                            txtFaculty.setText(m.getFaculty());
                            try{
                                DepartmentService departmentService=new DepartmentServiceImpl();
                                m.setDepartmentName(departmentService.searchDepartmentName(m.getDepartment()));
                                BuildingService buildingService=new BuildingServiceImpl();
                                m.setBuildingName(buildingService.searchBuildingName(m.getBuilding()));

                            }catch (SQLException ex){
                                log.log(Level.SEVERE,ex.getMessage());
                            }
                            txtDepartment.setText(m.getDepartmentName());
                            txtCenter.setValue(m.getCenter());
                            center=m.getCenter();
                            txtDesignation.setValue(m.getDesignation());
                            txtBuilding.setText(m.getBuildingName());
                        }
                    };
                    return cell;
                }
            };

    Callback<TableColumn<Lecturer, Boolean>, TableCell<Lecturer, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<Lecturer, Boolean>, TableCell<Lecturer, Boolean>>() {
                @Override
                public TableCell<Lecturer, Boolean> call(TableColumn<Lecturer, Boolean> param) {
                    final TableCell<Lecturer, Boolean> cell = new TableCell<Lecturer, Boolean>() {
                        FontAwesomeIconView iconViewDelete = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        final Button btnDelete = new Button();

                        @Override
                        public void updateItem(Boolean check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnDelete.setOnAction(e -> {
                                    Lecturer lecturer = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteLecturer(lecturer.getEmpId());
                                    }
                                });
                                btnDelete.setStyle("-fx-background-color: transparent;");
                                btnDelete.setGraphic(iconViewDelete);
                                setGraphic(btnDelete);
                                setAlignment(Pos.CENTER);
                                setText(null);

                            }
                        }
                    };
                    return cell;
                }
            };

    public void deleteLecturer(int id) {

        try{
            LecturerService lecturerService=new LectureServiceImpl();
            lecturerService.deleteLecturerDetails(id);
            this.setTableProperties();
            getAllLecturers();
        }catch (SQLException ex){
            log.log(Level.SEVERE,ex.getMessage());
        }
    }
    public void setDesignation(){

        txtDesignation.setItems(designationList);

    }
    private void getAllDepartmentDetails() {
        try {
            DepartmentService departmentService=new DepartmentServiceImpl();
            ArrayList<Department> list = departmentService.getAllDetails();
            for (Department department : list
            ) {
                departmentsId.add(department);
                departmentName.add(department.getDepartmentName());
            }
            TextFields.bindAutoCompletion(txtDepartment, departmentName);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
    @FXML
    void getCenter(ActionEvent event) {
        buildingName.clear();
        buildingsId.clear();
        String center1=txtCenter.getValue();

        try{
            BuildingService buildingService=new BuildingServiceImpl();
            ArrayList<Building> list =buildingService.searchBuildingDetailsByCenter(center1);
            for (Building building : list
            ) {
                buildingsId.add(building);
                buildingName.add(building.getBuilding());

            }
            TextFields.bindAutoCompletion(txtBuilding, buildingName);

        }catch (SQLException ex){
            log.log(Level.SEVERE,ex.getMessage());
        }

    }
    public void setCenter(){

        txtCenter.setItems(centerList);

    }
}
