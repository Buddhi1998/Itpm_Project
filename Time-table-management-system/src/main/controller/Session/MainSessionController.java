package main.controller.Session;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.*;
import main.service.*;
import main.service.impl.*;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;


public class MainSessionController implements Initializable {

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtLecturer;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtTag;

    @FXML
    private TextField txtGroup;

    @FXML
    private RadioButton btnRadioMain;

    @FXML
    private RadioButton btnRadioSub;

    @FXML
    private TextField txtCount;

    @FXML
    private TableView<Lecturer> lectTbl;

    @FXML
    private TextField txtDuration;
    @FXML
    private TableColumn<Lecturer, Boolean> colDelete;
    int subCount = 0;
    int lecCount = 0;
    private AutoCompletionBinding<String> autoCompletionBindings;
    private LecturerService lecturerServices;
    private SubjectService subjectServices;
    private TagService tagServices;
    private MainGroupService mainGroupServices;
    private SubGroupService subGroupServices;
    private List<Lecturer> lectureLists;
    private List<Subject> subjectLists;
    private List<Tag> tagLists;
    private List<Object> mainGroupLists;
    private List<String> lectureNameLists;
    private List<String> subNameLists;
    private List<String> tagNameLists;
    private List<String> maingroupNameLists;
    private ArrayList<Lecturer> list1 = new ArrayList<>();
    private String subId1;
    private int tagId;
    private int gId;
    private int subGroupId;
    public static final Logger log = Logger.getLogger(MainSessionController.class.getName());

    @FXML
    void loadGroupDetails(ActionEvent event) {
        //Load group details method
    }

    @FXML
    void saveDetails(ActionEvent event) {
        int empId;
        String subject = txtSubject.getText();
        String tag = txtTag.getText();
        String groupType;
        if ((tag.equalsIgnoreCase("Lecture")) || (tag.equalsIgnoreCase("Tute"))) {
            groupType = "Main";
        } else {
            groupType = "Sub";
        }
        String groupId = txtGroup.getText();
        try {
            int stdCount = Integer.parseInt(txtCount.getText().trim());
            float duration = Float.parseFloat(txtDuration.getText().trim());


            for (Subject s1 : subjectLists) {
                if (s1.getSubName().equals(subject.trim())) {
                    subId1 = s1.getSubId();
                    subCount++;
                }
            }
            subCount = 0;
            for (Tag t1 : tagLists) {
                if (t1.getTagName().equals(tag.trim())) {
                    tagId = t1.getTagId();
                    subCount++;
                }
            }

            subCount = 0;
            for (Object m : this.mainGroupLists
            ) {
                if (m instanceof MainGroup && groupId.equals(((MainGroup) m).getGroupid())) {
                    gId = ((MainGroup) m).getId();
                    subCount++;


                }
                if (m instanceof SubGroup && groupId.equals(((SubGroup) m).getSubgroupid())) {
                    subGroupId = ((SubGroup) m).getId();
                    subCount++;

                }
            }

            String isConsecutive;
            if (subId1 != null) {
                if (tagId != 0) {
                    if (stdCount != 0) {
                        if (duration != 0) {
                            SubjectService subjectService = new SubjectServiceImpl();
                            Subject sub = subjectService.getCategory(subId1);
                            String isParallel;
                            String category;
                            if (sub.getSubType().equalsIgnoreCase("Optional")) {
                                isParallel = "Yes";
                                category = sub.getCategory();
                            } else {
                                isParallel = "No";
                                category = null;
                            }
                            if (groupType.equalsIgnoreCase("Main")) {
                                isConsecutive = "Yes";
                                Session session = new Session(subId1, tagId, Integer.toString(gId), null, stdCount, duration, isConsecutive, isParallel, category);
                                SessionService sessionService = new SessionServiceImpl();

                                try {
                                    boolean res = sessionService.addSession(session);
                                    int sessionId = sessionService.searchSessionByDetails(subId1, tagId, subGroupId, gId);
                                    Iterator<Lecturer> itr = list1.iterator();

                                    while (itr.hasNext()) {
                                        Lecturer lecture1 = itr.next();
                                        empId = lecture1.getEmpId();
                                        res = sessionService.addLectureSession(empId, sessionId);

                                    }

                                    if (res) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle(null);
                                        alert.setHeaderText(null);
                                        alert.setContentText("Success Adding Session!");

                                        alert.showAndWait();
                                        txtSubject.setText("");
                                        txtTag.setText("");
                                        txtGroup.setText("");
                                        txtCount.setText("");
                                        txtDuration.setText("");
                                        txtLecturer.setText("");
                                        list1.clear();
                                        this.setTableProperties();
                                    } else {
                                        Alert al = new Alert(Alert.AlertType.ERROR);
                                        al.setTitle(null);
                                        al.setContentText("Error Adding Session!");
                                        al.setHeaderText(null);
                                        al.showAndWait();
                                    }

                                } catch (SQLException e) {
                                    log.log(Level.SEVERE, e.getMessage());
                                }
                            } else {
                                isConsecutive = "No";
                                SubGroupService subGroupService = new SubGroupServiceImpl();
                                int mainn = subGroupService.getMainGroup(subGroupId);
                                Session session = new Session(subId1, tagId, Integer.toString(mainn), Integer.toString(subGroupId), stdCount, duration, isConsecutive, isParallel, category);
                                SessionService sessionService = new SessionServiceImpl();

                                try {
                                    boolean res = sessionService.addSession(session);

                                    int sessionId = sessionService.searchSessionByDetails(subId1, tagId, subGroupId, gId);
                                    Iterator<Lecturer> itr = list1.iterator();

                                    while (itr.hasNext()) {
                                        Lecturer lecture1 = itr.next();
                                        empId = lecture1.getEmpId();
                                        res = sessionService.addLectureSession(empId, sessionId);

                                    }

                                    if (res) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle(null);
                                        alert.setHeaderText(null);
                                        alert.setContentText("Success Adding Session!");

                                        alert.showAndWait();
                                        txtSubject.setText("");
                                        txtTag.setText("");
                                        txtGroup.setText("");
                                        txtCount.setText("");
                                        txtDuration.setText("");
                                        txtLecturer.setText("");
                                        this.setTableProperties();

                                    } else {
                                        Alert al = new Alert(Alert.AlertType.ERROR);
                                        al.setTitle(null);
                                        al.setContentText("Error Adding Session!");
                                        al.setHeaderText(null);
                                        al.showAndWait();
                                    }
                                } catch (SQLException e) {
                                    log.log(Level.SEVERE, e.getMessage());
                                }
                            }
                        } else {
                            Alert ald = new Alert(Alert.AlertType.ERROR);
                            ald.setTitle(null);
                            ald.setContentText("Invalid Time Duration");
                            ald.setHeaderText(null);
                            ald.showAndWait();
                        }

                    } else {
                        Alert alst = new Alert(Alert.AlertType.ERROR);
                        alst.setTitle(null);
                        alst.setContentText("Invalid Student Count");
                        alst.setHeaderText(null);
                        alst.showAndWait();
                    }

                } else {
                    Alert alt = new Alert(Alert.AlertType.ERROR);
                    alt.setTitle(null);
                    alt.setContentText("Tag Is not Exists In this System");
                    alt.setHeaderText(null);
                    alt.showAndWait();
                }

            } else {
                Alert als = new Alert(Alert.AlertType.ERROR);
                als.setTitle(null);
                als.setContentText("Subject Is not Exists In this System");
                als.setHeaderText(null);
                als.showAndWait();
            }

        } catch (NumberFormatException | SQLException ex) {
            Alert als = new Alert(Alert.AlertType.ERROR);
            als.setTitle(null);
            als.setContentText("Enter Time & Duration in Correct Format");
            als.setHeaderText(null);
            als.showAndWait();
            log.log(Level.SEVERE, ex.getMessage());
        }

    }


    @FXML
    void AddLecturer(ActionEvent event) {

        String lectureName1 = txtLecturer.getText();
        int lecId1 = 0;

        for (Lecturer l1 : lectureLists) {
            if (l1.getEmpName().equals(lectureName1.trim())) {
                lecId1 = l1.getEmpId();
                lecCount++;
            }
        }
        if (lecId1 != 0) {
            Lecturer l1 = new Lecturer(lecId1, lectureName1);
            int duplicateCount = 0;
            for (Lecturer l2 : list1
            ) {
                if (l2.getEmpId() == lecId1) {
                    duplicateCount++;
                }
            }
            if (duplicateCount == 0) {
                list1.add(l1);
                lectTbl.setItems(FXCollections.observableArrayList(list1));
                txtLecturer.setText("");

            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Lecturer is Already In the Table!");
                al.setHeaderText(null);
                al.showAndWait();
                duplicateCount = 0;
            }

        }
        txtLecturer.setText("");
    }

    @FXML
    void SelectTag(ActionEvent event) {
        String tag = txtTag.getText();
        if ((tag.equalsIgnoreCase("Lecture") || (tag.equalsIgnoreCase("Tute")))) {

            btnRadioMain.setSelected(true);
            btnRadioSub.setSelected(false);
            this.loadAllMainGroupDetails();
        } else {
            btnRadioSub.setSelected(true);
            btnRadioMain.setSelected(false);
            this.loadAllSubGroupDetails();


        }

    }

    private void loadAllMainGroupDetails() {
        try {
            ArrayList<MainGroup> mainLists = this.mainGroupServices.getAllMainGroupDetails();
            maingroupNameLists.clear();
            mainGroupLists.clear();
            if (autoCompletionBindings != null) {
                autoCompletionBindings.dispose();
            }
            for (MainGroup m1 : mainLists
            ) {
                maingroupNameLists.add(m1.getGroupid());
                mainGroupLists.add(m1);
            }
            autoCompletionBindings = TextFields.bindAutoCompletion(txtGroup, maingroupNameLists);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    private void loadAllSubGroupDetails() {
        try {
            ArrayList<SubGroup> subLists = this.subGroupServices.getAllSubGroupDetails(0);

            maingroupNameLists.clear();
            mainGroupLists.clear();
            if (autoCompletionBindings != null) {
                autoCompletionBindings.dispose();
            }
            for (SubGroup s1 : subLists
            ) {
                maingroupNameLists.add(s1.getSubgroupid());
                mainGroupLists.add(s1);
            }
            autoCompletionBindings = TextFields.bindAutoCompletion(txtGroup, maingroupNameLists);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    public void loadAllLectureDetails() {
        try {
            ArrayList<Lecturer> lec = lecturerServices.getAllLecturerDetails();
            for (Lecturer l : lec
            ) {
                lectureNameLists.add(l.getEmpName());
                lectureLists.add(l);
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        TextFields.bindAutoCompletion(txtLecturer, lectureNameLists);
    }

    public void loadAllSubjectDetails() {
        try {
            ArrayList<Subject> subjects = subjectServices.getAllSubjectDetails();
            for (Subject s : subjects
            ) {
                subjectLists.add(s);
                subNameLists.add(s.getSubName());
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        TextFields.bindAutoCompletion(txtSubject, subNameLists);
    }

    public void loadAllTagDetails() {
        try {
            ArrayList<Tag> tag = tagServices.getAllDetails();
            for (Tag t : tag
            ) {
                tagLists.add(t);
                tagNameLists.add(t.getTagName());
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        TextFields.bindAutoCompletion(txtTag, tagNameLists);
    }

    public void setTableProperties() {
        lectTbl.getSelectionModel().getTableView().getItems().clear();
        lectTbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("empId"));
        lectTbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("empName"));
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<Lecturer, Boolean>, TableCell<Lecturer, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<Lecturer, Boolean>, TableCell<Lecturer, Boolean>>() {
                @Override
                public TableCell<Lecturer, Boolean> call(TableColumn<Lecturer, Boolean> param) {
                    final TableCell<Lecturer, Boolean> cell = new TableCell<Lecturer, Boolean>() {
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
                                    Lecturer lecturer = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteLecturer(lecturer.getEmpId());
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lecturerServices = new LectureServiceImpl();
        lectureNameLists = new ArrayList<>();
        lectureLists = new ArrayList<>();
        subjectServices = new SubjectServiceImpl();
        subNameLists = new ArrayList<>();
        subjectLists = new ArrayList<>();
        tagServices = new TagServiceImpl();
        tagLists = new ArrayList<>();
        tagNameLists = new ArrayList<>();
        mainGroupServices = new MainGroupServiceImpl();
        mainGroupLists = new ArrayList<>();
        maingroupNameLists = new ArrayList<>();
        subGroupServices = new SubGroupServiceImpl();
        this.loadAllLectureDetails();
        this.loadAllSubjectDetails();
        this.loadAllTagDetails();
        this.setTableProperties();
        btnRadioMain.setDisable(true);
        btnRadioSub.setDisable(true);
    }

    public void deleteLecturer(int empId1) {

        Iterator<Lecturer> itr = list1.iterator();
        while (itr.hasNext()) {
            Lecturer lecture1 = itr.next();
            if (lecture1.getEmpId() == empId1) {
                itr.remove();
            }
        }

        setTableProperties();
        lectTbl.setItems(FXCollections.observableArrayList(list1));

    }
}
