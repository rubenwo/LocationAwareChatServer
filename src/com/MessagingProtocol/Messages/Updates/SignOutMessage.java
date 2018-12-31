package com.MessagingProtocol.Messages.Updates;


import com.Constants;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class SignOutMessage implements IMessage {
    private MessageType messageType = MessageType.SignOut_Message;
    private String fireBaseToken;
    private boolean signOut;
    private User sender;

    public SignOutMessage(String fireBaseToken, boolean signOut, User sender) {
        this.fireBaseToken = fireBaseToken;
        this.signOut = signOut;
        this.sender = sender;
    }

    public static SignOutMessage fromJson(String json) {
        return Constants.GSON.fromJson(json, SignOutMessage.class);
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

    public boolean isSignOut() {
        return signOut;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
