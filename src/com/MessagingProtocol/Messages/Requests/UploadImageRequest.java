package com.MessagingProtocol.Messages.Requests;


import com.Constants;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class UploadImageRequest implements IMessage {
    private MessageType messageType = MessageType.UploadImageRequest_Message;
    private String fireBaseToken;
    private String imageName;
    private String imageExtension;
    private String base64EncodedImage;
    private User target;
    private User sender;

    public UploadImageRequest(String fireBaseToken, String imageName, String imageExtension, String base64EncodedImage, User target, User sender) {
        this.fireBaseToken = fireBaseToken;
        this.imageName = imageName;
        this.imageExtension = imageExtension;
        this.base64EncodedImage = base64EncodedImage;
        this.target = target;
        this.sender = sender;
    }

    public static UploadImageRequest fromJson(String json) {
        return Constants.GSON.fromJson(json, UploadImageRequest.class);
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

    public String getImageName() {
        return imageName;
    }

    public String getImageExtension() {
        return imageExtension;
    }

    public String getBase64EncodedImage() {
        return base64EncodedImage;
    }

    public User getTarget() {
        return target;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
