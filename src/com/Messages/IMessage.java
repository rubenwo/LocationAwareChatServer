package com.Messages;

/**
 *
 */
public interface IMessage {
    /**
     * @return
     */
    MessageType getMessageType();

    /**
     * @return
     */
    String serialize();
}