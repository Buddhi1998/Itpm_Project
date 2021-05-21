package main.controller.Student;

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
import main.model.YearAndSemester;
import main.service.YearandSemesterService;
import main.service.impl.YearAndServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AcademicYearController implements Initializable {

    @FXML
    private ComboBox<String> cmbYear;

    @FXML
    private ComboBox<String> cmbSemester;

    @FXML
    private TableView<YearAndSemester> tblSemester;

    @FXML
    private Button btnSave;

    @FXML
    private Label lblName;
    @FXML
    private TableColumn<YearAndSemester, Boolean> colEdit;
    @FXML
    private TableColumn<YearAndSemester, Boolean> colDelete;

    private YearandSemesterService yearandSemesterService;
    private boolean updateStatus;
    private int academicYearId;
    public static final Logger log = Logger.getLogger(AcademicYearController.class.getName());

    public AcademicYearController() {
        this.yearandSemesterService = new YearAndServiceImpl();
    }

    public void getAllDetails() {
        try {
            ArrayList<YearAndSemester> list = this.yearandSemesterService.getAllDetails();
            tblSemester.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblSemester.getSelectionModel().getTableView().getItems().clear();
        tblSemester.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblSemester.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("yearName"));
        tblSemester.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("semesterName"));
        tblSemester.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colEdit.setCellFactory(cellFactoryBtnEdit);
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<YearAndSemester, Boolean>, TableCell<YearAndSemester, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<YearAndSemester, Boolean>, TableCell<YearAndSemester, Boolean>>() {
                @Override
                public TableCell<YearAndSemester, Boolean> call(TableColumn<YearAndSemester, Boolean> param) {
                    final TableCell<YearAndSemester, Boolean> cell = new TableCell<YearAndSemester, Boolean>() {
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
                                    YearAndSemester andSemester = getTableView().getItems().get(getIndex());
                                    setAcademicYearSemesterDetailsToFiled(andSemester);
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }
                        private void setAcademicYearSemesterDetailsToFiled(YearAndSemester yearSemester) {
                            cmbSemester.setValue(yearSemester.getSemesterName());
                            cmbYear.setValue(yearSemester.getYearName());
                            updateStatus = true;
                            academicYearId = yearSemester.getId();
                        }
                    };
                    return cell;
                }
            };
    Callback<TableColumn<YearAndSemester, Boolean>, TableCell<YearAndSemester, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<YearAndSemester, Boolean>, TableCell<YearAndSemester, Boolean>>() {
                @Override
                public TableCell<YearAndSemester, Boolean> call(TableColumn<YearAndSemester, Boolean> param) {
                    final TableCell<YearAndSemester, Boolean> cell = new TableCell<YearAndSemester, Boolean>() {
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
                                    YearAndSemester semester = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteYearSemester(semester.getId());
                                    } else {

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


    public void saveDetails(ActionEvent actionEvent) {
        String year = (String) cmbYear.getValue();
        String semester = (String) cmbSemester.getValue();
        String fullName = lblName.getText();
        YearAndSemester yearAndSemester = new YearAndSemester();
        yearAndSemester.setFullName(fullName);
        yearAndSemester.setSemesterName(semester);
        yearAndSemester.setYearName(year);
        boolean isAdded = false;
        boolean status = false;
        boolean isUpdated = false;
        try {
            if (year != null) {
                if (semester != null) {
                    status = this.yearandSemesterService.searchYearAndSemester(year, semester);
                    if (!status) {
                        if (!updateStatus) {
                            isAdded = this.yearandSemesterService.saveAcademiceYear(yearAndSemester);
                            if (isAdded) {
                                Alert al = new Alert(Alert.AlertType.INFORMATION);
                                al.setTitle(null);
                                al.setContentText("Added Successfully ");
                                al.setHeaderText(null);
                                al.showAndWait();
                                this.getAllDetails();
                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Added Fail ");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }
                        } else {
                            yearAndSemester.setId(academicYearId);
                            isUpdated = this.yearandSemesterService.updateYearAndSemester(yearAndSemester);
                            if (isUpdated) {
                                Alert al = new Alert(Alert.AlertType.INFORMATION);
                                al.setTitle(null);
                                al.setContentText("Updated Successfully ");
                                al.setHeaderText(null);
                                al.showAndWait();
                                this.getAllDetails();
                                this.updateStatus=false;
                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Updated Fail ");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }
                        }
                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Year & Semester Is Exsiting In This System.You Cant Add this!!!!");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Please Select Semester");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Select Year");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setName(ActionEvent actionEvent) {
        String year = (String) cmbYear.getValue();
        String semester = (String) cmbSemester.getValue();
        if (year != null && semester != null) {
            if (year.equals("Year 1") && semester.equals("Semester 1")) {
                lblName.setText("Y1.S1");
            } else if (year.equals("Year 2") && semester.equals("Semester 1")) {
                lblName.setText("Y2.S1");
            } else if (year.equals("Year 3") && semester.equals("Semester 1")) {
                lblName.setText("Y3.S1");
            } else if (year.equals("Year 4") && semester.equals("Semester 1")) {
                lblName.setText("Y4.S1");
            } else if (year.equals("Year 1") && semester.equals("Semester 2")) {
                lblName.setText("Y1.S2");
            } else if (year.equals("Year 2") && semester.equals("Semester 2")) {
                lblName.setText("Y2.S2");
            } else if (year.equals("Year 3") && semester.equals("Semester 2")) {
                lblName.setText("Y3.S2");
            } else if (year.equals("Year 4") && semester.equals("Semester 2")) {
                lblName.setText("Y4.S2");
            } else {
                lblName.setText("");
            }
        }

    }

    private void deleteYearSemester(int id) {
        try {
            boolean staus = yearandSemesterService.deleteYearAndSemester(id);
            if (staus) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Deleted SuccessFully ");
                al.setHeaderText(null);
                al.showAndWait();
                getAllDetails();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Deleted Fail ");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        this.getAllDetails();
        this.updateStatus = false;
    }
}
