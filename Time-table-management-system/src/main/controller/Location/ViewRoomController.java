package main.controller.Location;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.Building;
import main.model.Room;
import main.service.BuildingService;
import main.service.RoomService;
import main.service.impl.BuildingServiceImpl;
import main.service.impl.RoomServiceImpl;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewRoomController implements Initializable {

    @FXML
    private TableView<Room> tblRoomView;

    @FXML
    private TableColumn<Room, Boolean> editBuildingView;

    @FXML
    private TableColumn<Room, Boolean> removeBuildingView;

    @FXML
    private TextField txtBuildingEdit1;

    @FXML
    private TextField txtRoomEdit1;

    @FXML
    private Button btnRoomUpdate;

    @FXML
    private TextField txtCapacitiesEdit1;

    @FXML
    private ComboBox<String> cmbCenterEdit;

    @FXML
    private ComboBox<String> cmbLabType;

    private ArrayList<Building> buildingsId = new ArrayList<>();
    private ArrayList<String> buildingName = new ArrayList<>();
    private RoomService roomService;
    private int roomId;
    public static final Logger log = Logger.getLogger(ViewRoomController.class.getName());


    @FXML
    void getBuilding(ActionEvent event) {
        String center = cmbCenterEdit.getValue();

        try {
            BuildingService buildingService = new BuildingServiceImpl();
            ArrayList<Building> list = buildingService.searchBuildingDetailsByUsingCenter(center);
            for (Building building : list
            ) {
                buildingsId.add(building);
                buildingName.add(building.getBuilding());
            }
            TextFields.bindAutoCompletion(txtBuildingEdit1, buildingName);

        } catch (SQLException ex) {
            log.log(Level.SEVERE,ex.getMessage());
        }
    }


    public ViewRoomController() {
        this.roomService = new RoomServiceImpl();
    }

    public void getAllDetails() {
        try {
            ArrayList<Room> listB = this.roomService.getAllDetails();
            tblRoomView.setItems(FXCollections.observableArrayList(listB));
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }


    @FXML
    void updateRoomDetails(ActionEvent event) {

        String updateCenter = cmbCenterEdit.getValue();
        String updateBuilding = txtBuildingEdit1.getText();
        String updateRoom = txtRoomEdit1.getText();
        String labType = cmbLabType.getSelectionModel().getSelectedItem();
        int updateCapacity = Integer.parseInt(txtCapacitiesEdit1.getText());
        int buildid = 0;
        for (Building b : this.buildingsId
        ) {
            if (updateBuilding.equals(b.getBuilding())) {
                buildid = b.getBid();
            }
        }

        if (updateCenter != null) {
            if (updateBuilding != null) {
                if (updateRoom != null) {
                    if (updateCapacity != 0) {
                        if(roomId != 0) {

                            Room r1 = new Room();
                            r1.setCenter(updateCenter);
                            r1.setBuilding(updateBuilding);
                            r1.setRoom(updateRoom);
                            r1.setBuildingId(buildid);
                            r1.setCapacity(updateCapacity);
                            r1.setRid(roomId);
                            r1.setRoomType(labType);

                            boolean isUpdated = false;
                            try {
                                isUpdated = this.roomService.updateRoomDetails(r1);
                            } catch (SQLException e) {
                                log.log(Level.SEVERE,e.getMessage());
                            }
                            if (isUpdated) {
                                Alert al = new Alert(Alert.AlertType.INFORMATION);
                                al.setTitle(null);
                                al.setContentText("Updated Successfully!");
                                al.setHeaderText(null);
                                al.showAndWait();
                                cmbCenterEdit.setValue(null);
                                txtBuildingEdit1.setText(null);
                                txtRoomEdit1.setText(null);
                                txtCapacitiesEdit1.setText(null);
                                roomId = 0;
                                this.getAllDetails();
                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Updated Failed!");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("You Can't Update this record.because this record isn't in the database!");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Capacity Field Is Empty!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Room Field Is Empty!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Building Field Is Empty!");
            al.setHeaderText(null);
            al.showAndWait();
        }

    }else{
        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setTitle(null);
        al.setContentText("Center Field Is Empty!");
        al.setHeaderText(null);
        al.showAndWait();
    }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        this.getAllDetails();
        cmbLabType.getItems().clear();
        cmbLabType.setItems(FXCollections.observableArrayList("Laboratory", "Lecture Hall"));
    }

    private void setTableProperties() {
        tblRoomView.getSelectionModel().getTableView().getItems().clear();
        tblRoomView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("center"));
        tblRoomView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("building"));
        tblRoomView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("room"));
        tblRoomView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("capacity"));
        tblRoomView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("roomType"));
        editBuildingView.setCellFactory(cellFactoryBtnEdit);
        removeBuildingView.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<Room, Boolean>, TableCell<Room, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<Room, Boolean>, TableCell<Room, Boolean>>() {
                @Override
                public TableCell<Room, Boolean> call(TableColumn<Room, Boolean> param) {
                    final TableCell<Room, Boolean> cell = new TableCell<Room, Boolean>() {
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
                                    Room room1 = getTableView().getItems().get(getIndex());
                                    setRoomDetailsToFiled(room1);
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }
                        private void setRoomDetailsToFiled(Room room2) {
                            cmbCenterEdit.setValue(room2.getCenter());

                            txtBuildingEdit1.setText(room2.getBuilding());
                            txtRoomEdit1.setText(room2.getRoom());

                            txtCapacitiesEdit1.setText(Integer.toString(room2.getCapacity()));

                            roomId = room2.getRid();
                            if(room2.getRoomType().equals("Lecture Hall")){
                                cmbLabType.getSelectionModel().select(1);
                            }
                            if(room2.getRoomType().equals("Laboratory")){
                                cmbLabType.getSelectionModel().select(0);
                            }
                        }
                    };
                    return cell;
                }
            };

    Callback<TableColumn<Room, Boolean>, TableCell<Room, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<Room, Boolean>, TableCell<Room, Boolean>>() {
                @Override
                public TableCell<Room, Boolean> call(TableColumn<Room, Boolean> param) {
                    final TableCell<Room, Boolean> cell = new TableCell<Room, Boolean>() {
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
                                    Room room1 = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteRoom(room1.getRid());
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

    private void deleteRoom(int id) {
        try {
            boolean status1 = roomService.deleteRoom(id);
            if (status1) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Deleted Successfully");
                al.setHeaderText(null);
                al.showAndWait();
                getAllDetails();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Deleted Failed ");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}
