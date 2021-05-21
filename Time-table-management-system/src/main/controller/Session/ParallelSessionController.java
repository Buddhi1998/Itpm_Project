package main.controller.Session;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import main.model.ParallelSession;
import main.service.TimeTableGenerateService;
import main.service.impl.TimeTableGenerateServiceImpl;

public class ParallelSessionController implements Initializable {

    private TimeTableGenerateService timeTableGenerateService;
    private ArrayList<ParallelSession> parallelSessions = new ArrayList<>();
    public static final Logger log = Logger.getLogger(ParallelSessionController.class.getName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeTableGenerateService = new TimeTableGenerateServiceImpl();
        setTablePropertiesForParallelSesionTable();
        this.loadParallelSessions("");
        this.setTablePropertiesForParallelSesionTableSave();
    }

    @FXML
    private TableView<ParallelSession> tblParallelSession;

    @FXML
    private TableColumn<ParallelSession, Boolean> colEdit;

    @FXML
    private TextField txtLecturer;

    @FXML
    private TableView<ParallelSession> tblParallelSessionSave;

    @FXML
    private TableColumn<ParallelSession, Boolean> colDelete;

    @FXML
    private Button btnSave;

    @FXML
    void saveDetails(ActionEvent event) {
        int count = 0;
        String orderID = getLastID();
        if(!parallelSessions.isEmpty()){
            for (ParallelSession p : parallelSessions
            ) {
                count++;
                try {
                    boolean status =  this.timeTableGenerateService.addParallelSessions(p,orderID);
                } catch (SQLException e) {
                    log.log(Level.SEVERE,e.getMessage());
                }
            }
            if(count==parallelSessions.size()){
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Added Successfully");
                al.setHeaderText(null);
                al.showAndWait();
                parallelSessions.clear();
                tblParallelSessionSave.setItems(FXCollections.observableArrayList(parallelSessions));

            }
        }else{
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle(null);
            al.setContentText("Please Add Sessions to the table");
            al.setHeaderText(null);
            al.showAndWait();
        }


    }

    public String getLastID() {
        String lastId = null;
        String newID = "";
        try {
            lastId = timeTableGenerateService.getResult();

            if (!lastId.isEmpty()) {
                String subid = lastId.substring(4);
                int id = Integer.parseInt(subid);
                id++;
                NumberFormat numberFormat = NumberFormat.getIntegerInstance();
                numberFormat.setMinimumIntegerDigits(4);
                numberFormat.setGroupingUsed(false);
                newID = "S" + numberFormat.format(id);

            } else {
                newID = "S" + "0001";
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }

        return newID;
    }

    @FXML
    void searchDetails(ActionEvent event) {
        //Method search details
    }

    public void loadParallelSessions(String id) {
        try {
            ArrayList<ParallelSession> parallelSessions1 = this.timeTableGenerateService.getParalleSessions(id);
            ArrayList<ParallelSession> ps = new ArrayList<>();
            for (ParallelSession p1 : parallelSessions1
            ) {
                ParallelSession p2 = p1;
                if (p1.getSubgroupid() != null) {
                    String subgroupId = this.timeTableGenerateService.getSubGroupId(Integer.parseInt(p2.getSubgroupid().trim()));
                    p2.setSubgroupid(subgroupId);
                    ps.add(p2);
                } else {
                    p2.setSubgroupid("-");
                    ps.add(p2);
                }
            }
            tblParallelSession.setItems(FXCollections.observableArrayList(ps));
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTablePropertiesForParallelSesionTable() {
        tblParallelSession.getSelectionModel().getTableView().getItems().clear();
        tblParallelSession.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("groupId"));
        tblParallelSession.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("subgroupid"));
        tblParallelSession.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        tblParallelSession.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("tagName"));
        tblParallelSession.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("category"));
        colEdit.setCellFactory(cellFactoryBtnEdit);
    }

    public void setTablePropertiesForParallelSesionTableSave() {
        tblParallelSessionSave.getSelectionModel().getTableView().getItems().clear();
        tblParallelSessionSave.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("groupId"));
        tblParallelSessionSave.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        tblParallelSessionSave.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("tagName"));
    }

    Callback<TableColumn<ParallelSession, Boolean>, TableCell<ParallelSession, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<ParallelSession, Boolean>, TableCell<ParallelSession, Boolean>>() {
                @Override
                public TableCell<ParallelSession, Boolean> call(TableColumn<ParallelSession, Boolean> param) {
                    final TableCell<ParallelSession, Boolean> cell = new TableCell<ParallelSession, Boolean>() {
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
                                    ParallelSession cs = getTableView().getItems().get(getIndex());
                                    addParallelSession(cs);
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

    public void addParallelSession(ParallelSession ps) {
        if (parallelSessions.isEmpty()) {
            parallelSessions.add(ps);
        } else {

            boolean result = false;

            for (ParallelSession p1 : parallelSessions
            ) {
                if (p1.getCategory().equalsIgnoreCase(ps.getCategory()) && p1.getTagName().equalsIgnoreCase(ps.getTagName())) {
                    result = true;
                }
            }
            if (result) {
                parallelSessions.add(ps);
                result = false;
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Add Same Category Or Same Tag To the Table");
                al.setHeaderText(null);
                al.showAndWait();
            }

        }
        tblParallelSessionSave.setItems(FXCollections.observableArrayList(parallelSessions));
    }
}
