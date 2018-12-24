package com.Listeners;


import com.MessagingProtocol.IMessage;

public interface MessageListener {
    /**
     * @param message
     */
    void onMessageReceiver(IMessage message);
}
