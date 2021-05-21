package main.controller.WorkSchedule;

import com.gluonhq.charm.glisten.control.ToggleButtonGroup;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import main.model.WorkingDaysMain;
import main.model.WorkingDaysSub;
import main.model.WorkingdDaysAndHours;
import main.service.WorkingDaysService;
import main.service.WorkingHoursService;
import main.service.impl.WorkingDaysServiceImpl;
import main.service.impl.WorkingHoursImpl;

// Referenced classes of package main.controller.WorkSchedule:
//            WeekdaysController, WeekendController
public class WorkingDaysController implements Initializable {

    ObservableList WeekdayNumber;
    ObservableList timeSlot;

    SpinnerValueFactory svfH;
    SpinnerValueFactory svfM;

    @FXML
    private Button btnAdd;
    @FXML
    private ComboBox cmbNoDays;

    @FXML
    public ComboBox cmbTimeSlot;

    @FXML
    private TableView<WorkingdDaysAndHours> tblWorkingDays;
    @FXML
    private TableColumn<WorkingdDaysAndHours, Boolean> colEdit;
    @FXML
    private TableColumn<WorkingdDaysAndHours, Boolean> colDelete;
    @FXML
    private BorderPane pnlWorkingDays;
    private Label pnlW;
    @FXML
    private RadioButton btnRadioWeekEnd;
    @FXML
    private RadioButton btnRadioWeekday;
    @FXML
    private ToggleButtonGroup togglebtnDays;
    private WorkingDaysService workingDaysService;
    private boolean updateStatus = false;
    private int updateId = 0;
    public static final Logger log = Logger.getLogger(WorkingDaysController.class.getName());
    private static final String MONDAY = "Monday";
    private static final String TUESDAY = "Tuesday";
    private static final String WEDNESDAY = "Wednesday";
    private static final String THURSDAY = "Thursday";
    private static final String FRIDAY = "Friday";
    private static final String SATURDAY = "Saturday";
    private static final String SUNDAY = "Sunday";
    private static final String WEEKEND = "Weekends";
    private static final String WEEKDAY = "Weekdays";
    private WorkingHoursService workingHoursService;
    @FXML
    public CheckBox checkMON;
    @FXML
    public CheckBox checkTUE;
    @FXML
    public CheckBox checkWED;
    @FXML
    public CheckBox checkTHU;
    @FXML
    public CheckBox checkFRI;
    @FXML
    public CheckBox checkSAT;
    @FXML
    public CheckBox checkSUN;
    @FXML
    private Spinner spinnerHour;
    @FXML
    private Spinner spinnerMinute;

    static boolean monday = false;
    static boolean tuesday = false;
    static boolean wednesday = false;
    static boolean thursday = false;
    static boolean friday = false;
    static boolean saturday = false;
    static boolean sunday = false;
    static int count = 0;


    public WorkingDaysController() {
        svfH = new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24, 0);
        svfM = new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0);
        this.workingDaysService = new WorkingDaysServiceImpl();
        this.workingHoursService = new WorkingHoursImpl();
    }

    @FXML
    void addDetails(ActionEvent event) {
        String noDays = (String) cmbNoDays.getValue();

        if (count != Integer.parseInt(noDays)) {
            Alert al = new Alert(Alert.AlertType.ERROR,
                    "Number of working days doesn't match the days selected !");
            al.showAndWait();
        } else {
            if (((Integer) spinnerHour.getValue()).intValue() < 1 && ((Integer) spinnerMinute.getValue()).intValue() < 30) {
                Alert al = new Alert(Alert.AlertType.ERROR,
                        "Atleast 30min time is needed for Thirty minute time slot ");
                al.showAndWait();
            } else if (((Integer) spinnerHour.getValue()).intValue() < 1) {

                Alert al = new Alert(Alert.AlertType.ERROR,
                        "Atleast 1 hour time is needed for One hour time slot");
                al.showAndWait();
            } else {

                if (!updateStatus) {
                    saveDetails();
                } else {
                    updateDetails();
                }

            }
        }

//        int countcheckboxWeekdays = Integer.valueOf(WeekdaysController.count).intValue();
//        int countcheckboxWeekends = Integer.valueOf(WeekendController.count).intValue();

//        }  else if (!((String) cmbNoDays.getValue()).matches(String.valueOf(countcheckboxWeekdays)) && !((String) cmbNoDays.getValue()).matches(String.valueOf(countcheckboxWeekends))) {
//            Alert al = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
//            al.setTitle(null);
//            al.setContentText("Number of working days doesn't match the days selected !");
//            al.setHeaderText(null);
//            al.showAndWait();


    }

    @FXML
    public void selectedDays(ActionEvent event) {
        count = 0;
        if (checkMON.isSelected()) {
            monday = true;
            count++;
        } else {
            monday = false;
        }
        if (checkTUE.isSelected()) {
            tuesday = true;
            count++;
        } else {
            tuesday = false;
        }
        if (checkWED.isSelected()) {
            wednesday = true;
            count++;
        } else {
            wednesday = false;
        }
        if (checkTHU.isSelected()) {
            thursday = true;
            count++;
        } else {
            thursday = false;
        }
        if (checkFRI.isSelected()) {
            friday = true;
            count++;
        } else {
            friday = false;
        }
        if (checkSAT.isSelected()) {
            saturday = true;
            count++;
        } else {
            saturday = false;
        }
        if (checkSUN.isSelected()) {
            sunday = true;
            count++;
        } else {
            sunday = false;
        }
    }

    public void resetAllSelectedDays() {
        checkMON.setSelected(false);
        checkTUE.setSelected(false);
        checkWED.setSelected(false);
        checkTHU.setSelected(false);
        checkFRI.setSelected(false);
        checkSAT.setSelected(false);
        checkSUN.setSelected(false);

    }


    public void saveDetails() {
        try {
            ArrayList<String> arrayListDay = new ArrayList<>();

            int noOfDays = Integer.parseInt(cmbNoDays.getValue().toString());

            if (monday) {
                arrayListDay.add(MONDAY);
            }
            if (tuesday) {
                arrayListDay.add(TUESDAY);
            }
            if (wednesday) {
                arrayListDay.add(WEDNESDAY);
            }
            if (thursday) {
                arrayListDay.add(THURSDAY);
            }
            if (friday) {
                arrayListDay.add(FRIDAY);
            }
            if (saturday) {
                arrayListDay.add(SATURDAY);
            }
            if (sunday) {
                arrayListDay.add(SUNDAY);
            }

            WorkingDaysMain workingDaysMain = new WorkingDaysMain(0, "", noOfDays);
            System.out.println("workingDaysMain = " + workingDaysMain);


            String selectedTimeSlot = cmbTimeSlot.getSelectionModel().getSelectedItem().toString();
            boolean canAddMore = this.workingDaysService.getWorkingDaysCanAddMore();
            if (!canAddMore) {


                int lastId = this.workingDaysService.addWorkingDays(workingDaysMain);
                System.out.println("lastId = " + lastId);
                int count = 0;
                if (lastId != 0) {
                    for (String day : arrayListDay) {
                        WorkingDaysSub workingDaysSub = new WorkingDaysSub();

                        workingDaysSub.setWorkingId(lastId);
                        workingDaysSub.setWorkingday(day);
                        workingDaysSub.setWorkingTimeSlotType(selectedTimeSlot);

                        workingDaysSub.setWorkingTime(((Integer) spinnerHour.getValue()).intValue() + "." + ((Integer) spinnerMinute.getValue()).intValue());
                        System.out.println("workingDaysSub = " + workingDaysSub);
                        boolean isAddedSubDays = this.workingDaysService.addWorkingDaysSub(workingDaysSub);
                        if (!isAddedSubDays) {

                            Alert al = new Alert(Alert.AlertType.ERROR,
                                    "Added Failed.!");
                            al.showAndWait();

                        }
                        count++;
                    }
                }


                if (count == arrayListDay.size()) {
                    Alert al = new Alert(Alert.AlertType.INFORMATION,
                            "Added Successfully..!");
                    al.showAndWait();
                    clearAllFields();
                    this.getAllDetails();

                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText(" Added Failed..! ");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText(" You can't add more working days to this week..! ");
                al.setHeaderText(null);
                al.showAndWait();
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    private void clearAllFields() {
        cmbNoDays.getItems().clear();
        cmbNoDays.setItems(FXCollections.observableArrayList(new String[]{"1", "2", "3", "4", "5", "6", "7"}));
        resetAllSelectedDays();
        spinnerHour.setValueFactory(new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24, 0));
        spinnerMinute.setValueFactory(new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0));
    }

    public void initialize(URL location, ResourceBundle resources) {
        WeekdayNumber = FXCollections.observableArrayList(new String[]{"1", "2", "3", "4", "5", "6", "7"});
        timeSlot = FXCollections.observableArrayList("One hour", "Thirty minute");
        spinnerHour.setValueFactory(svfH);
        spinnerMinute.setValueFactory(svfM);
        cmbNoDays.setItems(WeekdayNumber);
        cmbNoDays.getSelectionModel().select(0);
        cmbTimeSlot.setItems(timeSlot);
        cmbTimeSlot.getSelectionModel().select(1);
        setTableProperties();

        this.getAllDetails();
    }

    public void getAllDetails() {
        try {
            ArrayList<WorkingdDaysAndHours> list = this.workingDaysService.getAllNoOfWorkingDays();
            tblWorkingDays.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    public void setTableProperties() {
        tblWorkingDays.getSelectionModel().getTableView().getItems().clear();
        tblWorkingDays.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("workingId"));
        tblWorkingDays.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("noOfDays"));
        tblWorkingDays.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("subId"));
        tblWorkingDays.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("workingday"));
        tblWorkingDays.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        tblWorkingDays.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("timeSlotType"));
        colEdit.setCellFactory(cellFactoryBtnEdit);
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<WorkingdDaysAndHours, Boolean>, TableCell<WorkingdDaysAndHours, Boolean>> cellFactoryBtnEdit
            = new Callback<TableColumn<WorkingdDaysAndHours, Boolean>, TableCell<WorkingdDaysAndHours, Boolean>>() {
        @Override
        public TableCell<WorkingdDaysAndHours, Boolean> call(TableColumn<WorkingdDaysAndHours, Boolean> param) {
            final TableCell<WorkingdDaysAndHours, Boolean> cell = new TableCell<WorkingdDaysAndHours, Boolean>() {
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
                            WorkingdDaysAndHours mainDays = getTableView().getItems().get(getIndex());
                            updateStatus = true;
                            btnAdd.setText("Update");
                            getSelectedEditDetails();
                            updateId = mainDays.getWorkingId();
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

    Callback<TableColumn<WorkingdDaysAndHours, Boolean>, TableCell<WorkingdDaysAndHours, Boolean>> cellFactoryBtnDelete
            = new Callback<TableColumn<WorkingdDaysAndHours, Boolean>, TableCell<WorkingdDaysAndHours, Boolean>>() {
        @Override
        public TableCell<WorkingdDaysAndHours, Boolean> call(TableColumn<WorkingdDaysAndHours, Boolean> param) {
            final TableCell<WorkingdDaysAndHours, Boolean> cell = new TableCell<WorkingdDaysAndHours, Boolean>() {
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
                            WorkingdDaysAndHours daysMain = getTableView().getItems().get(getIndex());
                            Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                            a2.setTitle(null);
                            a2.setHeaderText("Are you sure you want to delete this record ?");
                            a2.setContentText(null);
                            Optional<ButtonType> result = a2.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                deleteWorkingDay(daysMain.getWorkingId());
                                getAllDetails();
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

    public void deleteWorkingDay(int workingId) {
        try {
            boolean isDeleted = this.workingDaysService.deleteWorkingDay(workingId);

            if (isDeleted) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Deleted SuccessFully ");
                al.setHeaderText(null);
                al.showAndWait();
                clearAllFields();
                this.getAllDetails();
            } else {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Deleted Fail ");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    public void getSelectedEditDetails() {
        try {
            ArrayList<WorkingdDaysAndHours> allWorkingDetailsByWorkID = this.workingDaysService.getAllWorkingDetailsByWorkID(updateId);
            for (WorkingdDaysAndHours wdh : allWorkingDetailsByWorkID) {
                cmbNoDays.getSelectionModel().select(wdh.getNoOfDays() - 1);

                if (wdh.getWorkingday().equals(MONDAY)) {
                    checkMON.setSelected(true);
                }
                if (wdh.getWorkingday().equals(TUESDAY)) {
                    checkTUE.setSelected(true);
                }
                if (wdh.getWorkingday().equals(WEDNESDAY)) {
                    checkWED.setSelected(true);
                }
                if (wdh.getWorkingday().equals(THURSDAY)) {
                    checkTHU.setSelected(true);
                }
                if (wdh.getWorkingday().equals(FRIDAY)) {
                    checkFRI.setSelected(true);
                }
                if (wdh.getWorkingday().equals(SATURDAY)) {
                    checkSAT.setSelected(true);
                }
                if (wdh.getWorkingday().equals(SUNDAY)) {
                    checkSUN.setSelected(true);
                }

                spinnerHour.getValueFactory().setValue(Integer.parseInt(wdh.getTimeSlot().substring(0, wdh.getTimeSlot().indexOf("."))));
                spinnerMinute.getValueFactory().setValue(Integer.parseInt(wdh.getTimeSlot().substring(wdh.getTimeSlot().lastIndexOf(".") + 1)));

                if (wdh.getTimeSlotType().equals("One Hour")) {
                    cmbTimeSlot.getSelectionModel().select(0);
                }
                if (wdh.getTimeSlotType().equals("Thirty minute")) {
                    cmbTimeSlot.getSelectionModel().select(1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateDetails() {
        try {
            ArrayList<String> updateArrayListDay = new ArrayList<>();

            int noOfDays = Integer.parseInt(cmbNoDays.getValue().toString());

            if (monday) {
                updateArrayListDay.add(MONDAY);
            }
            if (tuesday) {
                updateArrayListDay.add(TUESDAY);
            }
            if (wednesday) {
                updateArrayListDay.add(WEDNESDAY);
            }
            if (thursday) {
                updateArrayListDay.add(THURSDAY);
            }
            if (friday) {
                updateArrayListDay.add(FRIDAY);
            }
            if (saturday) {
                updateArrayListDay.add(SATURDAY);
            }
            if (sunday) {
                updateArrayListDay.add(SUNDAY);
            }

            WorkingDaysMain workingDaysMain = new WorkingDaysMain(updateId, "", noOfDays);
            boolean isUpdated = this.workingDaysService.updateNoOfWorkingDays(workingDaysMain);
            int count = 0;

            if (isUpdated) {

                boolean b = workingDaysService.deleteWorkingDaysfromSub(updateId);

                String selectedTimeSlot = cmbTimeSlot.getSelectionModel().getSelectedItem().toString();


                for (String day : updateArrayListDay) {
                    WorkingDaysSub workingDaysSub = new WorkingDaysSub();
                    workingDaysSub.setWorkingId(updateId);
                    workingDaysSub.setWorkingday(day);
                    workingDaysSub.setWorkingTime(((Integer) spinnerHour.getValue()).intValue() + "." + ((Integer) spinnerMinute.getValue()).intValue());
                    workingDaysSub.setWorkingTimeSlotType(selectedTimeSlot);
                    this.workingDaysService.addWorkingDaysSub(workingDaysSub);
                    count++;
                }
                if (count == updateArrayListDay.size()) {
                    Alert al = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                    al.setTitle(null);
                    al.setContentText("Updated Successfully ");
                    btnAdd.setText("Add");
                    clearAllFields();
                    al.setHeaderText(null);
                    al.showAndWait();
                    updateStatus = false;
                    this.getAllDetails();
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText(" Updated Fail ");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            }
        } catch (
                SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }
}