package com.example.mahmoudshaeer.chatus.models;

/**
 * Created by mahmoud Shaeer on 5/8/2017.
 */

public class UserIfo {
    String Name;
    String Password;

    public UserIfo() {
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public UserIfo(String name, String password) {
        Name = name;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }
}
