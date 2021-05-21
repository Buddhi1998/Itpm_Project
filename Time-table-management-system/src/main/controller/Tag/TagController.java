package main.controller.Tag;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.Tag;
import main.service.TagService;
import main.service.impl.TagServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TagController implements Initializable {

    @FXML
    private Label lblYearSemName;

    @FXML
    private TableView<Tag> tblTag;

    @FXML
    private TableColumn<Tag, Boolean> colEdit;

    @FXML
    private TableColumn<Tag, Boolean> colDelete;

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtTagName;

    private TagService tagService;
    private boolean updateStatus=false;
    private int tagId;
    public static final Logger log = Logger.getLogger(TagController.class.getName());

    public TagController() {
        tagService = new TagServiceImpl();
    }

    @FXML
    void saveDetails(ActionEvent event) {
        String tagName = txtTagName.getText();
        Tag tag = new Tag();
        tag.setTagName(tagName);
        boolean status = false;
        boolean isAdded = false;
        if (tagName!=null) {
            try {
                status = this.tagService.searchTag(tagName);
                if (!status) {
                    if(!updateStatus){
                        isAdded = this.tagService.saveTag(tag);
                        if (isAdded) {
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle(null);
                            al.setContentText(" Added Successfully ");
                            al.setHeaderText(null);
                            al.showAndWait();
                            txtTagName.setText(null);
                            this.getAllDetails();
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText(" Added Fail");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    }else{
                        tag.setTagId(tagId);
                        boolean isUpdated = this.tagService.updateTag(tag);
                        if(isUpdated){
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle(null);
                            al.setContentText("Updated Successfully ");
                            al.setHeaderText(null);
                            al.showAndWait();
                            txtTagName.setText(null);
                            this.getAllDetails();
                            updateStatus=false;
                        }else{
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Updated Fail");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    }

                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Tag Name Is Exsiting In This System.You Cant Add this!!!!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }

            } catch (SQLException e) {
                log.log(Level.SEVERE,e.getMessage());
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Enter Value For TagName Field");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }

    public void getAllDetails() {
        try {
            ArrayList<Tag> tagList = this.tagService.getAllDetails();
            tblTag.setItems(FXCollections.observableArrayList(tagList));

        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblTag.getSelectionModel().getTableView().getItems().clear();
        tblTag.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("tagId"));
        tblTag.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("tagName"));
        colEdit.setCellFactory(cellFactoryBtnEdit);
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<Tag, Boolean>, TableCell<Tag, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<Tag, Boolean>, TableCell<Tag, Boolean>>() {
                @Override
                public TableCell<Tag, Boolean> call(TableColumn<Tag, Boolean> param) {
                    final TableCell<Tag, Boolean> cell = new TableCell<Tag, Boolean>() {
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
                                    Tag tag = getTableView().getItems().get(getIndex());
                                    setTagNameToFiled(tag);
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }
                        private void setTagNameToFiled(Tag tag) {
                            txtTagName.setText(tag.getTagName());
                            updateStatus = true;
                            tagId = tag.getTagId();
                        }
                    };
                    return cell;
                }
            };

    Callback<TableColumn<Tag, Boolean>, TableCell<Tag, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<Tag, Boolean>, TableCell<Tag, Boolean>>() {
                @Override
                public TableCell<Tag, Boolean> call(TableColumn<Tag, Boolean> param) {
                    final TableCell<Tag, Boolean> cell = new TableCell<Tag, Boolean>() {
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
                                    Tag tag = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteTag(tag.getTagId());
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        this.getAllDetails();
    }

    private void deleteTag(int id) {
        try {
            boolean staus = tagService.deleteTag(id);
            if (staus) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Tag Deleted SuccessFully ");
                al.setHeaderText(null);
                al.showAndWait();
                getAllDetails();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Tag Deleted Fail ");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}
