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
     *
     */
    private ArrayList<String> subscribedEvents;

    /**
     * @param user
     */
    public Account(User user) {
        this.user = user;
        friends = new ArrayList<>();
        subscribedEvents = new ArrayList<>();
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

    public ArrayList<String> getSubscribedEvents() {
        return subscribedEvents;
    }

    public void setSubscribedEvents(ArrayList<String> subscribedEvents) {
        this.subscribedEvents = subscribedEvents;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "user=" + user +
                ", friends=" + friends +
                ", fireBaseMessagingId='" + fireBaseMessagingId + '\'' +
                ", subscribedEvents=" + subscribedEvents +
                '}';
    }
}
