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
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import main.Main;
import main.model.MainGroup;
import main.service.MainGroupService;
import main.service.impl.MainGroupServiceImpl;


public class ViewMainGroupController implements Initializable {

    @FXML
    private TextField txtGroupId;

    @FXML
    private TextField txtGroupNumber;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<MainGroup> tblMainGroup;

    @FXML
    private TableColumn<MainGroup, Boolean> colEdit;

    @FXML
    private TableColumn<MainGroup, Boolean> colDelete;

    @FXML
    private TextField txtSearch;
    private MainGroupService mainGroupService;
    private int id;
    public static final Logger log = Logger.getLogger(ViewMainGroupController.class.getName());

    public ViewMainGroupController() {
        mainGroupService = new MainGroupServiceImpl();
    }

    @FXML
    void searchDetails(ActionEvent event) {


        if (txtSearch.getText().length() != 0) {

            try {
                getAllGroupDetails(Integer.parseInt(txtSearch.getText()));
            } catch (NumberFormatException e) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Enter Number");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else {
            getAllGroupDetails(0);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        this.getAllGroupDetails(0);
    }

    public void getAllGroupDetails(int id) {

        try {
            ArrayList<MainGroup> list = this.mainGroupService.getAllGroupDetails(id);
            tblMainGroup.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @FXML
    void changeGroupId(ActionEvent event) {
        String groupNum = txtGroupNumber.getText();
        String groupId = txtGroupId.getText();
        if (groupId.length() != 0) {
            int lastIndxDot = groupId.lastIndexOf('.');
            String substringId = groupId.substring(0, lastIndxDot);
            String newGroupId = "";
            if (groupNum.length() != 0) {
                if (Integer.parseInt(groupNum) < 10) {
                    newGroupId = substringId + ".0" + groupNum;
                } else {
                    newGroupId = substringId + "." + groupNum;
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
                if (id != 0) {
                    try {
                        boolean status = this.mainGroupService.searchMainGroup(updateGroupId);
                        if (!status) {
                            MainGroup m = new MainGroup();
                            m.setGroupid(txtGroupId.getText());
                            m.setGroupNumber(Integer.parseInt(txtGroupNumber.getText()));
                            m.setId(id);
                            boolean isUpdated = this.mainGroupService.updateGroupNumber(m);
                            if (isUpdated) {
                                Alert al = new Alert(Alert.AlertType.INFORMATION);
                                al.setTitle(null);
                                al.setContentText("Successfully Updated");
                                al.setHeaderText(null);
                                al.showAndWait();
                                txtGroupId.setText(null);
                                txtGroupNumber.setText(null);
                                id = 0;
                                this.getAllGroupDetails(0);
                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Updated Fail  ");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("This Group Id is Exist In this System.");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    } catch (SQLException e) {
                        log.log(Level.SEVERE,e.getMessage());
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("You Cant Update this record.because this record isn't in the database");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Gorup Number Field Is Empty");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Gorup Id Field Is Empty");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }

    public void setTableProperties() {
        tblMainGroup.getSelectionModel().getTableView().getItems().clear();
        tblMainGroup.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblMainGroup.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("groupNumber"));
        tblMainGroup.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("groupid"));
        colEdit.setCellFactory(cellFactoryBtnEdit);
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<MainGroup, Boolean>, TableCell<MainGroup, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<MainGroup, Boolean>, TableCell<MainGroup, Boolean>>() {
                @Override
                public TableCell<MainGroup, Boolean> call(TableColumn<MainGroup, Boolean> param) {
                    final TableCell<MainGroup, Boolean> cell = new TableCell<MainGroup, Boolean>() {
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
                                    MainGroup mainGroup = getTableView().getItems().get(getIndex());
                                    setMainGroupDetailsToTheField(mainGroup);
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

                        private void setMainGroupDetailsToTheField(MainGroup m) {
                            txtGroupId.setText(m.getGroupid());
                            txtGroupNumber.setText(m.getGroupNumber() + "");
                            id = m.getId();
                        }
                    };
                    return cell;
                }
            };

    Callback<TableColumn<MainGroup, Boolean>, TableCell<MainGroup, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<MainGroup, Boolean>, TableCell<MainGroup, Boolean>>() {
                @Override
                public TableCell<MainGroup, Boolean> call(TableColumn<MainGroup, Boolean> param) {
                    final TableCell<MainGroup, Boolean> cell = new TableCell<MainGroup, Boolean>() {
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
                                    MainGroup mainGroup = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteMainGroup(mainGroup.getId());
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

    public void deleteMainGroup(int id) {
        boolean isDeleted = false;
        try {
            isDeleted = this.mainGroupService.deleteMainGroup(id);
            if (isDeleted) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Deleted SuccessFully");
                al.setHeaderText(null);
                al.showAndWait();
                this.getAllGroupDetails(0);
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Deleted Fail");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }

    }


}
