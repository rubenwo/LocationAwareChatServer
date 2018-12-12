package com.Messages;

import com.Constants;

import java.util.Date;

public class IdentificationMessage implements IMessage {
    /**
     *
     */
    private MessageType messageType = MessageType.Identification_Message;
    /**
     *
     */
    private String sender;
    /**
     *
     */
    private Date timeSend;
    /**
     *
     */
    private String message;

    /**
     * @param sender
     * @param timeSend
     * @param message
     */
    public IdentificationMessage(String sender, Date timeSend, String message) {
        this.sender = sender;
        this.timeSend = timeSend;
        this.message = message;
    }

    /**
     * @param serialized
     * @return
     */
    public static IdentificationMessage deserialize(String serialized) {
        return Constants.GSON.fromJson(serialized, IdentificationMessage.class);
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
    public String getSender() {
        return sender;
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
    public String getMessage() {
        return message;
    }

    /**
     * @return
     */
    @Override
    public String serialize() {
        return Constants.GSON.toJson(this);
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "IdentificationMessage{" +
                "messageType=" + messageType +
                ", sender='" + sender + '\'' +
                ", timeSend=" + timeSend +
                ", message='" + message + '\'' +
                '}';
    }
}
