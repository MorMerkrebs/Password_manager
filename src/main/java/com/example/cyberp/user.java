/*
Name: Mor
 */
package com.example.cyberp;

import java.util.List;

public class user  {
    private String username;
    private String password;
    private List<String>list=null ;
    public static boolean flag=false;
    public static int counter=0;
    public user(String u , String p){
        this.username=u;
        this.password=p;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }

    public void setUsername(String u){
        this.username=u;
    }

    public void setPassword(String p){
        this.password=p;
    }

}
