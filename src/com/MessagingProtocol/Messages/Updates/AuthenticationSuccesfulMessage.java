package com.MessagingProtocol.Messages.Updates;

import com.Constants;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class AuthenticationSuccesfulMessage implements IMessage {
    private MessageType messageType = MessageType.AuthenticationSuccessful_Message;
    private String fireBaseToken;
    private User user;
    private User sender = null;

    public AuthenticationSuccesfulMessage(String fireBaseToken, User user) {
        this.fireBaseToken = fireBaseToken;
        this.user = user;
    }

    public static AuthenticationSuccesfulMessage fromJson(String json) {
        return Constants.GSON.fromJson(json, AuthenticationSuccesfulMessage.class);
    }

    @Override
    public User getSender() {
        return sender;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
