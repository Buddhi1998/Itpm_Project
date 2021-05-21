package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private AnchorPane mainPnl;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSignin;

    @FXML
    private Label btnForgot;

    @FXML
    private Label lblErrors;

    private double x = 0;
    private double y = 0;
    private Stage stage;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.dragabled();

    }

    private void dragabled() {
        mainPnl.setOnMousePressed((event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        }));
        mainPnl.setOnMouseDragged((event -> {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }


}
