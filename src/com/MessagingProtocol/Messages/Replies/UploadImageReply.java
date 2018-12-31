package com.MessagingProtocol.Messages.Replies;

import com.Constants;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class UploadImageReply implements IMessage {
    private MessageType messageType = MessageType.UploadImageReply_Message;
    private String fireBaseToken;
    private String imageUrl;

    private User sender;

    public UploadImageReply(String fireBaseToken, String imageUrl, User sender) {
        this.fireBaseToken = fireBaseToken;
        this.imageUrl = imageUrl;
        this.sender = sender;
    }

    public static UploadImageReply fromJson(String json) {
        return Constants.GSON.fromJson(json, UploadImageReply.class);
    }

    @Override
    public User getSender() {
        return sender;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
