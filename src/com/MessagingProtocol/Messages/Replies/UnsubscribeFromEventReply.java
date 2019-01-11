package com.MessagingProtocol.Messages.Replies;

import com.Constants;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class UnsubscribeFromEventReply implements IMessage {
    private MessageType messageType = MessageType.UnsubscribeFromEventReply_Message;
    private String fireBaseToken;
    private User sender;

    public UnsubscribeFromEventReply(String fireBaseToken, User sender) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
    }

    public static UnsubscribeFromEventReply fromJson(String json) {
        return Constants.GSON.fromJson(json, UnsubscribeFromEventReply.class);
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
