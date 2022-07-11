/*
Name: Mor
 */
package com.example.cyberp;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.util.Callback;



public class LoggedInController implements Initializable {
    @FXML
    private TableView<ObservableList> tableview;
    private TableColumn App;
    private TableColumn password;
    private TableColumn user_name;
    @FXML
    private Button refresh;
    @FXML
    private Button button_add;
    @FXML
    private Button bonus;
    @FXML
    private Button button_logout;
    @FXML
    private Label lab_username;
    @FXML
    private Label lab_password;
    //TABLE VIEW AND DATA
    private ObservableList<ObservableList> data;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bonus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String url = "https://www.youtube.com/watch?v=DyDfgMOUjCI";
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
                } catch (java.io.IOException e) {
                    System.out.println(e.getMessage());
                }

            }
        });
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tableview.getColumns().clear();
                buildData();

            }
        });

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DBUti.changeScene(event, "MainPage.fxml", "login!", null, null, DBUti.user);
            }
        });

        button_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DBUti.changeScene(event, "add_data.fxml", "Add!", null, null, DBUti.user);
                System.out.println(DBUti.user.getPassword());
                System.out.println(DBUti.user.getUsername());
            }
        });
    }

    public void setUserInformation(String username, String password) {
        lab_username.setText("Welcome: " + username);
        lab_password.setText("Your password is: " + password);

    }
    public void buildData(){

        Connection c = null;
        data =FXCollections.observableArrayList();

        try {
            c = DBUti.getconnection();
        //SQL FOR SELECTING ALL OF CUSTOMER
        String SQL = "SELECT * from u_p";
        //ResultSet
        ResultSet rs = c.createStatement().executeQuery(SQL);

        /**
         * ********************************
         * TABLE COLUMN ADDED DYNAMICALLY *
         *********************************
         */

                for (int i =1; i < rs.getMetaData().getColumnCount()-1;  i++) {
                    //We are using non property style for making dynamic table
                    final int j = i;
                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                    col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });

                    tableview.getColumns().addAll(col);

                    System.out.println("Column [" + i + "] ");
                }

        /**
         * ******************************
         * Data added to ObservableList *
         *******************************
         */
        while (rs.next()) {
            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            if(rs.getString(5).equals(DBUti.td.encrypt(DBUti.user.getUsername()))){
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column

                    row.add(DBUti.td.decrypt(rs.getString(i)));

                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }
        }

        //FINALLY ADDED TO TableView
        tableview.setItems(data);
    } catch(
    Exception e)

    {
        e.printStackTrace();
        System.out.println("Error on Building Data");
    }

}
}
