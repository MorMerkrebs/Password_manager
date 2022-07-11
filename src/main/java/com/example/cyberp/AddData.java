/*
Name: Mor
 */
package com.example.cyberp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;

public class AddData implements Initializable {

    @FXML
   private Label rand_pass;
    @FXML
    private Button save_button;
   @FXML
    private Button clean_button;
    @FXML
    private Button random_pass_button;

    @FXML

    private Button return_button;

    @FXML
    private TextField app_TF;
    @FXML
    private TextField username_TF;
    @FXML
    private PasswordField password_TF;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        return_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUti.changeScene(event,"LoggedIn.fxml","Password manager",DBUti.user.getUsername(),DBUti.user.getPassword(), DBUti.user);
            }
        });

        clean_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                password_TF.setText(null);
                app_TF.setText(null);
                username_TF.setText(null);

            }
        });
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&*^!]).{8,8}$";
        save_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(password_TF.getText().isEmpty() || app_TF.getText().isEmpty()||username_TF.getText().isEmpty()) {
                    System.out.println("You cannot save empty fields");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("You cannot save empty fields");
                    alert.show();
                }

                else if(!isValidPassword(password_TF.getText(), regex))
                {
                    Alert q = new Alert(Alert.AlertType.ERROR);
                    q.setContentText("Password must have at least one :\nuppercase character!,\nlowercase character!,\none number!,\n" +
                            "special character among '@#$%&*^!'!\n\n And make sure your password has 8 letters! ");
                    q.show();
                }
                else {
                    DBUti.AddDataTable(event,  app_TF.getText(),username_TF.getText(), password_TF.getText(), DBUti.user);
                    DBUti.changeScene(event, "LoggedIn.fxml", "Password manager", DBUti.user.getUsername(), DBUti.user.getPassword(), DBUti.user);
                }
            }
        });

        random_pass_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int length=8;
                String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
                String specialCharacters = "@#$%&*^!";
                String numbers = "1234567890";
                String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
                Random random = new Random();
                char[] password = new char[length];

                password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
                password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
                password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
                password[3] = numbers.charAt(random.nextInt(numbers.length()));

                for(int i = 4; i< length ; i++) {
                    password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
                }
                password_TF.setText(String.valueOf(password));
                rand_pass.setText(String.valueOf(password));
            }
        });
    }
    public static boolean isValidPassword(String password,String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
