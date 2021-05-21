package main.controller.Location;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Building;
import main.service.BuildingService;
import main.service.impl.BuildingServiceImpl;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchBuildingController implements Initializable {

    @FXML
    private TableView<Building> tblBuildingSeach;

    @FXML
    private TextField txtCenterSearch;

    @FXML
    private TextField txtBuildingSearch;

    private BuildingService buildingService;
    private ArrayList<String> buildingList = new ArrayList<>();
    private ArrayList<String> centerList = new ArrayList<>();
    private ArrayList<Building> bIdList = new ArrayList<>();
    public static final Logger log = Logger.getLogger(SearchBuildingController.class.getName());

    public SearchBuildingController(){
        this.buildingService = new BuildingServiceImpl();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        getAllAllBuildingsDetails();
        getAllDetailsForSearch(null,null);
    }


    public void getAllAllBuildingsDetails() {
        try {
            ArrayList<Building> bList = this.buildingService.getAllAllRoomDetails();
            for (Building p1 : bList
            ) {
                if(!centerList.isEmpty()){
                    int duplicateCount=0;
                    for (String b1: centerList
                         ) {
                        if(b1.equals(p1.getCenter())){
                            duplicateCount++;
                        }
                    }
                    if(duplicateCount==0){
                        centerList.add(p1.getCenter());
                    }
                }else{
                    centerList.add(p1.getCenter());
                }

                buildingList.add(p1.getBuilding());
                bIdList.add(p1);
            }
            TextFields.bindAutoCompletion(txtBuildingSearch, buildingList);
            TextFields.bindAutoCompletion(txtCenterSearch, centerList);
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @FXML
    void searchDetails(ActionEvent event) {

        String building = txtCenterSearch.getText();
        String center = txtBuildingSearch.getText();

        getAllDetailsForSearch(building,center);
    }

    private void getAllDetailsForSearch(String bcenter,String bbuilding) {
        try {
            ArrayList<Building> list = this.buildingService.getAllDetailsForSearch(bcenter,bbuilding);
            tblBuildingSeach.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblBuildingSeach.getSelectionModel().getTableView().getItems().clear();
        tblBuildingSeach.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("center"));
        tblBuildingSeach.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("building"));
    }

}

