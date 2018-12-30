package com.MessagingProtocol.Messages.Updates;

import com.Constants;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class TextMessage implements IMessage {
    private MessageType messageType = MessageType.Text_Message;
    private String fireBaseToken;
    private String textMessage;
    private User target;

    public TextMessage(String fireBaseToken, String textMessage, User target) {
        this.fireBaseToken = fireBaseToken;
        this.textMessage = textMessage;
        this.target = target;
    }

    public static TextMessage fromJson(String json) {
        return Constants.GSON.fromJson(json, TextMessage.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
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
