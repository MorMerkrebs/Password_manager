/*
Name: Mor
 */
package com.example.cyberp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class DBUti {
    static user user = new user(null, null);
    static TrippleDes td;

    static {
        try {
            td = new TrippleDes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Connection getconnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("path", "name", "pass");//sqlworkbench path
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static void changeScene(ActionEvent event,String fxmlFile,String title,String username,String password,user u){
        Parent root=null;
    
        if (username!= null && password!=null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUti.class.getResource(fxmlFile));
                root= loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username,password);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try{
                root=FXMLLoader.load(DBUti.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        //assert root != null;
        stage.setScene(new Scene(root,600,400));
        stage.show();

    }

    public static void singupUser(ActionEvent event,String username,String password){
        Connection connection = null;
        PreparedStatement psInster = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet resultSet = null;

        try{
            connection = getconnection();
            psCheckUserExist = connection.prepareStatement("SELECT * FROM users WHERE uname =?");
            psCheckUserExist.setString(1,td.encrypt(username));
            resultSet = psCheckUserExist.executeQuery();

            if ( resultSet.isBeforeFirst()){
                System.out.println("User already exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User already exist");
                alert.show();
            }
            else{
                psInster = connection.prepareStatement("INSERT INTO users (uname,password) VALUES (?, ?)");
                psInster.setString(1,td.encrypt(username));
                psInster.setString(2,td.encrypt(password));
                psInster.executeUpdate();

                changeScene(event,"LoggedIn.fxml","Welcome!",username,password,user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(resultSet!=null){
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(psCheckUserExist!=null){
                try {
                    psCheckUserExist.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(psInster!=null){
                try {
                    psInster.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void loginUser(ActionEvent event, String username , String password){

        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet= null;
        try{
            connection = getconnection();
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE uname=?");
            preparedStatement.setString(1, td.encrypt(username));
            resultSet =preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            }
            else{
                while(resultSet.next()){
                    String retrivedPassword = td.decrypt(resultSet.getString("password"));

                    if(retrivedPassword.equals(password)){
                        user.setUsername(username);
                        user.setPassword(password);
                        changeScene(event,"LoggedIn.fxml","Welcome",username,retrivedPassword,user);
                    }
                    else{
                        System.out.println("Password did not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The Provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(resultSet !=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(preparedStatement !=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection !=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void AddDataTable(ActionEvent event, String app,String username , String password,user u) {

        String encrypted_app = td.encrypt(app);
        String encrypted_username = td.encrypt(username);
        String encrypted_password = td.encrypt(password);
        String encrypted_ID= td.encrypt(u.getUsername());
        Connection connection = null;
        PreparedStatement psInster = null;
        try {
            connection = getconnection();
            psInster = connection.prepareStatement("INSERT INTO u_p (site,user_name,password,ID) VALUES (?, ?,?,?)");
            psInster.setString(1,td.encrypt(app));
            psInster.setString(2,td.encrypt(username));
            psInster.setString(3,td.encrypt(password));
            psInster.setString(4,td.encrypt(u.getUsername()));
            psInster.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

