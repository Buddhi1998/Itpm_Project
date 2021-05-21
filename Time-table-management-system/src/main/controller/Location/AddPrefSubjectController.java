package main.controller.Location;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.*;
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

public class AddPrefSubjectController implements Initializable {

    @FXML
    private TextField txtSubTagOpt;

    @FXML
    private Button btnSubjectOptions;

    @FXML
    private TextField txtBuildingOpt;

    @FXML
    private TextField txtRoomOpt1;

    @FXML
    private ComboBox<String> cmbCenter;

    @FXML
    private TableView<PrefSubject> tblBuilding;

    @FXML
    private TableColumn<?, ?> removeBuilding;

    @FXML
    private TableColumn<?, ?> removeBuilding1;

    @FXML
    private TableColumn<?, ?> removeBuilding2;

    @FXML
    private Button btnBuildingAdd;

    @FXML
    private TextField txtTagOpt1;


    ArrayList<Building> buildingsId = new ArrayList<>();
    ArrayList<String> buildingName = new ArrayList<>();
    ArrayList<Room> roomsId = new ArrayList<>();
    ArrayList<String> roomName = new ArrayList<>();
    ArrayList<Tag> tagId = new ArrayList<>();
    ArrayList<String> tagName = new ArrayList<>();
    ArrayList<Subject> subjectId = new ArrayList<>();
    ArrayList<String> subjectName = new ArrayList<>();
    ArrayList<PrefSubject> prefSubject = new ArrayList<>();
    private AutoCompletionBinding<String> autoCompletionBinding;
    private AutoCompletionBinding<String> autoCompletionBinding2;
    private AutoCompletionBinding<String> autoCompletionBinding3;
    private AutoCompletionBinding<String> autoCompletionBinding4;

    public static final Logger log = Logger.getLogger(AddPrefSubjectController.class.getName());
    private PrefSubjectService prefSubjectService;
    private PrefTagService prefTagService;

    public AddPrefSubjectController() {
        this.prefSubjectService = new PrefSubjectServiceImpl();
        this.prefTagService = new PrefTagServiceImpl();
    }

    @FXML
    void getBuilding(ActionEvent event) {
        String center = cmbCenter.getValue();
        try {
            BuildingService buildingService = new BuildingServiceImpl();


            ArrayList<Building> list = buildingService.searchBuildingDetailsByUsingCenter(center);
            buildingsId = new ArrayList<>();
            buildingName = new ArrayList<>();

            for (Building building : list
            ) {
                buildingsId.add(building);
                buildingName.add(building.getBuilding());
            }

            if (autoCompletionBinding != null) {
                autoCompletionBinding.dispose();
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(txtBuildingOpt, buildingName);

        } catch (SQLException ex) {
            log.log(Level.SEVERE,ex.getMessage());
        }
    }

    void getTag() {
        try {
            TagService tagService = new TagServiceImpl();

            ArrayList<Tag> list = tagService.getAllDetails();
            tagId = new ArrayList<>();
            tagName = new ArrayList<>();

            for (Tag tag : list
            ) {
                tagId.add(tag);
                tagName.add(tag.getTagName());
            }

            if (autoCompletionBinding3 != null) {
                autoCompletionBinding3.dispose();
            }
            autoCompletionBinding3 = TextFields.bindAutoCompletion(txtTagOpt1, tagName);

        } catch (SQLException ex) {
            log.log(Level.SEVERE,ex.getMessage());
        }
    }

    @FXML
    void getRoom(ActionEvent event) {
        String building = txtRoomOpt1.getText();

        try {
            RoomService roomService = new RoomServiceImpl();

            ArrayList<Room> list = roomService.searchRoomDetailsByUsingbuilding(building);
            roomsId = new ArrayList<>();
            roomName = new ArrayList<>();

            for (Room room : list
            ) {
                roomsId.add(room);
                roomName.add(room.getRoom());

            }

            if (autoCompletionBinding2 != null) {
                autoCompletionBinding2.dispose();
            }
            autoCompletionBinding2 = TextFields.bindAutoCompletion(txtRoomOpt1, roomName);

        } catch (SQLException ex) {
            log.log(Level.SEVERE,ex.getMessage());
        }
    }

    void getSubject() {
        try {
            SubjectService tagService = new SubjectServiceImpl();

            ArrayList<Subject> list = tagService.getAllSubjectDetails();
            subjectId = new ArrayList<>();
            subjectName = new ArrayList<>();
            for (Subject subject : list
            ) {
                subjectId.add(subject);
                subjectName.add(subject.getSubName());
            }

            if (autoCompletionBinding4 != null) {
                autoCompletionBinding4.dispose();
            }
            autoCompletionBinding4 = TextFields.bindAutoCompletion(txtSubTagOpt, subjectName);

        } catch (SQLException ex) {
            log.log(Level.SEVERE,ex.getMessage());
        }
    }

    @FXML
    void AddSubjectToTable(ActionEvent event) {
        try {
            String center = (String) cmbCenter.getValue();
            String building = txtBuildingOpt.getText();
            String room = txtRoomOpt1.getText();
            String tag = txtTagOpt1.getText();
            String subject = txtSubTagOpt.getText();
            int roomId = 0;
            int tagId1 = 0;
            String subId = "";
            if (building != null) {
                if (center != null) {
                    if (room != null) {
                        roomId = prefTagService.getRoomId(center, building, room);

                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Please Select room!");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Please Select center!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }

            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Select building!");
                al.setHeaderText(null);
                al.showAndWait();
            }
            if (tag != null) {
                tagId1 = prefTagService.getTagIdFromTags(tag);
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Select Tag!");
                al.setHeaderText(null);
                al.showAndWait();
            }

            if (subject != null) {
                subId = prefSubjectService.getSubIdFromSubjects(subject);
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Select Subject!");
                al.setHeaderText(null);
                al.showAndWait();
            }
            PrefSubject prefSub = new PrefSubject();
            prefSub.setTagId(tagId1);
            prefSub.setRoomId(roomId);
            prefSub.setSubjectId(subId);

            boolean isAdded = false;

            if (tag != null) {
                if (center != null) {
                    if (building != null) {
                        if (room != null) {
                            prefSub.setBuidlingName(building);
                            prefSub.setCenterName(center);
                            prefSub.setRoomName(room);
                            prefSub.setSubName(subject);
                            prefSub.setTagName(tag);
                            boolean status=false;

                            if(prefSubject.isEmpty()){
                                prefSubject.add(prefSub);
                                tblBuilding.setItems(FXCollections.observableArrayList(prefSubject));
                            }else{

                                for(PrefSubject p :prefSubject){
                                    if(p.getRoomId()==roomId && p.getCenterName().equalsIgnoreCase(center)){
                                        status=true;
                                    }
                                }
                                if(!status){
                                    prefSubject.add(prefSub);
                                    tblBuilding.setItems(FXCollections.observableArrayList(prefSubject));
                                }else{
                                    Alert al = new Alert(Alert.AlertType.ERROR);
                                    al.setTitle(null);
                                    al.setContentText("Cant Add Same Room To Table");
                                    al.setHeaderText(null);
                                    al.showAndWait();
                                }
                            }



                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Please Select Room!");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Please Select Building!");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Please Select Center!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Select tag!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblBuilding.getSelectionModel().getTableView().getItems().clear();
        tblBuilding.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("tagName"));
        tblBuilding.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("subName"));
        tblBuilding.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("centerName"));
        tblBuilding.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("buidlingName"));
        tblBuilding.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("roomName"));
    }

    @FXML
    void saveTagRoom(ActionEvent event) throws SQLException {
        if(!prefSubject.isEmpty()){
            int count =0;
            for (PrefSubject pref:prefSubject
            ) {
                count++;
                PrefSubject prefSub = new PrefSubject();
                prefSub.setTagId(pref.getTagId());
                prefSub.setRoomId(pref.getRoomId());
                prefSub.setSubjectId(pref.getSubjectId());
                boolean isAdded = this.prefSubjectService.savePrefSubjectRoom(prefSub);

            }
            if (count==prefSubject.size()) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Added Successfully!!");
                al.setHeaderText(null);
                al.showAndWait();
                prefSubject.clear();
                tblBuilding.setItems(FXCollections.observableArrayList(prefSubject));
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Added Failed!!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        }else{
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Add Data To Table!");
            al.setHeaderText(null);
            al.showAndWait();
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.getSubject();
        this.getTag();
        this.setTableProperties();
    }
}
