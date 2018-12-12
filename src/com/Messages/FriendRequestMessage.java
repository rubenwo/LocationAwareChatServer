package com.Messages;


import com.Constants;
import com.Entities.Friend;

import java.util.Date;

public class FriendRequestMessage implements IMessage {
    private MessageType messageType = MessageType.FriendRequest_Message;
    private String sender;
    private Date timeSend;

    private String message;
    private Friend friend;

    public FriendRequestMessage(String sender, Date timeSend, String message, Friend friend) {
        this.sender = sender;
        this.timeSend = timeSend;
        this.message = message;
        this.friend = friend;
    }

    public static FriendRequestMessage deserialize(String serialized) {
        return Constants.GSON.fromJson(serialized, FriendRequestMessage.class);
    }

    @Override
    public MessageType getMessageType() {
        return null;
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

    public Friend getFriend() {
        return friend;
    }

    @Override
    public String serialize() {
        return Constants.GSON.toJson(this);
    }

    @Override
    public String toString() {
        return "FriendRequestMessage{" +
                "messageType=" + messageType +
                ", sender='" + sender + '\'' +
                ", timeSend=" + timeSend +
                ", message='" + message + '\'' +
                ", friend=" + friend +
                '}';
    }
}
