package com.MessagingProtocol;


import com.Entities.User;

public interface IMessage {
    MessageType getMessageType();

    String getFireBaseToken();

    User getSender();

    String toJson();
}
