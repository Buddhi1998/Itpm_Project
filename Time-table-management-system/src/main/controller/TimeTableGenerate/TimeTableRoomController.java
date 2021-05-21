package main.controller.TimeTableGenerate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.Pdf.PrintLecturerTimeTable;
import main.model.Building;
import main.model.Room;
import main.model.RoomTimeTable;
import main.service.*;
import main.service.impl.*;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimeTableRoomController implements Initializable {

    @FXML
    private Button btnGenerate;

    @FXML
    private TextField txtBuildingOpt1;

    @FXML
    private TextField txtRoom;

    @FXML
    private ComboBox<String> cmbCenter;

    private ArrayList<String> roomName;
    private ArrayList<String> buildingName;
    private AutoCompletionBinding<String> autoCompletionBinding2;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private TimeTableGenerateService timeTableGenerateService;
    String timeSlot = "";
    double workingHours = 0;
    int workingDaysCount = 0;
    PrefTagService prefTagService;
    private WorkingDaysService workingDaysService;
    double hourSize = 0;

    public static final Logger log = Logger.getLogger(TimeTableRoomController.class.getName());

    public int getCountOfWorkingDays() {
        int countWorkingDays = 0;
        try {
            countWorkingDays = workingDaysService.getCountOfWorkingDays();
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return countWorkingDays;
    }

    public double getWorkingTime() {
        double workingTime = 0;
        try {
            workingTime = workingDaysService.getWorkingTime();
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return workingTime;
    }

    public String getWorkingTimeType() {
        String type = "";
        try {
            type = workingDaysService.getWorkingTimeType();
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return type;
    }

    @FXML
    void generateTimeTable(ActionEvent event) {
        String[][] lectSession;
        String center = cmbCenter.getValue();
        String building = txtBuildingOpt1.getText();
        String room = txtRoom.getText();
        timeSlot = this.getWorkingTimeType();
        workingHours = this.getWorkingTime();
        workingDaysCount = this.getCountOfWorkingDays();

        if (!center.isEmpty()) {
            if (!building.isEmpty()) {
                if (!room.isEmpty()) {
                    try {
                        ArrayList<RoomTimeTable> list = this.timeTableGenerateService.getTimeTableForRoom(center, building, room);
                        if (!list.isEmpty()) {
                            String[][] timeString = this.getStringArray();
                            lectSession = new String[workingDaysCount][(int) hourSize];
                            for (RoomTimeTable rm : list
                            ) {
                                int dayNumber = getDayNumber(rm.getDay());
                                String groupId = "";
                                if (rm.getSubGroupId() != null) {
                                    groupId = this.timeTableGenerateService.getSubGroupId(Integer.parseInt(rm.getSubGroupId()));
                                } else {
                                    groupId = this.timeTableGenerateService.getMainGroupId(Integer.parseInt(rm.getMainGroupId()));
                                }
                                String lecture = "";
                                ArrayList<String> lecturerNames = this.timeTableGenerateService.getLecturerNamesAccordingTo(rm.getSessionId().trim());
                                StringBuilder sb = new StringBuilder();
                                for (String lec : lecturerNames
                                ) {
                                    sb.append(lec).append(",");
                                }
                                lecture = sb.deleteCharAt(sb.length() - 1).toString();
                                if (rm.getDay().equalsIgnoreCase("Monday")) {
                                    for (int i = 0; i < hourSize; i++) {
                                        if (rm.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                            String session = rm.getSubCode() + "\n" + rm.getSubName() + "\n(" +
                                                    rm.getTagName() + ")\n" + lecture + "\n" + groupId;
                                            lectSession[dayNumber][i] = session;
                                        }
                                    }
                                } else if (rm.getDay().equalsIgnoreCase("Tuesday")) {
                                    for (int i = 0; i < hourSize; i++) {
                                        if (rm.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                            String session = rm.getSubCode() + "\n" + rm.getSubName() + "\n(" +
                                                    rm.getTagName() + ")\n" + lecture + "\n" + groupId;
                                            lectSession[dayNumber][i] = session;
                                        }
                                    }
                                } else if (rm.getDay().equalsIgnoreCase("Wednesday")) {
                                    for (int i = 0; i < hourSize; i++) {
                                        if (rm.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                            String session = rm.getSubCode() + "\n" + rm.getSubName() + "\n(" +
                                                    rm.getTagName() + ")\n" + lecture + "\n" + groupId;
                                            lectSession[dayNumber][i] = session;
                                        }
                                    }
                                } else if (rm.getDay().equalsIgnoreCase("Thursday")) {
                                    for (int i = 0; i < hourSize; i++) {
                                        if (rm.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                            String session = rm.getSubCode() + "\n" + rm.getSubName() +
                                                    "\n(" + rm.getTagName() + ")\n" + lecture + "\n" + groupId;
                                            lectSession[dayNumber][i] = session;
                                        }
                                    }
                                } else if (rm.getDay().equalsIgnoreCase("Friday")) {
                                    for (int i = 0; i < hourSize; i++) {
                                        if (rm.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                            String session = rm.getSubCode() + "\n" + rm.getSubName() + "\n(" +
                                                    rm.getTagName() + ")\n" + lecture + "\n" + groupId;
                                            lectSession[dayNumber][i] = session;
                                        }
                                    }
                                } else if (rm.getDay().equalsIgnoreCase("Saturday")) {
                                    for (int i = 0; i < hourSize; i++) {
                                        if (rm.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                            String session = rm.getSubCode() + "\n" + rm.getSubName() + "\n(" +
                                                    rm.getTagName() + ")\n" + lecture + "\n" + groupId;
                                            lectSession[dayNumber][i] = session;
                                        }
                                    }
                                } else if (rm.getDay().equalsIgnoreCase("Sunday")) {
                                    for (int i = 0; i < hourSize; i++) {
                                        if (rm.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                            String session = rm.getSubCode() + "\n" + rm.getSubName() + "\n(" +
                                                    rm.getTagName() + ")\n" + rm.getRomm() + "\n" + groupId;
                                            lectSession[dayNumber][i] = session;
                                        }
                                    }
                                }

                            }
                            String[][] swapArray = new String[(int) hourSize][workingDaysCount];
                            String[][] swapArrayTime = new String[(int) hourSize][workingDaysCount];
                            for (int i = 0; i < (int) hourSize; i++) {
                                for (int j = 0; j < workingDaysCount; j++) {
                                    swapArray[i][j] = lectSession[j][i];
                                    swapArrayTime[i][j] = timeString[j][i];
                                }
                            }
                            PrintLecturerTimeTable printTimeTable = new PrintLecturerTimeTable();
                            printTimeTable.generateCustomerReportPdf(swapArray, swapArrayTime, workingDaysCount, (int) hourSize, room);
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle(null);
                            al.setContentText("Time Table Is Generated");
                            al.setHeaderText(null);
                            al.showAndWait();

                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Any Time Table Not Found For This Room");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    } catch (SQLException e) {
                        log.log(Level.SEVERE, e.getMessage());
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Room field is empty!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Buidling field is empty!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Center field is empty!");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }

    public String[][] getStringArray() {
        timeSlot = this.getWorkingTimeType();
        workingHours = this.getWorkingTime();
        workingDaysCount = this.getCountOfWorkingDays();
        hourSize = 0;
        if (timeSlot.equals("One Hour")) {
            hourSize = workingHours;
        } else {
            hourSize = workingHours * 2;
        }
        String[][] timeString = new String[workingDaysCount][(int) hourSize];

        int hoursCount = 8;
        int minutCount = 30;
        for (int i = 0; i < workingDaysCount; i++) {
            for (int j = 0; j < hourSize; j++) {
                String tempTime = "";
                if (timeSlot.equals("One Hour")) {
                    if (hoursCount < 10) {
                        tempTime = "0" + hoursCount + ":" + minutCount;
                    } else {
                        tempTime = hoursCount + ":" + minutCount;
                    }
                    hoursCount += 1;
                    if (hoursCount < 10) {
                        timeString[i][j] = tempTime + "-0" + hoursCount + ":" + minutCount;
                    } else {
                        timeString[i][j] = tempTime + "-" + hoursCount + ":" + minutCount;
                    }

                } else {
                    if (minutCount != 30) {
                        if (hoursCount < 10) {
                            tempTime = "0" + hoursCount + ":" + minutCount + "0";
                        } else {
                            tempTime = hoursCount + ":" + minutCount + "0";
                        }

                    } else {
                        if (hoursCount < 10) {
                            tempTime = "0" + hoursCount + ":" + minutCount;
                        } else {
                            tempTime = hoursCount + ":" + minutCount;
                        }

                    }
                    minutCount += 30;
                    if (minutCount >= 60) {
                        hoursCount++;
                        minutCount = 0;
                    }
                    if (minutCount != 30) {
                        if (hoursCount < 10) {
                            timeString[i][j] = tempTime + "-0" + hoursCount + ":" + minutCount + "0";
                        } else {
                            timeString[i][j] = tempTime + "-" + hoursCount + ":" + minutCount + "0";
                        }

                    } else {
                        if (hoursCount < 10) {
                            timeString[i][j] = tempTime + "-0" + hoursCount + ":" + minutCount;
                        } else {
                            timeString[i][j] = tempTime + "-" + hoursCount + ":" + minutCount;

                        }
                    }
                }
            }
            hoursCount = 8;
            minutCount = 30;
        }
        return timeString;
    }


    public int getDayNumber(String day) {
        int number = 0;
        if (day.equalsIgnoreCase("Monday")) {
            number = 0;
        } else if (day.equalsIgnoreCase("Tuesday")) {
            number = 1;
        } else if (day.equalsIgnoreCase("Wednesday")) {
            number = 2;
        } else if (day.equalsIgnoreCase("Thursday")) {
            number = 3;
        } else if (day.equalsIgnoreCase("Friday")) {
            number = 4;
        } else if (day.equalsIgnoreCase("Saturday")) {
            number = 5;
        } else if (day.equalsIgnoreCase("Sunday")) {
            number = 6;
        }
        return number;
    }


    @FXML
    void getBuilding(ActionEvent event) {
        String center = cmbCenter.getValue();
        try {
            BuildingService buildingService = new BuildingServiceImpl();
            ArrayList<Building> list = buildingService.searchBuildingDetailsByUsingCenter(center);

            buildingName = new ArrayList<>();

            for (Building building : list
            ) {
                buildingName.add(building.getBuilding());
            }
            if (autoCompletionBinding != null) {
                autoCompletionBinding.dispose();
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(txtBuildingOpt1, buildingName);

        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
    }

    @FXML
    void getRoom(ActionEvent event) {
        String building = txtBuildingOpt1.getText();
        try {
            RoomService roomService = new RoomServiceImpl();

            ArrayList<Room> list = roomService.searchRoomDetailsByUsingbuilding(building);
            for (Room room : list
            ) {

                roomName.add(room.getRoom());
            }
            if (autoCompletionBinding2 != null) {
                autoCompletionBinding2.dispose();
            }
            autoCompletionBinding2 = TextFields.bindAutoCompletion(txtRoom, roomName);

        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roomName = new ArrayList<>();
        buildingName = new ArrayList<>();
        this.prefTagService = new PrefTagServiceImpl();
        this.timeTableGenerateService = new TimeTableGenerateServiceImpl();
        this.workingDaysService = new WorkingDaysServiceImpl();
    }
}
