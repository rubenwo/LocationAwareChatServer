package com.Messages;

import com.Constants;
import com.google.gson.Gson;

import java.util.Date;

public class IdentificationMessage implements IMessage {
    private MessageType messageType = MessageType.Identification_Message;
    private String sender;
    private Date timeSend;
    private String message;

    /**
     * @param sender
     * @param timeSend
     * @param message
     */
    public IdentificationMessage(String sender, Date timeSend, String message) {
        this.sender = sender;
        this.timeSend = timeSend;
        this.message = message;
    }

    public static IdentificationMessage deserialize(String serialized) {
        return Constants.GSON.fromJson(serialized, IdentificationMessage.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    public String getSender() {
        return sender;
    }

    public Date getTimeSend() {
        return timeSend;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "IdentificationMessage{" +
                "messageType=" + messageType +
                ", sender='" + sender + '\'' +
                ", timeSend=" + timeSend +
                ", message='" + message + '\'' +
                '}';
    }
}
