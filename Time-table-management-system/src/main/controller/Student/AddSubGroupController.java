package main.controller.Student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.model.MainGroup;
import main.model.SubGroup;
import main.service.MainGroupService;
import main.service.SubGroupService;
import main.service.impl.MainGroupServiceImpl;
import main.service.impl.SubGroupServiceImpl;
import org.controlsfx.control.textfield.TextFields;


import java.net.URL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddSubGroupController implements Initializable {

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtMainGroupId;

    @FXML
    private TextField txtGroupCount;
    private MainGroupService mainGroupService;
    private List<String> mainGroupNameList;
    private List<MainGroup> mainGroupList;
    private SubGroupService subGroupService;
    public static final Logger log = Logger.getLogger(AddSubGroupController.class.getName());

    public AddSubGroupController() {
        mainGroupService = new MainGroupServiceImpl();
        mainGroupNameList = new ArrayList<>();
        mainGroupList = new ArrayList<>();
        subGroupService = new SubGroupServiceImpl();
    }

    @FXML
    void saveDetails(ActionEvent event) {

        try {
            String mainGroupId = txtMainGroupId.getText();
            int mainid = 0;
            int mainCount = 0;
            int mainGroupCount = 0;
            if (txtGroupCount.getText().length() != 0) {
                mainGroupCount = Integer.parseInt(txtGroupCount.getText());
            }
            for (MainGroup m : this.mainGroupList
            ) {
                if (mainGroupId.equals(m.getGroupid())) {
                    mainid = m.getId();
                    mainCount++;
                }
            }
            if (mainGroupId != null) {
                if (mainGroupCount != 0) {
                    if (mainCount != 0) {
                        int count = this.subGroupService.subGroupCountAccordingToId(mainid);
                        boolean isAdded = false;
                        for (int i = 0; i < mainGroupCount; i++) {
                            SubGroup sub = new SubGroup();
                            String generateId = null;
                            if (count < 9) {
                                generateId = mainGroupId + ".0" + (count + 1);
                            } else {
                                generateId = mainGroupId + "." + (count + 1);
                            }
                            sub.setMaingroupid(mainid);
                            sub.setSubgroupid(generateId);
                            sub.setSubgroupnumber(1 + count);
                            isAdded = this.subGroupService.saveDetails(sub);
                            count++;
                        }
                        if (isAdded) {
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle(null);
                            al.setContentText("Added Successfully ");
                            al.setHeaderText(null);
                            al.showAndWait();
                            txtMainGroupId.setText(null);
                            txtGroupCount.setText(null);
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Added Fail   ");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("MainGroup Is not Exists In this System");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("GroupCount Field is Empty");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("MainGroupId Field Is Empty");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        } catch (NumberFormatException e){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Enter Numeric Value For This Field");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadAllMainGroups();
    }

    public void loadAllMainGroups() {
        try {
            ArrayList<MainGroup> mainList = this.mainGroupService.getAllMainGroupDetails();
            for (MainGroup m : mainList
            ) {
                mainGroupNameList.add(m.getGroupid());
                mainGroupList.add(m);
            }
            TextFields.bindAutoCompletion(txtMainGroupId, mainGroupNameList);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }


}
