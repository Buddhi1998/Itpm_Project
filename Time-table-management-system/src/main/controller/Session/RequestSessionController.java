package main.controller.Session;

import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.model.*;
import main.service.*;
import main.service.impl.*;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestSessionController implements Initializable {

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<?> cmbDate;

    @FXML
    private JFXTimePicker toTime;

    @FXML
    private JFXTimePicker fromTime;

    @FXML
    private TextField txtLecturer;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtTag;

    @FXML
    private TextField txtGroup;

    @FXML
    private RadioButton btnRadioMain;

    @FXML
    private RadioButton btnRadioSub;

    @FXML
    private TextField txtBuilding;

    @FXML
    private ComboBox<String> txtCenter;

    @FXML
    void getCenter(ActionEvent event) {
        //Method get Center
    }

    @FXML
    private TextField txtRoom;
    private AutoCompletionBinding<String> autoCompletionBindings;
    private LecturerService lecturerServicess;
    private SubjectService subjectServicess;
    private TagService tagServicess;
    private MainGroupService mainGroupServicess;
    private SubGroupService subGroupServicess;
    private List<Lecturer> lectureListss;
    private List<Subject> subjectListss;
    private List<Tag> tagListss;
    private List<Object> mainGroupListss;
    private List<Building> buildingsListss;
    private List<String> lectureNameListss;
    private List<String> subNameListss;
    private List<String> tagNameListss;
    private List<String> maingroupNameListss;
    private List<String> buildingNameListss;

    private ObservableList<String> centerList= FXCollections.observableArrayList("Malabe","Metro","Kurunegala","Kandy","Matara","SLIIT Academy","Jaffna");
    public static final Logger log = Logger.getLogger(RequestSessionController.class.getName());

    @FXML
    void loadGroupDetails(ActionEvent event) {
        //Method load group details
    }
    private void loadAllMainGroupDetails() {
        try {
            ArrayList<MainGroup> mainLists = this.mainGroupServicess.getAllMainGroupDetails();
            maingroupNameListss.clear();
            mainGroupListss.clear();
            if (autoCompletionBindings != null) {
                autoCompletionBindings.dispose();
            }
            for (MainGroup m1 : mainLists
            ) {
                maingroupNameListss.add(m1.getGroupid());
                mainGroupListss.add(m1);
            }
            autoCompletionBindings = TextFields.bindAutoCompletion(txtGroup, maingroupNameListss);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
    private void loadAllSubGroupDetails() {
        try {
            ArrayList<SubGroup> subLists = this.subGroupServicess.getAllSubGroupDetails(0);

            maingroupNameListss.clear();
            mainGroupListss.clear();
            if (autoCompletionBindings != null) {
                autoCompletionBindings.dispose();
            }
            for (SubGroup s1 : subLists
            ) {
                maingroupNameListss.add(s1.getSubgroupid());
                mainGroupListss.add(s1);
            }
            autoCompletionBindings = TextFields.bindAutoCompletion(txtGroup, maingroupNameListss);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
    public void loadAllBuildings(){
        BuildingService buildingService=new BuildingServiceImpl();
        try {
            ArrayList<Building> listBuildings=buildingService.getAllDetails();
            if (autoCompletionBindings != null) {
                autoCompletionBindings.dispose();
            }
            for (Building b1 : listBuildings
            ) {
                buildingNameListss.add(b1.getBuilding());
                buildingsListss.add(b1);
            }

            autoCompletionBindings = TextFields.bindAutoCompletion(txtBuilding, buildingNameListss);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }

    }
    @FXML
    void SelectTag(ActionEvent event) {
        String tag=txtTag.getText();
        if((tag.equalsIgnoreCase("Lecture")||(tag.equalsIgnoreCase("Tute")))){

            btnRadioMain.setSelected(true);
            btnRadioSub.setSelected(false);
            this.loadAllMainGroupDetails();
        }else{
            btnRadioSub.setSelected(true);
            btnRadioMain.setSelected(false);
            this.loadAllSubGroupDetails();


        }
    }

    @FXML
    void saveDetails(ActionEvent event) {
        //Method for save details
    }
    public void setCenter(){

        txtCenter.setItems(centerList);

    }
    public void loadAllLectureDetails() {
        try {
            ArrayList<Lecturer> lec = lecturerServicess.getAllLecturerDetails();
            for (Lecturer ls : lec
            ) {
                lectureNameListss.add(ls.getEmpName());
                lectureListss.add(ls);
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
        TextFields.bindAutoCompletion(txtLecturer, lectureNameListss);
    }
    public void loadAllSubjectDetails() {
        try {
            ArrayList<Subject> subjects = subjectServicess.getAllSubjectDetails();
            for (Subject s : subjects
            ) {
                subjectListss.add(s);
                subNameListss.add(s.getSubName());
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
        TextFields.bindAutoCompletion(txtSubject, subNameListss);
    }
    public void loadAllTagDetails() {
        try {
            ArrayList<Tag> tag = tagServicess.getAllDetails();
            for (Tag t : tag
            ) {
                tagListss.add(t);
                tagNameListss.add(t.getTagName());
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
        TextFields.bindAutoCompletion(txtTag, tagNameListss);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lecturerServicess=new LectureServiceImpl();
        lectureNameListss = new ArrayList<>();
        lectureListss = new ArrayList<>();
        subjectServicess=new SubjectServiceImpl();
        subNameListss=new ArrayList<>();
        subjectListss=new ArrayList<>();
        tagServicess=new TagServiceImpl();
        tagListss=new ArrayList<>();
        tagNameListss=new ArrayList<>();
        mainGroupServicess=new MainGroupServiceImpl();
        mainGroupListss=new ArrayList<>();
        maingroupNameListss=new ArrayList<>();
        subGroupServicess=new SubGroupServiceImpl();
        buildingsListss=new ArrayList<>();
        buildingNameListss=new ArrayList<>();
        this.loadAllLectureDetails();
        this.loadAllSubjectDetails();
        this.loadAllTagDetails();
        this.loadAllBuildings();
        this.setCenter();
    }
}
