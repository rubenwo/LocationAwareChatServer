package com.MessagingProtocol.Messages.Replies;

import com.Constants;
import com.Entities.Event;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

import java.util.ArrayList;

public class GetAllEventsReply implements IMessage {
    private MessageType messageType = MessageType.GetAllEventsReply_Message;
    private String fireBaseToken;
    private User sender;
    private ArrayList<Event> events;

    public GetAllEventsReply(String fireBaseToken, User sender, ArrayList<Event> events) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
        this.events = events;
    }

    public static GetAllEventsReply fromJson(String json) {
        return Constants.GSON.fromJson(json, GetAllEventsReply.class);
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

    public ArrayList<Event> getEvents() {
        return events;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
