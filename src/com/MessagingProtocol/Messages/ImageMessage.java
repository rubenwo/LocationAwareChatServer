package com.MessagingProtocol.Messages;

import com.Constants;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class ImageMessage implements IMessage {
    private MessageType messageType = MessageType.Image_Message;
    private String fireBaseToken;
    private String imageName;
    private String imageExtension;
    private String base64EncodedImage;
    private String textMessage;
    private User target;

    public ImageMessage(String fireBaseToken, String imageName, String imageExtension, String base64EncodedImage, String textMessage, User target) {
        this.fireBaseToken = fireBaseToken;
        this.imageName = imageName;
        this.imageExtension = imageExtension;
        this.base64EncodedImage = base64EncodedImage;
        this.textMessage = textMessage;
        this.target = target;
    }

    public static ImageMessage deserialize(String json) {
        return Constants.GSON.fromJson(json, ImageMessage.class);
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

    public String getTextMessage() {
        return textMessage;
    }

    public User getTarget() {
        return target;
    }


    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
