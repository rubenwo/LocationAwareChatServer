package com.Listeners;

import com.Messages.IMessage;

public interface MessageListener {
    /**
     * @param message
     */
    void onMessageReceiver(IMessage message);
}
