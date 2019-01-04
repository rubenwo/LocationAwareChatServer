package com.MessagingProtocol.Messages.Requests;


import com.Constants;
import com.Entities.Location;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class EventCreationRequest implements IMessage {
    private MessageType messageType = MessageType.EventCreationRequest_Message;
    private String fireBaseToken;
    private User sender;
    private Location location;
    private String eventName;

    public EventCreationRequest(String fireBaseToken, User sender, Location location, String eventName) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
        this.location = location;
        this.eventName = eventName;
    }

    public static EventCreationRequest fromJson(String json) {
        return Constants.GSON.fromJson(json, EventCreationRequest.class);
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


    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
