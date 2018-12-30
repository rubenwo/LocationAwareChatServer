package com.MessagingProtocol.Messages.Updates;

import com.Constants;
import com.Entities.Location;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class LocationUpdateMessage implements IMessage {
    private MessageType messageType = MessageType.LocationUpdate_Message;
    private String fireBaseToken;
    private Location location;

    public LocationUpdateMessage(String fireBaseToken, Location location) {
        this.fireBaseToken = fireBaseToken;
        this.location = location;
    }

    public static LocationUpdateMessage fromJson(String json) {
        return Constants.GSON.fromJson(json, LocationUpdateMessage.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
