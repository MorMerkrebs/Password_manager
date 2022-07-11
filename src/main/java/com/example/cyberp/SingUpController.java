/*
Name: Mor
 */
package com.example.cyberp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SingUpController implements Initializable {

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @FXML
    private PasswordField tf_confirm_password;

    @FXML
    private Button button_register;
    @FXML
    private Button button_back;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

     button_register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!tf_confirm_password.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_username.getText().trim().isEmpty()){
                    if(tf_confirm_password.getText().equals(tf_password.getText())) {
                        if (tf_password.getLength()!=8) {
                            System.out.println("Make sure your password has  8 letters");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Make sure your password has  8 letters");
                            alert.show();
                        }
                        else
                            DBUti.singupUser(event, tf_username.getText(), tf_password.getText());
                    }
                    else{
                            System.out.println("Password not match");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Password not match");
                            alert.show();
                        }
                    }
                else {
                    System.out.println("Please fill all the info");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill all the info to Register");
                    alert.show();

                    }
                }
        });


        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUti.changeScene(event,"MainPage.fxml","login",null,null,null);
            }
        });


    }
}
