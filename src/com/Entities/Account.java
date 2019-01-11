package com.Entities;

import java.util.ArrayList;

public class Account {
    /**
     *
     */
    private User user;
    /**
     *
     */
    private ArrayList<User> friends;
    /**
     *
     */
    private String fireBaseMessagingId;

    /**
     * @param user
     */
    public Account(User user) {
        this.user = user;
        friends = new ArrayList<>();
    }

    /**
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * @return
     */
    public ArrayList<User> getFriends() {
        return friends;
    }

    /**
     * @param friends
     */
    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    /**
     * @return
     */
    public String getFireBaseMessagingId() {
        return fireBaseMessagingId;
    }

    /**
     * @param fireBaseMessagingId
     */
    public void setFireBaseMessagingId(String fireBaseMessagingId) {
        this.fireBaseMessagingId = fireBaseMessagingId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "user=" + user +
                ", friends=" + friends +
                ", fireBaseMessagingId='" + fireBaseMessagingId + '\'' +
                '}';
    }
}
