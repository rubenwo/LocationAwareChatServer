package com.MessagingProtocol.Messages.Replies;

import com.Constants;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class FriendReply implements IMessage {
    private MessageType messageType = MessageType.FriendReply_Message;
    private String fireBaseToken;
    private User friend;
    private boolean approved;

    public FriendReply(String fireBaseToken, User friend, boolean approved) {
        this.fireBaseToken = fireBaseToken;
        this.friend = friend;
        this.approved = approved;
    }

    public static FriendsReply fromJson(String json) {
        return Constants.GSON.fromJson(json, FriendsReply.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public User getFriend() {
        return friend;
    }

    public boolean isApproved() {
        return approved;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
