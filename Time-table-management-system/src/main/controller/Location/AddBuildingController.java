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
import main.service.BuildingService;
import main.service.impl.BuildingServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddBuildingController implements Initializable {

    @FXML
    private TableView<Building> tblBuilding;

    @FXML
    private TableColumn<?, ?> editBuilding;

    @FXML
    private TableColumn<Building, Boolean> removeBuilding;

    @FXML
    private ComboBox<String> cmbCenterAdd;

    @FXML
    private TextField txtBuildingAdd;

    @FXML
    private Button btnBuildingAdd;

    @FXML
    private Button btnBuildingSave;

    private BuildingService buildingService;
    private ArrayList<Building> buildingList = new ArrayList<>();
    public static final Logger log = Logger.getLogger(AddBuildingController.class.getName());


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
    }

    public AddBuildingController() {
        this.buildingService = new BuildingServiceImpl();

    }


    @FXML
    void AddBuildingsToTable(ActionEvent event) {
        String building = txtBuildingAdd.getText();
        String center = (String) cmbCenterAdd.getValue();
        if (center != null) {
            if (!building.isEmpty()) {
                Building buildingObj = new Building();
                buildingObj.setBuilding(building);
                buildingObj.setCenter(center);
                txtBuildingAdd.setText("");
                int duplicateCount  = 0;
                if (!buildingList.isEmpty()) {
                    for (Building b1 : buildingList
                    ) {
                        if (b1.getBuilding().equals(building) && b1.getCenter().equals(center)) {
                            duplicateCount++;
                        }
                    }
                    if (duplicateCount == 0) {
                        buildingList.add(buildingObj);
                        tblBuilding.getSelectionModel().getTableView().getItems().clear();
                        tblBuilding.setItems(FXCollections.observableArrayList(buildingList));

                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Building & Center is Already In the Table!");
                        al.setHeaderText(null);
                        al.showAndWait();
                        duplicateCount = 0;
                    }
                } else {
                    buildingList.add(buildingObj);
                    tblBuilding.getSelectionModel().getTableView().getItems().clear();
                    tblBuilding.setItems(FXCollections.observableArrayList(buildingList));
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText(" Building field is empty!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText(" Please select Center!");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }


    @FXML
    void saveBuildingDetails(ActionEvent event) {

        if (!buildingList.isEmpty()) {
            boolean isAdded = false;
            int addedCount = 0;
            for (Building buildingObj : buildingList
            ) {
                try {
                    isAdded = this.buildingService.saveBuildings(buildingObj);
                } catch (SQLException e) {
                    log.log(Level.SEVERE,e.getMessage());
                }
                addedCount++;
            }
            if (addedCount == buildingList.size()) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Added Successfully!");
                al.setHeaderText(null);
                al.showAndWait();
                buildingList.clear();
                tblBuilding.getSelectionModel().getTableView().getItems().clear();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Added Fail!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Add Values to Table!");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }

    public void setTableProperties() {
        tblBuilding.getSelectionModel().getTableView().getItems().clear();
        tblBuilding.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("center"));
        tblBuilding.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("building"));
        removeBuilding.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<Building, Boolean>, TableCell<Building, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<Building, Boolean>, TableCell<Building, Boolean>>() {
                @Override
                public TableCell<Building, Boolean> call(TableColumn<Building, Boolean> param) {
                    final TableCell<Building, Boolean> cell = new TableCell<Building, Boolean>() {
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
                                    Building building = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteBuilding(building);
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

    public void deleteBuilding(Building obj) {
        buildingList.remove(obj);
        tblBuilding.getSelectionModel().getTableView().getItems().clear();
        tblBuilding.setItems(FXCollections.observableArrayList(buildingList));
    }
}


