package main.controller.Student;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.SubGroup;
import main.service.SubGroupService;
import main.service.impl.SubGroupServiceImpl;


public class ViewSubGroupController implements Initializable {

    @FXML
    private TextField txtGroupId;

    @FXML
    private TextField txtGroupNumber;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtSearchGroupNumber;

    @FXML
    private TableView<SubGroup> tblGroupNumber;

    @FXML
    private TableColumn<SubGroup, Boolean> colEdit;

    @FXML
    private TableColumn<SubGroup, Boolean> colDelete;

    private SubGroupService subGroupService;
    private int id;
    public static final Logger log = Logger.getLogger(ViewSubGroupController.class.getName());

    public ViewSubGroupController() {
        this.subGroupService = new SubGroupServiceImpl();
    }

    @FXML
    void editSubGroupId(ActionEvent event) {
        String newGroupId = "";
        String groupNumber = txtGroupNumber.getText();
        String groupId = txtGroupId.getText();
        if (groupId.length() != 0) {
            int lastIndxDot = groupId.lastIndexOf('.');
            String substringId = groupId.substring(0, lastIndxDot);
            if (groupNumber.length() != 0) {
                if (Integer.parseInt(groupNumber) <= 9) {
                    newGroupId = substringId + ".0" + groupNumber;
                } else {
                    newGroupId = substringId + "." + groupNumber;
                }
                txtGroupId.setText(newGroupId);
            }
        }
    }

    @FXML
    void updateGroupNumber(ActionEvent event) {
        String updateGroupId = txtGroupId.getText();
        if (updateGroupId.length() != 0) {
            if (txtGroupNumber.getText().length() != 0) {
                if(id!=0){
                    try {
                        boolean status = this.subGroupService.searchSubGroup(updateGroupId);
                        if (!status) {
                            SubGroup m = new SubGroup();
                            m.setSubgroupnumber(Integer.parseInt(txtGroupNumber.getText()));
                            m.setSubgroupid(txtGroupId.getText());
                            m.setId(id);
                            boolean isUpdated = this.subGroupService.updateGroupNumber(m);
                            if (isUpdated) {
                                Alert al = new Alert(Alert.AlertType.INFORMATION);
                                al.setTitle(null);
                                al.setContentText("Successfully Updated  ");
                                al.setHeaderText(null);
                                al.showAndWait();
                                txtGroupId.setText(null);
                                txtGroupNumber.setText(null);
                                id = 0;
                                this.getAllSubgroupDetails(0);
                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Updated Fail   ");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("This Group Id is Exist In this System.  ");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    } catch (SQLException e) {
                        log.log(Level.SEVERE,e.getMessage());
                    }
                }else{
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText(" You Cant Update this record.because this record isn't in the database");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Sub Gorup Number Field Is Empty");
                al.setContentText("Sub Gorup Number Field Is Empty");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Sub Gorup Id Field Is Empty");
            al.setHeaderText(null);
            al.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        this.getAllSubgroupDetails(0);
    }

    @FXML
    void searchGroupId(ActionEvent event) {
        String groupNumber = txtSearchGroupNumber.getText();
        if (groupNumber.length() != 0) {
            try{
                getAllSubgroupDetails(Integer.parseInt(groupNumber));
            }catch(NumberFormatException e){
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText(" Please Enter Number");
                al.setHeaderText(null);
                al.showAndWait();
            }

        } else {
            getAllSubgroupDetails(0);

        }
    }

    public void getAllSubgroupDetails(int id) {
        try {
            ArrayList<SubGroup> list = this.subGroupService.getAllSubGroupDetails(id);
            tblGroupNumber.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblGroupNumber.getSelectionModel().getTableView().getItems().clear();
        tblGroupNumber.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblGroupNumber.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("subgroupnumber"));
        tblGroupNumber.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("subgroupid"));
        colEdit.setCellFactory(cellFactoryBtnEdit);
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<SubGroup, Boolean>, TableCell<SubGroup, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<SubGroup, Boolean>, TableCell<SubGroup, Boolean>>() {
                @Override
                public TableCell<SubGroup, Boolean> call(TableColumn<SubGroup, Boolean> param) {
                    final TableCell<SubGroup, Boolean> cell = new TableCell<SubGroup, Boolean>() {
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
                                    SubGroup subGroup = getTableView().getItems().get(getIndex());
                                    setMainGroupDetailsToTheField(subGroup);
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

                        private void setMainGroupDetailsToTheField(SubGroup m) {
                            txtGroupId.setText(m.getSubgroupid());
                            txtGroupNumber.setText(m.getSubgroupnumber() + "");
                            id = m.getId();
                        }
                    };
                    return cell;
                }
            };

    Callback<TableColumn<SubGroup, Boolean>, TableCell<SubGroup, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<SubGroup, Boolean>, TableCell<SubGroup, Boolean>>() {
                @Override
                public TableCell<SubGroup, Boolean> call(TableColumn<SubGroup, Boolean> param) {
                    final TableCell<SubGroup, Boolean> cell = new TableCell<SubGroup, Boolean>() {
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
                                    SubGroup subGroup = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteSubGroup(subGroup.getId());
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

    public void deleteSubGroup(int id) {
        boolean isDeleted = false;
        try {
            isDeleted = this.subGroupService.deleteSubGroup(id);
            if (isDeleted) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("SubGroup Deleted SuccessFully");
                al.setHeaderText(null);
                al.showAndWait();
                this.getAllSubgroupDetails(0);
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Sub Group Deleted Fail");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}
