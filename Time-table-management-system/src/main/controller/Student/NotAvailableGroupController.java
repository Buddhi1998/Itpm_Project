package main.controller.Student;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.MainGroup;
import main.model.NotAvailableGroup;
import main.model.SubGroup;
import main.service.MainGroupService;
import main.service.SubGroupService;
import main.service.impl.MainGroupServiceImpl;
import main.service.impl.SubGroupServiceImpl;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class NotAvailableGroupController implements Initializable {

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<Object> cmbDate;

    @FXML
    private TableView<NotAvailableGroup> tblNotAvailable;

    @FXML
    private TableColumn<NotAvailableGroup, Boolean> colDelete;

    @FXML
    private RadioButton btnRadioMain;

    @FXML
    private RadioButton btnRadioSub;

    @FXML
    private JFXTimePicker toTime;

    @FXML
    private JFXTimePicker fromTime;

    @FXML
    private TextField txtGroupId;


    private MainGroupService mainGroupservice;
    private SubGroupService subGroupService;
    private List<String> groupNameList;
    private List<Object> groupList;
    private AutoCompletionBinding<String> autoCompletionBinding;
    MainGroupService groupService;
    private String curTime;
    public static final Logger log = Logger.getLogger(NotAvailableGroupController.class.getName());

    @FXML
    void loadGroupDetails() {
        if (btnRadioMain.isSelected()) {
            loadMainGroupDetails();
        } else if (btnRadioSub.isSelected()) {
            loadSubGroupDetails();
        }
    }

    private void loadSubGroupDetails() {
        try {
            ArrayList<SubGroup> subList = this.subGroupService.getAllSubGroupDetails(0);
            groupNameList.clear();
            groupList.clear();
            if (autoCompletionBinding != null) {
                autoCompletionBinding.dispose();
            }
            for (SubGroup s : subList
            ) {
                groupNameList.add(s.getSubgroupid());
                groupList.add(s);
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(txtGroupId, groupNameList);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @FXML
    void saveDetails(ActionEvent event) {
        String day = (String) cmbDate.getValue();
        String getToTime = toTime.getValue().toString();
        String getFromTime = fromTime.getValue().toString();

        String groupId = txtGroupId.getText();
        NotAvailableGroup nag = new NotAvailableGroup();
        String selectedBtn = "";
        if(btnRadioMain.isSelected()){
            selectedBtn ="MainGroupId";
        }
        if(btnRadioSub.isSelected()){
            selectedBtn="SubGroupId";
        }

        if (!day.isEmpty()) {
            if (!getToTime.isEmpty()) {
                if (!getFromTime.isEmpty()) {
                    if (!groupId.isEmpty()) {
                        int count = 0;
                            for (Object m : this.groupList
                            ) {
                                if (m instanceof MainGroup && groupId.equals(((MainGroup) m).getGroupid())) {
                                        nag.setMainGroupId(((MainGroup) m).getId());
                                        nag.setSubGroupId(0);
                                        count++;


                                }
                                if (m instanceof SubGroup && groupId.equals(((SubGroup) m).getSubgroupid())) {
                                        nag.setSubGroupId(((SubGroup) m).getId());
                                        nag.setMainGroupId(0);
                                        count++;

                                }
                            }
                            if (count != 0) {
                                nag.setDay(day);
                                nag.setFromTime(getFromTime);
                                nag.setToTime(getToTime);
                                nag.setGroupId(groupId);
                                boolean status = false;
                                try {
                                    status = this.mainGroupservice.addNotAvailableGroup(nag);
                                } catch (SQLException|ParseException e) {
                                    log.log(Level.SEVERE,e.getMessage());
                                }

                                if(status){
                                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                                    al.setTitle(null);
                                    al.setContentText("Added Successfully ");
                                    al.setHeaderText(null);
                                    al.showAndWait();
                                    cmbDate.setValue("");
                                    toTime.setValue(LocalTime.parse(curTime));
                                    fromTime.setValue(LocalTime.parse(curTime));
                                    txtGroupId.setText("");
                                    btnRadioMain.setSelected(true);
                                    this.getAllNotAvailableGroupDetails("");
                                    count=0;
                                }else{
                                    Alert al = new Alert(Alert.AlertType.ERROR);
                                    al.setTitle(null);
                                    al.setContentText("   Added Fail");
                                    al.setHeaderText(null);
                                    al.showAndWait();
                                }
                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText(selectedBtn +" Is not Exists In this System");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }

                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("GroupId Field is Empty.Please Select Group ID!!!");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("FromTime Field is Empty.Please Select From Time!!!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("ToTime Field is Empty.Please Select To Time!!!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Day Field is Empty.Please Select Day!!!");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }


    private void loadMainGroupDetails() {
        try {
            ArrayList<MainGroup> mainList = this.mainGroupservice.getAllMainGroupDetails();
            groupNameList.clear();
            groupList.clear();
            if (autoCompletionBinding != null) {
                autoCompletionBinding.dispose();
            }
            for (MainGroup m : mainList
            ) {
                groupNameList.add(m.getGroupid());
                groupList.add(m);
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(txtGroupId, groupNameList);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainGroupservice = new MainGroupServiceImpl();
        groupNameList = new ArrayList<>();
        groupList = new ArrayList<>();
        subGroupService = new SubGroupServiceImpl();
        btnRadioMain.setSelected(true);
        loadGroupDetails();
        groupService = new MainGroupServiceImpl();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        curTime = dtf.format(now);
        toTime.setValue(LocalTime.parse(curTime));
        fromTime.setValue(LocalTime.parse(curTime));
        this.setTableProperties();
        this.getAllNotAvailableGroupDetails("");
    }

    public void getAllNotAvailableGroupDetails(String groupId){
        try {
            ArrayList<NotAvailableGroup> nvg = this.mainGroupservice.getAllNotAvailableGroupDetails(groupId);
            tblNotAvailable.setItems(FXCollections.observableArrayList(nvg));
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblNotAvailable.getSelectionModel().getTableView().getItems().clear();
        tblNotAvailable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("groupId"));
        tblNotAvailable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("day"));
        tblNotAvailable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("fromTime"));
        tblNotAvailable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("toTime"));
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<NotAvailableGroup, Boolean>, TableCell<NotAvailableGroup, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<NotAvailableGroup, Boolean>, TableCell<NotAvailableGroup, Boolean>>() {
                @Override
                public TableCell<NotAvailableGroup, Boolean> call(TableColumn<NotAvailableGroup, Boolean> param) {
                    final TableCell<NotAvailableGroup, Boolean> cell = new TableCell<NotAvailableGroup, Boolean>() {
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
                                    NotAvailableGroup dept = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteNotAvailableGroup(dept.getId());
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

    public void deleteNotAvailableGroup(int id){
        boolean isDeleted = false;
        try {
            isDeleted = this.mainGroupservice.deleteNotAvailableGroupId(id);
            if (isDeleted) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText(" Deleted SuccessFully");
                al.setHeaderText(null);
                al.showAndWait();
                this.getAllNotAvailableGroupDetails("");
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText(" Deleted Fail");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}
