package com.MessagingProtocol.Messages.Requests;

import com.Constants;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class GetAllEventsRequest implements IMessage {
    private MessageType messageType = MessageType.GetAllEventsRequest_Message;
    private String fireBaseToken;
    private User sender;

    public GetAllEventsRequest(String fireBaseToken, User sender) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
    }

    public static GetAllEventsRequest fromJson(String json) {
        return Constants.GSON.fromJson(json, GetAllEventsRequest.class);
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

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
