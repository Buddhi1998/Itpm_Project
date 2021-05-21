package main.controller.Subject;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.Subject;
import main.model.YearAndSemester;
import main.service.SubjectService;
import main.service.YearandSemesterService;
import main.service.impl.SubjectServiceImpl;
import main.service.impl.YearAndServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.TextFields;

public class ViewSubjectController implements Initializable {

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Subject> tblSubject;

    @FXML
    private TableColumn<Subject, Boolean> colEdit;

    @FXML
    private TableColumn<Subject, Boolean> colDelete;


    @FXML
    private TextField txtName;

    @FXML
    private TextField txtLec;

    @FXML
    private TextField txtEval;

    @FXML
    private TextField txtOfferedYear;

    @FXML
    private TextField txtTut;

    @FXML
    private TextField txtLabHrs;

    @FXML
    private TextField txtSubCode;

    @FXML
    private TextField txtCategory;

    @FXML
    private ComboBox<String> comboType;
    private ObservableList<String> subjectsLists = FXCollections.observableArrayList("Compulsory", "Optional");


    String subId;
    int dId;
    private ArrayList<YearAndSemester> yearsId = new ArrayList<>();
    private ArrayList<String> yearName = new ArrayList<>();
    public static final Logger log = Logger.getLogger(ViewSubjectController.class.getName());
    int dCount = 0;

    @FXML
    void updateLecturer(ActionEvent event) {
        String subName = txtName.getText();
        String offered = txtOfferedYear.getText();
        int lec = Integer.parseInt(txtLec.getText());
        int tut = Integer.parseInt(txtTut.getText());
        int eval = Integer.parseInt(txtEval.getText());
        int labHrs = Integer.parseInt(txtLabHrs.getText());
        String category = txtCategory.getText();
        String selectedType = comboType.getSelectionModel().getSelectedItem();

        for (YearAndSemester yearAndSemester : this.yearsId) {
            if (offered.equals(yearAndSemester.getFullName())) {
                dId = yearAndSemester.getId();
                dCount++;
            }
        }
        try {
            if (!subName.equalsIgnoreCase("")) {
                if (!offered.equalsIgnoreCase("")) {
                    if (lec != 0 && tut != 0 && eval != 0) {
                        Subject subject = new Subject(subId, subName, dId, lec, tut, eval,labHrs,selectedType,category);
                        SubjectService subjectService = new SubjectServiceImpl();
                        boolean res = subjectService.updateSubject(subject);
                        if (res) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(null);
                            alert.setHeaderText(null);
                            alert.setContentText("Success Updating Subject!");

                            alert.showAndWait();

                            txtName.setText(" ");
                            txtOfferedYear.setText(" ");
                            txtLec.setText(" ");
                            txtTut.setText(" ");
                            txtEval.setText(" ");
                            txtSubCode.setText("");
                            txtLabHrs.setText("");
                            txtCategory.setText("");
                            comboType.getSelectionModel().select(0);
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Error Updating Subject!");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                        this.setTableProperties();
                        getAllSubjects();
                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Enter in Correct Format for  Lecture/Tute/Evaluation hours!");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Offered Year and Semester Empty!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Subject Name is Empty!");
                al.setHeaderText(null);
                al.showAndWait();
            }

        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        txtSubCode.setEditable(false);
        setSubjectType();
        getAllSubjects();
        getAllYearAndSemesterDetails();
    }

    public void setSubjectType() {
        comboType.setItems(subjectsLists);
    }

    public void getAllSubjects() {

        try {
            SubjectService subjectService = new SubjectServiceImpl();
            ArrayList<Subject> list = subjectService.getAllSubjectDetails();
            for (Subject str : list) {

                YearandSemesterService yearandSemesterService = new YearAndServiceImpl();
                str.setYearSem(yearandSemesterService.searchYearAndSemesterName(str.getOfferedYearSem()));
            }
            tblSubject.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    public void setTableProperties() {
        tblSubject.getSelectionModel().getTableView().getItems().clear();
        tblSubject.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("subId"));
        tblSubject.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("subName"));
        tblSubject.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("yearSem"));
        tblSubject.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("noLecHrs"));
        tblSubject.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("noTutHrs"));
        tblSubject.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("noEvalHrs"));
        tblSubject.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("noLabHrs"));
        tblSubject.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("category"));
        tblSubject.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("subType"));
        colEdit.setCellFactory(cellFactoryBtnEdit);
        colDelete.setCellFactory(cellFactoryBtnDelete);

    }

    Callback<TableColumn<Subject, Boolean>, TableCell<Subject, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<Subject, Boolean>, TableCell<Subject, Boolean>>() {
                @Override
                public TableCell<Subject, Boolean> call(TableColumn<Subject, Boolean> param) {
                    final TableCell<Subject, Boolean> cell = new TableCell<Subject, Boolean>() {
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
                                    Subject subject = getTableView().getItems().get(getIndex());
                                    setSubjectDetailsToTheField(subject);
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

                        private void setSubjectDetailsToTheField(Subject subject1) {
                            subId = subject1.getSubId();
                            txtName.setText(subject1.getSubName());
                            txtSubCode.setText(subject1.getSubId());
                            try {
                                YearandSemesterService yearandSemesterService = new YearAndServiceImpl();
                                subject1.setYearSem(yearandSemesterService.searchYearAndSemesterName(subject1.getOfferedYearSem()));
                            } catch (SQLException ex) {
                                log.log(Level.SEVERE, ex.getMessage());
                            }

                            txtOfferedYear.setText(subject1.getYearSem());
                            txtLec.setText(Integer.toString(subject1.getNoLecHrs()));
                            txtTut.setText(Integer.toString(subject1.getNoTutHrs()));
                            txtEval.setText(Integer.toString(subject1.getNoEvalHrs()));
                            txtLabHrs.setText(Integer.toString(subject1.getNoLabHrs()));
                            txtCategory.setText(subject1.getCategory());
                            if (subject1.getSubType().equals("Compulsory")) {
                                comboType.getSelectionModel().select(0);
                            }
                            if (subject1.getSubType().equals("Optional")) {
                                comboType.getSelectionModel().select(1);
                            }

                        }
                    };
                    return cell;
                }
            };
    Callback<TableColumn<Subject, Boolean>, TableCell<Subject, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<Subject, Boolean>, TableCell<Subject, Boolean>>() {
                @Override
                public TableCell<Subject, Boolean> call(TableColumn<Subject, Boolean> param) {
                    final TableCell<Subject, Boolean> cell = new TableCell<Subject, Boolean>() {
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
                                    Subject subject = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteSubject(subject.getSubId());
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

    public void deleteSubject(String id) {

        try {
            SubjectService subjectService = new SubjectServiceImpl();
            subjectService.deleteSubjectDetails(id);
            this.setTableProperties();
            getAllSubjects();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
    }

    private void getAllYearAndSemesterDetails() {
        try {
            YearandSemesterService yearandSemesterService = new YearAndServiceImpl();
            ArrayList<YearAndSemester> list = yearandSemesterService.getAllDetails();
            for (YearAndSemester yearAndSemester : list
            ) {
                yearsId.add(yearAndSemester);
                yearName.add(yearAndSemester.getFullName());
            }
            TextFields.bindAutoCompletion(txtOfferedYear, yearName);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }
}
