package main.controller.Dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import main.model.Dashboard;
import main.model.Dashboard2;
import main.service.DashboardService;
import main.service.impl.DashboardServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {


    @FXML
    private BarChart<String, Integer> subjectChart;

    @FXML
    private BarChart<String, Integer> employeeChart;

    @FXML
    private Label noOfLecturesLbl;

    @FXML
    private Label noOfSubjects;

    @FXML
    private Label noOfBuildings;

    @FXML
    private Label noOfRooms;


    private DashboardService dashboardService;

    public DashboardController() {
        this.dashboardService = new DashboardServiceImpl();
    }

    public static final Logger log = Logger.getLogger(DashboardController.class.getName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.loadSubjects();
        this.loadEmployeeCounts();
        this.setStatistics();


    }

    private void loadSubjects() {
        try {
            ArrayList<Dashboard2> list1 = this.dashboardService.getSubjects();
            ObservableList<XYChart.Series<String, Integer>> data1 = FXCollections.observableArrayList();
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            for (Dashboard2 d1 : list1
            ) {
                series.getData().add(new XYChart.Data(d1.getYearSem(), d1.getNoOfSubjects()));
            }
            data1.add(series);
            subjectChart.setData(data1);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    private void loadEmployeeCounts() {

        try {

            ArrayList<Dashboard> list = this.dashboardService.getEmployeeCount();
            ObservableList<XYChart.Series<String, Integer>> data = FXCollections.observableArrayList();
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            for (Dashboard d1 : list
            ) {
                series.getData().add(new XYChart.Data(d1.getFaculty(), d1.getNoOfEmployees()));
            }
            data.add(series);
            employeeChart.setData(data);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    private void setStatistics() {

        try {
            int countOfLectures = this.dashboardService.getCountOfLectures();
            noOfLecturesLbl.setText(String.valueOf(countOfLectures));
            int countOfBuildings = this.dashboardService.getCountOfBuildings();
            noOfBuildings.setText(String.valueOf(countOfBuildings));
            int countOfSubjects = this.dashboardService.getCountOfSubjects();
            noOfSubjects.setText(String.valueOf(countOfSubjects));
            int countOfRooms = this.dashboardService.getCountOfRooms();
            noOfRooms.setText(String.valueOf(countOfRooms));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("throwables = " + throwables.getMessage());
        }

    }
}
