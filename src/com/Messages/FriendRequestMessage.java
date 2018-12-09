package com.Messages;


import com.Entities.Friend;

import java.time.LocalDateTime;

public class FriendRequestMessage implements IMessage {
    private MessageType messageType = MessageType.FriendRequest_Message;
    private String sender;
    private LocalDateTime timeSend;

    private String message;
    private Friend friend;

    public FriendRequestMessage(String sender, LocalDateTime timeSend, String message, Friend friend) {
        this.sender = sender;
        this.timeSend = timeSend;
        this.message = message;
        this.friend = friend;
    }

    public static FriendRequestMessage deserialize(String serialized) {
        return null;
    }

    @Override
    public MessageType getMessageType() {
        return null;
    }

    public String getSender() {
        return sender;
    }

    public LocalDateTime getTimeSend() {
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
        return null;
    }
}
