package main.controller.Session;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.ConsectiveSession;
import main.model.Lecturer;
import main.model.Subject;
import main.service.LecturerService;
import main.service.SessionService;
import main.service.SubjectService;
import main.service.impl.LectureServiceImpl;
import main.service.impl.SessionServiceImpl;
import main.service.impl.SubjectServiceImpl;
import org.controlsfx.control.textfield.TextFields;

public class ConsectiveSessionController implements Initializable {


    @FXML
    private TableView<ConsectiveSession> tblConsectiveSession;

    @FXML
    private TableColumn<ConsectiveSession, Boolean> colEdit;

    @FXML
    private TextField txtLecturer;

    @FXML
    private TextField txtSubject;

    private SessionService sessionService;
    private List<Lecturer> lectureList;
    private List<String> lectureNameList;
    private LecturerService lecturerService;
    private SubjectService subjectService;
    private List<Subject> subList;
    private List<String> subNameList;
    public static final Logger log = Logger.getLogger(ConsectiveSessionController.class.getName());

    @FXML
    void searchDetails(ActionEvent event) {
        String lecturer = txtLecturer.getText();
        String subject = txtSubject.getText();
        loadConsectiveSesssions(lecturer, subject);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        sessionService = new SessionServiceImpl();
        this.loadConsectiveSesssions("", "");
        lectureNameList = new ArrayList<>();
        lectureList = new ArrayList<>();
        lecturerService = new LectureServiceImpl();
        subjectService = new SubjectServiceImpl();
        subList = new ArrayList<>();
        subNameList = new ArrayList<>();

        this.loadLectureDetails();
        this.loadSubjectDetails();
    }

    public void loadLectureDetails() {
        try {
            ArrayList<Lecturer> lec = lecturerService.getAllLecturerDetails();

            for (Lecturer l : lec
            ) {
                lectureNameList.add(l.getEmpName());
                lectureList.add(l);
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
        TextFields.bindAutoCompletion(txtLecturer, lectureNameList);
    }

    public void loadSubjectDetails() {
        try {
            ArrayList<Subject> subjects = subjectService.getAllSubjectDetails();

            for (Subject s : subjects
            ) {
                subList.add(s);
                subNameList.add(s.getSubId());
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
        TextFields.bindAutoCompletion(txtSubject, subNameList);
    }


    private void loadConsectiveSesssions(String lecturer, String subject) {
        try {
            ArrayList<ConsectiveSession> csList = sessionService.getAllConsectiveSessions(lecturer, subject);

            tblConsectiveSession.setItems(FXCollections.observableArrayList(csList));
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }

    }


    public void setTableProperties() {
        tblConsectiveSession.getSelectionModel().getTableView().getItems().clear();
        tblConsectiveSession.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("groupId"));
        tblConsectiveSession.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("subject"));
        tblConsectiveSession.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("tag"));
        colEdit.setCellFactory(cellFactoryBtnEdit);

    }

    Callback<TableColumn<ConsectiveSession, Boolean>, TableCell<ConsectiveSession, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<ConsectiveSession, Boolean>, TableCell<ConsectiveSession, Boolean>>() {
                @Override
                public TableCell<ConsectiveSession, Boolean> call(TableColumn<ConsectiveSession, Boolean> param) {
                    final TableCell<ConsectiveSession, Boolean> cell = new TableCell<ConsectiveSession, Boolean>() {
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
                                    ConsectiveSession cs = getTableView().getItems().get(getIndex());
                                    setConsectiveSessions(cs);
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };

    public void setConsectiveSessions(ConsectiveSession cs) {
        if (cs.getTag().equals("Tute")) {
            consectiveSession("Lecture", cs);
        } else if (cs.getTag().equals("Lecture")) {
            consectiveSession("Tute", cs);
        }
    }

    public void consectiveSession(String type, ConsectiveSession cs) {
        try {
            int id = this.sessionService.getSessionIdForConsectiveSession(cs.getGroupId(), cs.getSubject(), type);
            if (id != 0) {
                boolean isAdded = this.sessionService.saveCosectiveSession(cs.getId(), id);
                if (isAdded) {
                    boolean isAdded1 = this.sessionService.saveCosectiveSession(id, cs.getId());
                    if (isAdded1) {
                        boolean isUpdated = this.sessionService.updateRowForConsectiveSession(id);
                        if (isUpdated) {
                            boolean isUpdated1 = this.sessionService.updateRowForConsectiveSession(cs.getId());
                            if (isUpdated1) {
                                Alert al = new Alert(Alert.AlertType.INFORMATION);
                                al.setTitle(null);
                                al.setContentText("Consective Session Is Added SuccessFully");
                                al.setHeaderText(null);
                                al.showAndWait();
                                this.loadConsectiveSesssions("", "");
                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Consective Session Added Fail ");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Consective Session Added Fail");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    }

                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText(" Consective Session Added Fail");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("This Session hasn't Consective Session");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}
