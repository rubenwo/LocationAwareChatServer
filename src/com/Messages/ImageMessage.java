package com.ruben.woldhuis.androideindopdrachtapp.Messages;

import com.Constants;
import com.Messages.IMessage;
import com.Messages.MessageType;

import java.util.Date;


public class ImageMessage implements IMessage {
    private MessageType messageType = MessageType.Image_Message;
    private String sender;
    private String extension;
    private Date timeSend;
    private String base64EncodedString;

    public ImageMessage(String sender, String extension, Date timeSend, String base64EncodedString) {
        this.sender = sender;
        this.extension = extension;
        this.timeSend = timeSend;
        this.base64EncodedString = base64EncodedString;
    }

    public static ImageMessage deserialize(String serialized) {
        return Constants.GSON.fromJson(serialized, ImageMessage.class);
    }

    public String getSender() {
        return sender;
    }

    public String getExtension() {
        return extension;
    }

    public Date getTimeSend() {
        return timeSend;
    }

    public String getBase64EncodedString() {
        return base64EncodedString;
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
