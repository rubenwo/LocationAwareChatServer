package com.MessagingProtocol;

public interface IMessage {
    /**
     * @return
     */
    MessageType getMessageType();

    /**
     * @return
     */
    String getFireBaseToken();

    /**
     * @return
     */
    String toJson();
}
