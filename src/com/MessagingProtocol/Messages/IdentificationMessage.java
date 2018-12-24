package com.MessagingProtocol.Messages;

import com.Constants;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class IdentificationMessage implements IMessage {
    private MessageType messageType = MessageType.Identification_Message;
    private String fireBaseToken;
    private String name;

    public IdentificationMessage(String fireBaseToken, String name) {
        this.fireBaseToken = fireBaseToken;
        this.name = name;
    }

    public static IdentificationMessage deserialize(String json) {
        return Constants.GSON.fromJson(json, IdentificationMessage.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
