package com.Messages;

import com.Constants;

import java.util.Date;

public class ImageResponseMessage implements IMessage {
    private MessageType messageType = MessageType.ImageResponse_Message;
    private String imageID;
    private Date timeSend;

    public ImageResponseMessage(String imageID, Date timeSend) {
        this.imageID = imageID;
        this.timeSend = timeSend;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    public String getImageID() {
        return imageID;
    }

    public Date getTimeSend() {
        return timeSend;
    }

    @Override
    public String serialize() {
        return Constants.GSON.toJson(this);
    }
}
