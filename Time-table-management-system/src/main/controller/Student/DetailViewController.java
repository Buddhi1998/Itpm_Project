package main.controller.Student;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.AllGroupDetail;
import main.service.MainGroupService;
import main.service.impl.MainGroupServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DetailViewController implements Initializable {

    @FXML
    private Label lblYearSemName;

    @FXML
    private TableView<AllGroupDetail> tblGroupMainTable;

    private MainGroupService mainGroupService = new MainGroupServiceImpl();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllStudentGroupDetails();
    }

    private void loadAllStudentGroupDetails(){
        ArrayList<AllGroupDetail> allGroupDetails = null;
        try {
            allGroupDetails = this.mainGroupService.getAllGroupDetails();

            tblGroupMainTable.setItems(FXCollections.observableArrayList(allGroupDetails));

            tblGroupMainTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("accdamicYearAndSemester"));
            tblGroupMainTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("programme"));
            tblGroupMainTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("groupeName"));
            tblGroupMainTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("groupeNumber"));
            tblGroupMainTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("groupeId"));
            tblGroupMainTable.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("subGroupeNo"));
            tblGroupMainTable.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("subGroupeId"));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}


