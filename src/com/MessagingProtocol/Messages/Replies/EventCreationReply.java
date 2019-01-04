package com.MessagingProtocol.Messages.Replies;


import com.Constants;
import com.Entities.Location;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class EventCreationReply implements IMessage {
    private MessageType messageType = MessageType.EventCreationReply_Message;
    private String fireBaseToken;
    private User sender;
    private Location location;
    private String eventName;
    private String eventUID;
    private String expirationDateAsString;

    public EventCreationReply(String fireBaseToken, User sender, Location location, String eventName, String eventUID, String expirationDateAsString) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
        this.location = location;
        this.eventName = eventName;
        this.eventUID = eventUID;
        this.expirationDateAsString = expirationDateAsString;
    }

    public static EventCreationReply fromJson(String json) {
        return Constants.GSON.fromJson(json, EventCreationReply.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    @Override
    public User getSender() {
        return sender;
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

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
