package com.MessagingProtocol.Messages.Replies;

import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class SubscribeToEventReply implements IMessage {
    @Override
    public MessageType getMessageType() {
        return null;
    }

    @Override
    public String getFireBaseToken() {
        return null;
    }

    @Override
    public User getSender() {
        return null;
    }

    @Override
    public String toJson() {
        return null;
    }
}
