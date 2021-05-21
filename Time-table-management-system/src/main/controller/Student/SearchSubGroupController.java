package main.controller.Student;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.MainGroup;
import main.model.SubGroupCount;
import main.service.MainGroupService;
import main.service.SubGroupService;
import main.service.impl.MainGroupServiceImpl;
import main.service.impl.SubGroupServiceImpl;
import org.controlsfx.control.textfield.TextFields;


public class SearchSubGroupController implements Initializable {

    @FXML
    private TextField txtMainGroup;

    @FXML
    private TableView<SubGroupCount> tblMainGroup;

    private SubGroupService subGroupService;
    private MainGroupService mainGroupService;
    private List<String> groupIdList = new ArrayList<>();
    private List<MainGroup> groupList = new ArrayList<>();
    public static final Logger log = Logger.getLogger(SearchSubGroupController.class.getName());

    public SearchSubGroupController(){
        subGroupService = new SubGroupServiceImpl();
        mainGroupService = new MainGroupServiceImpl();
    }


    @FXML
    void searchDetails(ActionEvent event) {
        String groupId = txtMainGroup.getText();
        int mId=0;
        int groupCount=0;
        for (MainGroup m : this.groupList
        ) {
            if (groupId.equals(m.getGroupid())) {
                mId = m.getId();
                groupCount++;
            }
        }
        if(groupCount!=0){
            this.getAllGroupCount(mId);
        }else{
            this.getAllGroupCount(0);

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        this.getAllGroupCount(0);
        this.getAllMainGroup();
    }

    public void getAllMainGroup(){
        try {
            ArrayList<MainGroup> mainList = mainGroupService.getAllMainGroupDetails();
            for (MainGroup m:mainList
                 ) {
                groupIdList.add(m.getGroupid());
                groupList.add(m);
            }
            TextFields.bindAutoCompletion(txtMainGroup, groupIdList);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }

    }

    public void getAllGroupCount(int yearId){

        try {
            ArrayList<SubGroupCount> list = this.subGroupService.getAllSubGroupCount(yearId);
            tblMainGroup.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblMainGroup.getSelectionModel().getTableView().getItems().clear();
        tblMainGroup.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("groupId"));
        tblMainGroup.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("subGroupCount"));
    }



}
