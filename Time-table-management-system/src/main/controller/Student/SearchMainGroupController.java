package main.controller.Student;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.MainGroupCount;
import main.model.Programme;
import main.model.YearAndSemester;
import main.service.MainGroupService;
import main.service.ProgrammeService;
import main.service.YearandSemesterService;
import main.service.impl.MainGroupServiceImpl;
import main.service.impl.ProgrammeServiceImpl;
import main.service.impl.YearAndServiceImpl;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class SearchMainGroupController implements Initializable {

    @FXML
    private TableView<MainGroupCount> tblGroupCount;

    @FXML
    private TextField txtyear;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField txtProgramme;


    private ProgrammeService programmeService;
    private YearandSemesterService yearandSemesterService;
    private ArrayList<String> pNameList = new ArrayList<>();
    private ArrayList<Programme> pIdList = new ArrayList<>();
    private ArrayList<YearAndSemester> yearSemIdList = new ArrayList<>();
    private ArrayList<String> yearSemesterNameList = new ArrayList<>();
    private MainGroupService mainGroupService;
    public static final Logger log = Logger.getLogger(SearchMainGroupController.class.getName());

    public SearchMainGroupController() {
        this.programmeService = new ProgrammeServiceImpl();
        this.yearandSemesterService = new YearAndServiceImpl();
        this.mainGroupService = new MainGroupServiceImpl();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        getAllProgrammeDetails();
        getAllYearSemesterDetails();
        getAllGroupCount(0,0);
    }

    public void getAllProgrammeDetails() {
        try {
            ArrayList<Programme> pList = this.programmeService.getAllDetails();
            for (Programme p1 : pList
            ) {
                pNameList.add(p1.getProgrammeName());
                pIdList.add(p1);
            }
            TextFields.bindAutoCompletion(txtProgramme, pNameList);
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    private void getAllYearSemesterDetails() {
        try {
            ArrayList<YearAndSemester> list = this.yearandSemesterService.getAllDetails();
            for (YearAndSemester y : list
            ) {
                yearSemIdList.add(y);
                yearSemesterNameList.add(y.getFullName());
            }
            TextFields.bindAutoCompletion(txtyear, yearSemesterNameList);
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void searchDetails(){
        int pId = 0;
        int yId = 0;
        int yearCount = 0;
        int programmeCount = 0;
        String yName = txtyear.getText();
        String pName = txtProgramme.getText();
        for (Programme p : this.pIdList
        ) {
            if (pName.equals(p.getProgrammeName())) {
                pId = p.getProgrammeId();
                programmeCount++;
            }
        }
        for (YearAndSemester y : this.yearSemIdList) {
            if (yName.equals(y.getFullName())) {
                yId = y.getId();
                yearCount++;
            }
        }
        if(programmeCount==0){
            pId=0;
        }
        if(yearCount==0){
            yId=0;
        }
        getAllGroupCount(yId,pId);
    }

    public void getAllGroupCount(int yearId,int semId){

        try {
            ArrayList<MainGroupCount> list = this.mainGroupService.getAllGroupCount(yearId,semId);
            tblGroupCount.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblGroupCount.getSelectionModel().getTableView().getItems().clear();
        tblGroupCount.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("yearandsemester"));
        tblGroupCount.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("programme"));
        tblGroupCount.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("groupcount"));
    }


}
