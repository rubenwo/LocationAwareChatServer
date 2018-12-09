package com.Messages;

import java.time.LocalDateTime;

public class DisconnectingMessage implements IMessage {
    private MessageType messageType = MessageType.Disconnecting_Message;
    private String sender;
    private LocalDateTime timeSend;
    private String message;

    /**
     * @param sender
     * @param timeSend
     * @param message
     */
    public DisconnectingMessage(String sender, LocalDateTime timeSend, String message) {
        this.sender = sender;
        this.timeSend = timeSend;
        this.message = message;
    }

    public static DisconnectingMessage deserialize(String serialized) {
        String[] items = serialized.split(",");

        return new DisconnectingMessage(items[0], LocalDateTime.parse(items[1]), items[3]);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    public String getSender() {
        return null;
    }

    public LocalDateTime getTimeSend() {
        return null;
    }

    public String getMessage() {
        return null;
    }

    @Override
    public String serialize() {
        return messageType + "," + sender + "," + timeSend.toString() + "," + message;
    }
}
