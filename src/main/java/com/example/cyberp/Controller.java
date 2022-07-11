/*
Name: Mor
 */
package com.example.cyberp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button button_login;
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField tf_password;
    @FXML
    private Button button_singup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       button_login.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               DBUti.loginUser(event,tf_username.getText(),tf_password.getText());
           }
       });

       button_singup.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               DBUti.changeScene(event,"Singup.fxml","Sing Up",null,null,null);
           }
       });

    }
}
