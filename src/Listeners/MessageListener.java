package Listeners;

import Messages.IMessage;

public interface MessageListener {
    void onMessageReceiver(IMessage message);
}
