package com.Messages;

import com.Entities.Location;
import com.google.gson.Gson;

import java.time.LocalDateTime;

public class LocationMessage implements IMessage {
    private MessageType messageType = MessageType.Location_Message;
    private String sender;
    private LocalDateTime timeSend;
    private String message;
    private Location location;

    public LocationMessage(String sender, LocalDateTime timeSend, String message, Location location) {
        this.sender = sender;
        this.timeSend = timeSend;
        this.message = message;
        this.location = location;
    }

    public static LocationMessage deserialize(String serialized) {
        return null;
    }

    @Override
    public MessageType getMessageType() {
        return this.messageType;
    }

    public String getSender() {
        return this.sender;
    }

    public LocalDateTime getTimeSend() {
        return this.timeSend;
    }

    public String getMessage() {
        return this.message;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "LocationMessage{" +
                "messageType=" + messageType +
                ", sender='" + sender + '\'' +
                ", timeSend=" + timeSend +
                ", message='" + message + '\'' +
                ", location=" + location +
                '}';
    }
}
