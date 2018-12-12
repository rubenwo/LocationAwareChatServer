package com.Messages;

import com.Constants;

import java.util.Date;


public class ImageMessage implements IMessage {
    /**
     *
     */
    private MessageType messageType = MessageType.Image_Message;
    /**
     *
     */
    private String sender;
    /**
     *
     */
    private String extension;
    /**
     *
     */
    private Date timeSend;
    /**
     *
     */
    private String base64EncodedString;

    /**
     * @param sender
     * @param extension
     * @param timeSend
     * @param base64EncodedString
     */
    public ImageMessage(String sender, String extension, Date timeSend, String base64EncodedString) {
        this.sender = sender;
        this.extension = extension;
        this.timeSend = timeSend;
        this.base64EncodedString = base64EncodedString;
    }

    /**
     * @param serialized
     * @return
     */
    public static ImageMessage deserialize(String serialized) {
        return Constants.GSON.fromJson(serialized, ImageMessage.class);
    }

    /**
     * @return
     */
    public String getSender() {
        return sender;
    }

    /**
     * @return
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @return
     */
    public Date getTimeSend() {
        return timeSend;
    }

    /**
     * @return
     */
    public String getBase64EncodedString() {
        return base64EncodedString;
    }

    /**
     * @return
     */
    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @return
     */
    @Override
    public String serialize() {
        return Constants.GSON.toJson(this);
    }
}
