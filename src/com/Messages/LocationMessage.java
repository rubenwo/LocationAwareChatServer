package com.Messages;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDateTime;

public class LocationMessage implements IMessage {
    private MessageType messageType = MessageType.Location_Message;
    private String sender;
    private LocalDateTime timeSend;
    private String message;
    private LatLng location;

    public LocationMessage(String sender, LocalDateTime timeSend, String message, LatLng location) {
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

    public LatLng getLocation() {
        return location;
    }

    @Override
    public String serialize() {
        return null;
    }
}
