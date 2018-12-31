package com.Entities;

import java.util.ArrayList;

public class Account {
    private User user;
    private ArrayList<User> friends;

    public Account(User user) {
        this.user = user;
        friends = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }
}
