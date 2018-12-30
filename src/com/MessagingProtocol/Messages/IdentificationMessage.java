package com.MessagingProtocol.Messages;

import com.Constants;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class IdentificationMessage implements IMessage {
    private MessageType messageType = MessageType.Identification_Message;
    private String fireBaseToken;

    public IdentificationMessage(String fireBaseToken) {
        this.fireBaseToken = fireBaseToken;
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

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
