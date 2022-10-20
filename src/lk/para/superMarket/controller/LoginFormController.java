package lk.para.superMarket.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginFormController {

    public JFXTextField txtUserName;
    public JFXComboBox<String> cmbSelectType;
    public JFXPasswordField pwdPassword;
    public AnchorPane MainAnchorePane;
    int attempts;

    public void initialize() {
        cmbSelectType.getItems().addAll("Admin", "Cashier");
    }



    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {

        attempts++;
        if (attempts <= 3) {
            if (txtUserName.getText().equals("sudheera") && pwdPassword.getText().equals("1234")) {
                new Alert(Alert.AlertType.CONFIRMATION, "Success!").showAndWait();
                Stage stage = (Stage) MainAnchorePane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml"))));

                stage.centerOnScreen();


            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }
        } else {
            txtUserName.setEditable(false);
            pwdPassword.setEditable(false);
        }
    }
}

