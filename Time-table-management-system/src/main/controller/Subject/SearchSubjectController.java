package main.controller.Subject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Subject;
import main.service.SubjectService;
import main.service.YearandSemesterService;
import main.service.impl.SubjectServiceImpl;
import main.service.impl.YearAndServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchSubjectController implements Initializable {
    @FXML
    private TableView<Subject> tblSubjectCount;

    @FXML
    private TextField txtSubject;

    @FXML
    private Button btnSearch;

    public static final Logger log = Logger.getLogger(SearchSubjectController.class.getName());

    @FXML
    void searchDetails(ActionEvent event) {
        try {
            SubjectService subjectService=new SubjectServiceImpl();
            ArrayList<Subject> list = subjectService.searchSubjectDetails(txtSubject.getText());
            for (Subject str : list)
            {

                YearandSemesterService yearandSemesterService=new YearAndServiceImpl();
                str.setYearSem(yearandSemesterService.searchYearAndSemesterName(str.getOfferedYearSem()));
            }
            tblSubjectCount.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
    public void setTableProperties() {
        tblSubjectCount.getSelectionModel().getTableView().getItems().clear();
        tblSubjectCount.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("subId"));
        tblSubjectCount.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("subName"));
        tblSubjectCount.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("yearSem"));
        tblSubjectCount.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("noLecHrs"));
        tblSubjectCount.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("noTutHrs"));
        tblSubjectCount.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("noEvalHrs"));
        tblSubjectCount.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("noLabHrs"));
        tblSubjectCount.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("category"));
        tblSubjectCount.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("subType"));
    }
    public void getAllSubjects(){

        try {
            SubjectService subjectService=new SubjectServiceImpl();
            ArrayList<Subject> list = subjectService.getAllSubjectDetails();
            for (Subject str : list)
            {

                YearandSemesterService yearandSemesterService=new YearAndServiceImpl();
                str.setYearSem(yearandSemesterService.searchYearAndSemesterName(str.getOfferedYearSem()));
            }
            tblSubjectCount.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        this.getAllSubjects();
    }
}
