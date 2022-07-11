/*
Name: Mor
 */
package com.example.cyberp;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        user user = new user(null,null);
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        stage.setTitle("login");
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }

    public static void main(String[] args){launch(args);}
}