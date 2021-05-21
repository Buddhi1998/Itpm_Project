package main.controller.Location;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Building;
import main.model.Room;
import main.service.BuildingService;
import main.service.RoomService;
import main.service.impl.BuildingServiceImpl;
import main.service.impl.RoomServiceImpl;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchRoomController implements Initializable {

    @FXML
    private TableView<Room> tblRoomSeach;

    @FXML
    private TextField txtBuildingSearch1;

    @FXML
    private TextField txtRoomSearch1;

    private RoomService roomService;
    private ArrayList<String> roomNameList = new ArrayList<>();
    private ArrayList<Room> roomIdList = new ArrayList<>();

    private BuildingService buildingService;
    private ArrayList<String> buildingNameList = new ArrayList<>();
    private ArrayList<Building> buildingList = new ArrayList<>();
    public static final Logger log = Logger.getLogger(SearchRoomController.class.getName());

    public SearchRoomController(){
        this.roomService = new RoomServiceImpl();
        this.buildingService = new BuildingServiceImpl();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        getAllBuildingDetails();
        getAllRoomDetails();
        getAllDetailsForSearch(null,null);
    }

    private void getAllRoomDetails() {
        try {
            ArrayList<Room> bList = this.roomService.getAllDetails();
            for (Room r1 : bList
            ) {
                roomIdList.add(r1);
                roomNameList.add(r1.getRoom());
            }
            TextFields.bindAutoCompletion(txtRoomSearch1, roomNameList);
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }


    private void getAllBuildingDetails() {
        try {
            ArrayList<Building> bList = this.buildingService.getAllDetails();
            for (Building p1 : bList
            ) {
                buildingList.add(p1);
                buildingNameList.add(p1.getBuilding());
            }
            TextFields.bindAutoCompletion(txtBuildingSearch1, buildingNameList);
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }




    public void searchDetails(){
        String rbuilding = null;
        String rroom = null;

        String building1 = txtBuildingSearch1.getText();
        String room = txtRoomSearch1.getText();
        for (Building b :this.buildingList){
            if (building1.equals(b.getBuilding())) {
                rbuilding = b.getBuilding();
            }
        }
        for (Room r : this.roomIdList
        ) {
            if (room.equals(r.getRoom())) {
                rroom = r.getRoom();
            }
        }
        getAllDetailsForSearch(rbuilding,rroom);
    }

    private void getAllDetailsForSearch(String rbuilding, String rroom) {

        try {
            ArrayList<Room> list = this.roomService.getAllDetailsForSearch(rbuilding,rroom);
            tblRoomSeach.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblRoomSeach.getSelectionModel().getTableView().getItems().clear();
        tblRoomSeach.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("building"));
        tblRoomSeach.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("room"));
        tblRoomSeach.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("capacity"));
    }
}

