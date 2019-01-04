package com.Entities;

public class Event {
    private Location location;
    private String eventName;
    private String eventUID;
    private String expirationDateAsString;
    private User eventCreator;

    public Event(Location location, String eventName, String eventUID, String expirationDateAsString, User eventCreator) {
        this.location = location;
        this.eventName = eventName;
        this.eventUID = eventUID;
        this.expirationDateAsString = expirationDateAsString;
        this.eventCreator = eventCreator;
    }

    public Location getLocation() {
        return location;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventUID() {
        return eventUID;
    }

    public String getExpirationDateAsString() {
        return expirationDateAsString;
    }

    public User getEventCreator() {
        return eventCreator;
    }
}
