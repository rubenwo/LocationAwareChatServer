package com.MessagingProtocol.Messages.Requests;

import com.Constants;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class SyncMissedMessagesRequest implements IMessage {
    private MessageType messageType = MessageType.SyncMissedMessagesRequest_Message;
    private String fireBaseToken;
    private User sender;

    public SyncMissedMessagesRequest(String fireBaseToken, User sender) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
    }

    public static SyncMissedMessagesRequest fromJson(String json) {
        return Constants.GSON.fromJson(json, SyncMissedMessagesRequest.class);
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
