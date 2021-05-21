package main.controller.Lecturer;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import main.model.Lecturer;
import main.model.NotAvailableLecturer;
import main.service.LecturerService;
import main.service.impl.LectureServiceImpl;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;



public class NotAvailableLecturerController implements Initializable {

    @FXML
    private AnchorPane pnlNotAvailableLecturer;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<String> cmbDay;

    @FXML
    private TableView<NotAvailableLecturer> tblNotAvailableLecturer;

    @FXML
    private TableColumn<NotAvailableLecturer, Boolean> colDelete;

    @FXML
    private JFXTimePicker toTime;

    @FXML
    private JFXTimePicker fromTime;

    @FXML
    private TextField txtLecturer;

    @FXML
    private TextField txtSearchLecturer;
    AutoCompletionBinding<String> autoCompletionBinding;
    private LecturerService lectureService;
    private List<String> lecNameList;
    private List<Lecturer> lecObj;
    private String curTime;
    public static final Logger log = Logger.getLogger(MainGroupController.class.getName());

    @FXML
    void saveDetails(ActionEvent event) {
        String day = (String) cmbDay.getValue();
        String getToTime = toTime.getValue().toString();
        String getFromTime = fromTime.getValue().toString();
        String lecName = txtLecturer.getText();
        if(!day.isEmpty()){
            if(!getToTime.isEmpty()){
                if(!getFromTime.isEmpty()){
                    int count = 0;
                    NotAvailableLecturer nal = new NotAvailableLecturer();
                    for (Lecturer l1 :lecObj){
                        if(l1.getEmpName().equals(lecName)){
                            count++;
                            nal.setLectureId(l1.getEmpId());
                        }
                    }
                    if(count!=0){
                            nal.setDay(day);
                            nal.setFromTime(getFromTime);
                            nal.setToTime(getToTime);
                        boolean isAdded = false;
                        try {
                            isAdded = lectureService.addNotAvailableLectures(nal);

                        } catch (SQLException e) {
                            log.log(Level.SEVERE,e.getMessage());
                        }
                        if(isAdded){
                                Alert al = new Alert(Alert.AlertType.INFORMATION);
                                al.setTitle(null);
                                al.setContentText("Added Successfully ");
                                al.setHeaderText(null);
                                al.showAndWait();
                            cmbDay.setValue("");
                            toTime.setValue(LocalTime.parse(curTime));
                            fromTime.setValue(LocalTime.parse(curTime));
                            txtLecturer.setText("");
                            this.getNotAvailableLecturerDetails("");
                            }else{
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("    Added Fail");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }
                    }else{
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Lecturer Is not Exists In this System");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                }else{
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("  FromTime Field is Empty.Please Select From Time!!!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            }else{
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText(" ToTime Field is Empty.Please Select To Time!!!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        }else{
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText(" Day Field is Empty.Please Select Day!!!");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }

    @FXML
    void searchLecturer(ActionEvent event) {
        String lecName = txtSearchLecturer.getText();
        getNotAvailableLecturerDetails(lecName);
    }

    public void getAllLecturerDetails(){


        try {
            ArrayList<Lecturer> lec = lectureService.getAllLecturerDetails();
            lecNameList.clear();
            lecObj.clear();
            for (Lecturer l: lec) {
                lecObj.add(l);
                lecNameList.add(l.getEmpName());
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(txtLecturer, lecNameList);
            TextFields.bindAutoCompletion(txtSearchLecturer,lecNameList);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lectureService = new LectureServiceImpl();
        lecNameList = new ArrayList<>();
        lecObj = new ArrayList<>();
        this.getAllLecturerDetails();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        curTime = dtf.format(now);
        toTime.setValue(LocalTime.parse(curTime));
        fromTime.setValue(LocalTime.parse(curTime));
        this.setTableProperties();
        this.getNotAvailableLecturerDetails("");
    }

    private void getNotAvailableLecturerDetails(String name) {
        try {
            List<NotAvailableLecturer> nal = lectureService.getAllNotAvailableLecturers(name);

            tblNotAvailableLecturer.setItems(FXCollections.observableArrayList(nal));
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblNotAvailableLecturer.getSelectionModel().getTableView().getItems().clear();
        tblNotAvailableLecturer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("lectureName"));
        tblNotAvailableLecturer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("day"));
        tblNotAvailableLecturer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("fromTime"));
        tblNotAvailableLecturer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("toTime"));
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<NotAvailableLecturer, Boolean>, TableCell<NotAvailableLecturer, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<NotAvailableLecturer, Boolean>, TableCell<NotAvailableLecturer, Boolean>>() {
                @Override
                public TableCell<NotAvailableLecturer, Boolean> call(TableColumn<NotAvailableLecturer, Boolean> param) {
                    final TableCell<NotAvailableLecturer, Boolean> cell = new TableCell<NotAvailableLecturer, Boolean>() {
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
                                    NotAvailableLecturer nalv = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteNotAvailableLecturer(nalv.getId());
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

    public void deleteNotAvailableLecturer(int id){
        boolean isDeleted = false;
        try {
            isDeleted = this.lectureService.deleteNotAvailableLecturer(id);
            if (isDeleted) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("  Deleted SuccessFully");
                al.setHeaderText(null);
                al.showAndWait();
                this.getNotAvailableLecturerDetails("");
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("  Deleted Fail");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

}
