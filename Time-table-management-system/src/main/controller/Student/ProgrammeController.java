package main.controller.Student;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.Programme;
import main.model.YearAndSemester;
import main.service.ProgrammeService;
import main.service.impl.ProgrammeServiceImpl;

public class ProgrammeController implements Initializable {

    @FXML
    private Label lblYearSemName;

    @FXML
    private TableView<Programme> tblProgramme;

    @FXML
    private TableColumn<Programme, Boolean> coloUpdate;

    @FXML
    private TableColumn<Programme,Boolean > colDelete;

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtProgramme;

    private ProgrammeService programmeService;
    private boolean updateStatus = false;
    private int programmeId;
    public static final Logger log = Logger.getLogger(ProgrammeController.class.getName());

    public ProgrammeController() {
        programmeService = new ProgrammeServiceImpl();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        this.getAllDetails();
    }


    public void saveDetails(ActionEvent actionEvent) {
        String programmeName = txtProgramme.getText();
        Programme p = new Programme();
        p.setProgrammeName(programmeName);
        boolean status = false;
        boolean isUpdated =false;
        if (programmeName != null) {
            try {
                status = this.programmeService.searchProgramme(programmeName);
                if (!status) {
                    if(!updateStatus){
                        boolean isAdded = this.programmeService.saveProgramme(p);
                        if (isAdded) {
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle(null);
                            al.setContentText("Added Successfully ");
                            al.setHeaderText(null);
                            al.showAndWait();
                            txtProgramme.setText(null);
                            this.getAllDetails();
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Added Fail");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    }else{
                        p.setProgrammeId(programmeId);
                        isUpdated = this.programmeService.updateProgramme(p);
                        if (isUpdated) {
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle(null);
                            al.setContentText("Updated Successfully. ");
                            al.setHeaderText(null);
                            al.showAndWait();
                            this.getAllDetails();
                            this.updateStatus=false;
                            txtProgramme.setText(null);
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Updated Fail. ");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    }


                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Programme Name Is Exsiting In This System.You Cant Add this!!!!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE,e.getMessage());
            }

        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Enter Value For Programme Field");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }

    public void getAllDetails() {
        try {
            ArrayList<Programme> list = this.programmeService.getAllDetails();
            tblProgramme.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblProgramme.getSelectionModel().getTableView().getItems().clear();
        tblProgramme.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("programmeId"));
        tblProgramme.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("programmeName"));
        coloUpdate.setCellFactory(cellFactoryBtnEdit);
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<Programme, Boolean>, TableCell<Programme, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<Programme, Boolean>, TableCell<Programme, Boolean>>() {
                @Override
                public TableCell<Programme, Boolean> call(TableColumn<Programme, Boolean> param) {
                    final TableCell<Programme, Boolean> cell = new TableCell<Programme, Boolean>() {
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
                                    Programme programme = getTableView().getItems().get(getIndex());
                                    setProgrammeNameToFiled(programme);
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }
                        private void setProgrammeNameToFiled(Programme programme) {
                            txtProgramme.setText(programme.getProgrammeName());
                            updateStatus = true;
                            programmeId = programme.getProgrammeId();
                        }
                    };
                    return cell;
                }
            };

    Callback<TableColumn<Programme, Boolean>, TableCell<Programme, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<Programme, Boolean>, TableCell<Programme, Boolean>>() {
                @Override
                public TableCell<Programme, Boolean> call(TableColumn<Programme, Boolean> param) {
                    final TableCell<Programme, Boolean> cell = new TableCell<Programme, Boolean>() {
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
                                    Programme programme = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteProgramme(programme.getProgrammeId());
                                    } else {

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

    private void deleteProgramme(int id) {
        try {
            boolean staus = programmeService.deleteProgramme(id);
            if (staus) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Programme Deleted SuccessFully ");
                al.setHeaderText(null);
                al.showAndWait();
                getAllDetails();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Programme Deleted Fail ");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}
