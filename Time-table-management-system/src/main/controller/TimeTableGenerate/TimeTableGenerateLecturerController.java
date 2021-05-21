package main.controller.TimeTableGenerate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.Pdf.PrintLecturerTimeTable;
import main.model.Lecturer;
import main.model.LecturerTimeTable;
import main.service.LecturerService;
import main.service.TimeTableGenerateService;
import main.service.WorkingDaysService;
import main.service.impl.LectureServiceImpl;
import main.service.impl.TimeTableGenerateServiceImpl;
import main.service.impl.WorkingDaysServiceImpl;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimeTableGenerateLecturerController implements Initializable {


    @FXML
    private TextField txtSubID;

    public static final String MONDAY ="Monday";
    public static final String TUESDAY ="Tuesday";
    public static final String WEDNESDAY ="Wednesday";
    public static final String THURSDAY ="Thursday";
    public static final String FRIDAY ="Friday";
    public static final String SATURDAY ="Saturday";
    public static final String SUNDAY ="Sunday";

    private LecturerService lectureService;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private List<String> lecNameList;
    private TimeTableGenerateService timeTableGenerateService;
    private WorkingDaysService workingDaysService;
    String timeSlot = "";
    double workingHours = 0;
    int workingDaysCount = 0;
    double hourSize = 0;
    public static final Logger log = Logger.getLogger(TimeTableGenerateLecturerController.class.getName());
    String[][] lectSession;

    public void assignSession(LecturerTimeTable lec,int dayNumber,String groupId,String [][]timeString){
        for (int i = 0; i < hourSize; i++) {
            if (lec.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                String session = lec.getSubCode() + "\n" + lec.getSubName() + "\n(" +
                        lec.getTagName() + ")\n" + lec.getRomm() + "\n" + groupId;
                lectSession[dayNumber][i] = session;
            }
        }
    }

    @FXML
    void generateTimeTable(ActionEvent event) {
        String lecName = txtSubID.getText();

        if (!lecName.isEmpty()) {
            String[][] timeString = this.getStringArray();
            timeSlot = this.getWorkingTimeType();
            workingHours = this.getWorkingTime();
            workingDaysCount = this.getCountOfWorkingDays();

            try {
                ArrayList<LecturerTimeTable> lecturerTimeTables = timeTableGenerateService.getLectureTimeTableDetails(lecName);
                if (!lecturerTimeTables.isEmpty()) {
                    lectSession = new String[workingDaysCount][(int) hourSize];
                    for (LecturerTimeTable lec : lecturerTimeTables
                    ) {
                        int dayNumber = getDayNumber(lec.getDay());
                        String groupId = "";
                        if (lec.getSubGroupId() != null) {
                            groupId = this.timeTableGenerateService.getSubGroupId(Integer.parseInt(lec.getSubGroupId()));
                        } else {
                            groupId = this.timeTableGenerateService.getMainGroupId(Integer.parseInt(lec.getMainGroupId()));
                        }
                        assignSession(lec,dayNumber,groupId,timeString);
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
                    printTimeTable.generateCustomerReportPdf(swapArray, swapArrayTime, workingDaysCount, (int) hourSize, lecName);
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle(null);
                    al.setContentText("Time Table Is Generated");
                    al.setHeaderText(null);
                    al.showAndWait();
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Any Time Table Not Found For This Lecturer");
                    al.setHeaderText(null);
                    al.showAndWait();
                }


            } catch (SQLException e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Lecturer Field Is Empty");
            al.setHeaderText(null);
            al.showAndWait();
        }


    }

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

    private String getDay(int i) {
        String day = "";
        if (i == 0) {
            day = MONDAY;
        } else if (i == 1) {
            day = TUESDAY;
        } else if (i == 2) {
            day = WEDNESDAY;
        } else if (i == 3) {
            day = THURSDAY;
        } else if (i == 4) {
            day = FRIDAY;
        } else if (i == 5) {
            day = SATURDAY;
        } else if (i == 6) {
            day = SUNDAY;
        }
        return day;
    }

    public int getDayNumber(String day) {
        int number = 0;
        if (day.equalsIgnoreCase(MONDAY)) {
            number = 0;
        } else if (day.equalsIgnoreCase(TUESDAY)) {
            number = 1;
        } else if (day.equalsIgnoreCase(WEDNESDAY)) {
            number = 2;
        } else if (day.equalsIgnoreCase(THURSDAY)) {
            number = 3;
        } else if (day.equalsIgnoreCase(FRIDAY)) {
            number = 4;
        } else if (day.equalsIgnoreCase(SATURDAY)) {
            number = 5;
        } else if (day.equalsIgnoreCase(SUNDAY)) {
            number = 6;
        }
        return number;
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lectureService = new LectureServiceImpl();
        lecNameList = new ArrayList<>();
        this.getAllLecturerDetails();
        timeTableGenerateService = new TimeTableGenerateServiceImpl();
        workingDaysService = new WorkingDaysServiceImpl();
    }

    public void getAllLecturerDetails() {
        try {
            ArrayList<Lecturer> lec = lectureService.getAllLecturerDetails();
            lecNameList.clear();
            for (Lecturer l : lec) {
                lecNameList.add(l.getEmpName());
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(txtSubID, lecNameList);
            TextFields.bindAutoCompletion(txtSubID, lecNameList);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }

    }


}
