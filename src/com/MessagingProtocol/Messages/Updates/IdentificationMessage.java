package com.MessagingProtocol.Messages.Updates;


import com.Constants;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class IdentificationMessage implements IMessage {
    private MessageType messageType = MessageType.Identification_Message;
    private String fireBaseToken;
    private String fireBaseMessagingId;
    private User sender = null;

    public IdentificationMessage(String fireBaseToken, String fireBaseMessagingId) {
        this.fireBaseToken = fireBaseToken;
        this.fireBaseMessagingId = fireBaseMessagingId;
    }

    public static IdentificationMessage fromJson(String json) {
        return Constants.GSON.fromJson(json, IdentificationMessage.class);
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

    public String getFireBaseMessagingId() {
        return fireBaseMessagingId;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
