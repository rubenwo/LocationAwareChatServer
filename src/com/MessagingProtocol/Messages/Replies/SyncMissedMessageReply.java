package com.MessagingProtocol.Messages.Replies;

import com.Constants;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

import java.util.ArrayList;

public class SyncMissedMessageReply implements IMessage {
    private MessageType messageType = MessageType.SyncMissedMessagesReply_Message;
    private String fireBaseToken;
    private User sender;
    private ArrayList<IMessage> messages;

    public SyncMissedMessageReply(String fireBaseToken, User sender, ArrayList<IMessage> messages) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
        this.messages = messages;
    }

    public static SyncMissedMessageReply fromJson(String json) {
        return Constants.GSON.fromJson(json, SyncMissedMessageReply.class);
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
    public User getSender() {
        return sender;
    }

    public ArrayList<IMessage> getMessages() {
        return messages;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
