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

public class ViewBuildingController implements Initializable {

    @FXML
    private TableView<Building> tblBuildingView;

    @FXML
    private TableColumn<Building, Boolean> editBuildingView;

    @FXML
    private TableColumn<Building, Boolean> removeBuildingView;

    @FXML
    private TextField txtBuildingEdit;

    @FXML
    private Button btnBuildingSave;

    @FXML
    private ComboBox<String> cmbCenterEdit;

    private BuildingService buildingService;
    private int buildingId;
    public static final Logger log = Logger.getLogger(ViewBuildingController.class.getName());

    public ViewBuildingController() {
        this.buildingService =new BuildingServiceImpl();
    }

    public void getAllDetails() {
        try {
            ArrayList<Building> listB = this.buildingService.getAllDetails();
            tblBuildingView.setItems(FXCollections.observableArrayList(listB));
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }


    @FXML
    void updateBuildingDetails(ActionEvent event) {

        String updateCenter = cmbCenterEdit.getValue();
        String updateBuilding = txtBuildingEdit.getText();
        if (updateCenter.length() != 0) {
            if (updateBuilding.length() != 0) {
                if(buildingId!=0){
                    try {
                        boolean status = this.buildingService.searchBuilding(updateCenter,updateBuilding);
                        if (!status) {
                            Building b1 = new Building();
                            b1.setCenter(cmbCenterEdit.getValue());
                            b1.setBuilding(txtBuildingEdit.getText());
                            b1.setBid(buildingId);
                            boolean isUpdated = this.buildingService.updateBuildingDetails(b1);
                            if (isUpdated) {
                                Alert al = new Alert(Alert.AlertType.INFORMATION);
                                al.setTitle(null);
                                al.setContentText("Updated Successfully");
                                al.setHeaderText(null);
                                al.showAndWait();
                                cmbCenterEdit.setValue(null);
                                txtBuildingEdit.setText(null);
                                buildingId = 0;
                                this.getAllDetails();
                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Updated Failed  ");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("This center and building is Exist In this System.");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    } catch (SQLException e) {
                        log.log(Level.SEVERE,e.getMessage());
                    }
                }else{
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("You Can't Update this record.because this record isn't in the database");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Building Field Is Empty");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Center Field Is Empty");
            al.setHeaderText(null);
            al.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        this.getAllDetails();
    }

    private void setTableProperties() {
        tblBuildingView.getSelectionModel().getTableView().getItems().clear();
        tblBuildingView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("center"));
        tblBuildingView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("building"));
        editBuildingView.setCellFactory(cellFactoryBtnEdit);
        removeBuildingView.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<Building, Boolean>, TableCell<Building, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<Building, Boolean>, TableCell<Building, Boolean>>() {
                @Override
                public TableCell<Building, Boolean> call(TableColumn<Building, Boolean> param) {
                    final TableCell<Building, Boolean> cell = new TableCell<Building, Boolean>() {
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
                                    Building build1 = getTableView().getItems().get(getIndex());
                                    setBuildingDetailsToFiled(build1);
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }
                        private void setBuildingDetailsToFiled(Building build2) {
                            cmbCenterEdit.setValue(build2.getCenter());
                            txtBuildingEdit.setText(build2.getBuilding());
//
                            buildingId = build2.getBid();
                        }
                    };
                    return cell;
                }
            };

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
                                    Building build1 = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteBuilding(build1.getBid());
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

    private void deleteBuilding(int id) {
        try {
            boolean status1 = buildingService.deleteBuilding(id);
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