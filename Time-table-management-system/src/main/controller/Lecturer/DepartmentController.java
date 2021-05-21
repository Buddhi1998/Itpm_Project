package main.controller.Lecturer;

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
import main.model.Department;
import main.service.DepartmentService;
import main.service.impl.DepartmentServiceImpl;

public class DepartmentController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        departmentService = new DepartmentServiceImpl();
        this.setTableProperties();
        this.getAllDetails();

    }

    @FXML
    private Label lblYearSemName;

    @FXML
    private TableView<Department> tblDept;

    @FXML
    private TableColumn<Department, Boolean> colEdit;

    @FXML
    private TableColumn<Department, Boolean> colDelete;

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtDepartmentName;

    private DepartmentService departmentService;
    private boolean updateStatus=false;
    private int deptId;
    public static final Logger log = Logger.getLogger(DepartmentController.class.getName());

    @FXML
    void saveDetails(ActionEvent event) {
        String departmentName = txtDepartmentName.getText();
        Department department = new Department();
        department.setDepartmentName(departmentName);
        boolean status = false;
        boolean isAdded = false;
        if (departmentName!=null) {
            try {
                status = this.departmentService.searchDepartment(departmentName);
                if (!status) {
                    if(!updateStatus){
                        isAdded = this.departmentService.saveDepartment(department);
                        if (isAdded) {
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle(null);
                            al.setContentText("  Added Successfully ");
                            al.setHeaderText(null);
                            al.showAndWait();
                            txtDepartmentName.setText(null);
                            this.getAllDetails();
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("  Added Fail");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    }else{
                        department.setDepartmentId(deptId);
                        boolean isUpdated = this.departmentService.updateDepartment(department);
                        if(isUpdated){
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle(null);
                            al.setContentText(" Updated Successfully ");
                            al.setHeaderText(null);
                            al.showAndWait();
                            txtDepartmentName.setText(null);
                            this.getAllDetails();
                            updateStatus=false;
                        }else{
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText(" Updated Fail");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    }

                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText(" Department Name Is Exsiting In This System.You Cant Add this!!!!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }

            } catch (SQLException e) {
                log.log(Level.SEVERE,e.getMessage());
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText(" Please Enter Value For DepartmentName Field");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }

    public void getAllDetails() {
        try {
            ArrayList<Department> departmentsList = this.departmentService.getAllDetails();
            tblDept.setItems(FXCollections.observableArrayList(departmentsList));

        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblDept.getSelectionModel().getTableView().getItems().clear();
        tblDept.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        tblDept.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        colEdit.setCellFactory(cellFactoryBtnEdit);
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<Department, Boolean>, TableCell<Department, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<Department, Boolean>, TableCell<Department, Boolean>>() {
                @Override
                public TableCell<Department, Boolean> call(TableColumn<Department, Boolean> param) {
                    final TableCell<Department, Boolean> cell = new TableCell<Department, Boolean>() {
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
                                    Department dept = getTableView().getItems().get(getIndex());
                                    setTagNameToFiled(dept);
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }
                        private void setTagNameToFiled(Department dept) {
                            txtDepartmentName.setText(dept.getDepartmentName());
                            updateStatus = true;
                            deptId = dept.getDepartmentId();
                        }
                    };
                    return cell;
                }
            };

    Callback<TableColumn<Department, Boolean>, TableCell<Department, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<Department, Boolean>, TableCell<Department, Boolean>>() {
                @Override
                public TableCell<Department, Boolean> call(TableColumn<Department, Boolean> param) {
                    final TableCell<Department, Boolean> cell = new TableCell<Department, Boolean>() {
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
                                    Department dept = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteDepartment(dept.getDepartmentId());
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

    public void deleteDepartment(int id){
        //Method for delete department
    }

}
