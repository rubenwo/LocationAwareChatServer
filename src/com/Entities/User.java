package com.Entities;

import java.util.ArrayList;
import java.util.Date;

public class User {
    //TODO: Add useful identification variables to User class.
    private String name;
    private String email;
    private String uid;

    public User(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}
