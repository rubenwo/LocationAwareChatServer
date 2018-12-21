package com.Messages;

import com.Constants;

import java.util.Date;

public class ImageResponseMessage implements IMessage {
    private MessageType messageType = MessageType.ImageResponse_Message;
    private String imageUrl;
    private Date timeSend;

    public ImageResponseMessage(String imageUrl, Date timeSend) {
        this.imageUrl = imageUrl;
        this.timeSend = timeSend;
    }

    public static ImageResponseMessage deserialize(String serialized) {
        return Constants.GSON.fromJson(serialized, ImageResponseMessage.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Date getTimeSend() {
        return timeSend;
    }

    @Override
    public String serialize() {
        return Constants.GSON.toJson(this);
    }
}
