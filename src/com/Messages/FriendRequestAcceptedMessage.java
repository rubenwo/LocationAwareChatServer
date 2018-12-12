package com.Messages;

import com.Constants;

public class FriendRequestAcceptedMessage implements IMessage {
    private MessageType messageType = MessageType.FriendRequestAccepted_Message;
    private String username;
    private String friendName;
    private boolean accepted;

    public FriendRequestAcceptedMessage(String username, String friendName, boolean accepted) {
        this.username = username;
        this.friendName = friendName;
        this.accepted = accepted;
    }

    public static FriendRequestAcceptedMessage deserialze(String serialized) {
        return Constants.GSON.fromJson(serialized, FriendRequestAcceptedMessage.class);
    }

    public String getUsername() {
        return username;
    }

    public String getFriendName() {
        return friendName;
    }

    public boolean isAccepted() {
        return accepted;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String serialize() {
        return Constants.GSON.toJson(this);
    }
}
