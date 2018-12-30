package com.MessagingProtocol.Messages.Requests;

import com.Constants;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class FriendsRequest implements IMessage {
    private MessageType messageType = MessageType.FriendsRequest_Message;
    private String fireBaseToken;

    public FriendsRequest(String fireBaseToken) {
        this.fireBaseToken = fireBaseToken;
    }

    public static FriendsRequest fromJson(String json) {
        return Constants.GSON.fromJson(json, FriendsRequest.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
