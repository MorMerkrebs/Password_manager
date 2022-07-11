/*
Name: Mor
 */
package com.example.cyberp;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.time.LocalDate;

public class webAndpass {
    private SimpleStringProperty web, pass,username;

    public webAndpass(String web, String pass, String username) {
        this.web = new SimpleStringProperty(web);
        this.pass = new SimpleStringProperty(pass);
        this.username = new SimpleStringProperty(username);
    }

    public String getWeb() {
        return web.get();
    }
   public void setWeb(String w){
        this.web=new SimpleStringProperty(w);
    }

    public String getPass(){
        return pass.get();
    }

    public void setPass(String p){
        this.pass = new SimpleStringProperty(p);
    }

    public String getUsername(){
        return username.get();
    }

    public void setUsername(String u){
        this.username=new SimpleStringProperty(u);
    }
}
