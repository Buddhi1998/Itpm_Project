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
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddRoomController implements Initializable {


    @FXML
    private TableView<Room> tblRoomAdd;


    @FXML
    private TableColumn<Room, Boolean> removeRoomAdd;

    @FXML
    private TextField txtRoomAdd;

    @FXML
    private Button btnRoomAdd;

    @FXML
    private Button btnRoomSave;

    @FXML
    private TextField txtCapacities;

    @FXML
    private ComboBox<String> cmbCenter;

    @FXML
    private TextField txtBuilding;

    @FXML
    private ComboBox<String> cmbLabType;

    ArrayList<Building> buildingsId = new ArrayList<>();
    ArrayList<String> buildingName = new ArrayList<>();
    private ArrayList<Room> roomsList = new ArrayList<>();
    private AutoCompletionBinding<String> autoCompletionBinding;
    public static final Logger log = Logger.getLogger(AddRoomController.class.getName());

    @FXML
    void getBuilding(ActionEvent event) {
        String center = cmbCenter.getValue();
        try {
            BuildingService buildingService = new BuildingServiceImpl();
            ArrayList<Building> list = buildingService.searchBuildingDetailsByUsingCenter(center);
            buildingsId = new ArrayList<>();
            buildingName = new ArrayList<>();
            for (Building building : list
            ) {
                buildingsId.add(building);
                buildingName.add(building.getBuilding());
            }

            if (autoCompletionBinding != null) {
                autoCompletionBinding.dispose();
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(txtBuilding, buildingName);

        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
    }

    private RoomService roomService;

    public AddRoomController() {
        this.roomService = new RoomServiceImpl();
    }


    @FXML
    void AddRoomsToTable(ActionEvent event) {

        String room = txtRoomAdd.getText();
        String center = cmbCenter.getValue();
        String building = txtBuilding.getText();
        int buildid = 0;
        for (Building b : this.buildingsId
        ) {
            if (building.equals(b.getBuilding())) {
                buildid = b.getBid();
            }
        }
        try {
            if (center != null) {
                if (!building.isEmpty()) {
                    if (!room.isEmpty()) {
                        if (!txtCapacities.getText().isEmpty()) {
                            if (!cmbLabType.getSelectionModel().isEmpty()) {
                                int capacity = Integer.parseInt(txtCapacities.getText());
                                String labType = cmbLabType.getSelectionModel().getSelectedItem();
                                Room rooomObj = new Room();
                                rooomObj.setCenter(center);
                                rooomObj.setRoom(room);
                                rooomObj.setCapacity(capacity);
                                rooomObj.setBuildingId(buildid);
                                rooomObj.setBuilding(building);
                                rooomObj.setRoomType(labType);
                                int duplicateCount = 0;
                                if (!roomsList.isEmpty()) {
                                    for (Room r1 : roomsList
                                    ) {
                                        if (r1.getBuilding().equals(building) && r1.getCenter().equals(center)
                                                && r1.getRoom().equals(room)) {
                                            duplicateCount++;
                                        }
                                    }
                                    if (duplicateCount == 0) {
                                        roomsList.add(rooomObj);

                                        tblRoomAdd.getSelectionModel().getTableView().getItems().clear();
                                        tblRoomAdd.setItems(FXCollections.observableArrayList(roomsList));

                                        txtCapacities.setText("");
                                        txtRoomAdd.setText("");
                                    } else {
                                        Alert al = new Alert(Alert.AlertType.ERROR);
                                        al.setTitle(null);
                                        al.setContentText("Room  is Already In the Table!");
                                        al.setHeaderText(null);
                                        al.showAndWait();
                                        duplicateCount = 0;
                                    }
                                } else {
                                    roomsList.add(rooomObj);

                                    tblRoomAdd.getSelectionModel().getTableView().getItems().clear();
                                    tblRoomAdd.setItems(FXCollections.observableArrayList(roomsList));
                                    txtCapacities.setText("");
                                    txtRoomAdd.setText("");
                                }
                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Empty Lab Type field!");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Empty capacity field!");
                            al.setHeaderText(null);
                            al.showAndWait();

                        }
                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Empty room field!");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }


                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Please select Building!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please select Center !");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Enter Numeric Value !");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }


    @FXML
    void saveRoomDetails(ActionEvent event) {
        if (!roomsList.isEmpty()) {
            boolean isAdded = false;
            int addedCount = 0;
            for (Room roomObj : roomsList) {
                try {
                  this.roomService.saveRooms(roomObj);

                } catch (SQLException e) {
                    log.log(Level.SEVERE, e.getMessage());
                }
                addedCount++;
            }
            if (addedCount == roomsList.size()) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText(" Added Successfully!");
                al.setHeaderText(null);
                al.showAndWait();
                roomsList.clear();
                tblRoomAdd.getSelectionModel().getTableView().getItems().clear();
                txtBuilding.setText("");
                txtCapacities.setText("");
                txtRoomAdd.setText("");
                cmbCenter.getSelectionModel().clearSelection();
                cmbLabType.getSelectionModel().clearSelection();

            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText(" Added Fail!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText(" Please Add Values to Table!");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }

    public void setTableProperties() {
        tblRoomAdd.getSelectionModel().getTableView().getItems().clear();
        tblRoomAdd.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("center"));
        tblRoomAdd.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("building"));
        tblRoomAdd.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("room"));
        tblRoomAdd.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("capacity"));
        tblRoomAdd.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("roomType"));
        removeRoomAdd.setCellFactory(cellFactoryBtnDelete);
    }

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
                                    Room room = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteRoom(room);
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

    public void deleteRoom(Room obj) {
        roomsList.remove(obj);
        tblRoomAdd.getSelectionModel().getTableView().getItems().clear();
        tblRoomAdd.setItems(FXCollections.observableArrayList(roomsList));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        cmbLabType.getItems().clear();
        cmbLabType.setItems(FXCollections.observableArrayList("Laboratory", "Lecture Hall"));
    }
}



