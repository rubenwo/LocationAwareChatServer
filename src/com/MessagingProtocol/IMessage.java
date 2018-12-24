package com.MessagingProtocol;

public interface IMessage {
    MessageType getMessageType();

    String getFireBaseToken();

    String toJson();
}
