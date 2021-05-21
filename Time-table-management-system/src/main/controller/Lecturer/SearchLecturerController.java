package main.controller.Lecturer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Lecturer;
import main.service.BuildingService;
import main.service.DepartmentService;
import main.service.LecturerService;
import main.service.impl.BuildingServiceImpl;
import main.service.impl.DepartmentServiceImpl;
import main.service.impl.LectureServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchLecturerController implements Initializable {
    @FXML
    private TableView<Lecturer> tblGroupCount;

    @FXML
    private TextField txtLecturer;

    @FXML
    private Button btnSave;
    static ArrayList<Lecturer> list1=new ArrayList<>();
    static ArrayList<Lecturer> list2=new ArrayList<>();
    public static final Logger log = Logger.getLogger(SearchLecturerController.class.getName());


    @FXML
    void searchDetails(ActionEvent event) {
        list2.clear();
        String lecturerName=txtLecturer.getText();
        try{
            LecturerService lecturerService=new LectureServiceImpl();
            ArrayList<Lecturer> list = lecturerService.searchLecturerDetails(lecturerName);
            try{
                for (Lecturer str : list)
                {

                    DepartmentService departmentService=new DepartmentServiceImpl();
                    str.setDepartmentName(departmentService.searchDepartmentName(str.getDepartment()));

                    BuildingService buildingService=new BuildingServiceImpl();
                    str.setBuildingName(buildingService.searchBuildingName(str.getBuilding()));
                    list2.add(str);


                }

            this.setTableProperties();
            tblGroupCount.setItems(FXCollections.observableArrayList(list2));

            }catch (SQLException ex){
                log.log(Level.SEVERE,ex.getMessage());
            }
        }catch(Exception exception){
            log.log(Level.SEVERE,exception.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        getAllLecturers();
    }
    public void setTableProperties() {
        tblGroupCount.getSelectionModel().getTableView().getItems().clear();
        tblGroupCount.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("empId"));
        tblGroupCount.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("empName"));
        tblGroupCount.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Faculty"));
        tblGroupCount.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("DepartmentName"));
        tblGroupCount.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("Center"));
        tblGroupCount.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("buildingName"));
        tblGroupCount.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("designation"));
        tblGroupCount.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("Level"));
        tblGroupCount.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("rank"));
    }
    public  void getAllLecturers(){

        try {
            LecturerService lecturerService=new LectureServiceImpl();
            ArrayList<Lecturer> list = lecturerService.getAllLecturerDetails();
            try{
                for (Lecturer str : list)
                {


                    DepartmentService departmentService=new DepartmentServiceImpl();
                    str.setDepartmentName(departmentService.searchDepartmentName(str.getDepartment()));

                    BuildingService buildingService=new BuildingServiceImpl();
                    str.setBuildingName(buildingService.searchBuildingName(str.getBuilding()));

                    list1.add(str);
                    System.out.println("str = " + list1);

            }
            }catch (SQLException ex){
                log.log(Level.SEVERE,ex.getMessage());
            }

            tblGroupCount.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }


}
