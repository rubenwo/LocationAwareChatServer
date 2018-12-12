package com.Messages;

import com.Constants;

import java.util.Date;

public class DisconnectingMessage implements IMessage {
    private MessageType messageType = MessageType.Disconnecting_Message;
    private String sender;
    private Date timeSend;
    private String message;

    /**
     * @param sender
     * @param timeSend
     * @param message
     */
    public DisconnectingMessage(String sender, Date timeSend, String message) {
        this.sender = sender;
        this.timeSend = timeSend;
        this.message = message;
    }

    public static DisconnectingMessage deserialize(String serialized) {
        return Constants.GSON.fromJson(serialized, DisconnectingMessage.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    public String getSender() {
        return null;
    }

    public Date getTimeSend() {
        return null;
    }

    public String getMessage() {
        return null;
    }

    @Override
    public String serialize() {
        return Constants.GSON.toJson(this);
    }

    @Override
    public String toString() {
        return "DisconnectingMessage{" +
                "messageType=" + messageType +
                ", sender='" + sender + '\'' +
                ", timeSend=" + timeSend +
                ", message='" + message + '\'' +
                '}';
    }
}
