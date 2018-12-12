package com.Conn;

import com.Entities.Friend;
import com.Messages.*;

public class MessageHandler {
    private ConnectionHandler client;

    public MessageHandler(ConnectionHandler client) {
        this.client = client;
    }

    public void handleDisconnectingMessage(DisconnectingMessage message) {
        this.client.disconnect();
    }

    public void handleFriendRequestMessage(FriendRequestMessage message) {
        for (ConnectionHandler connection : this.client.getClients()) {
            if (connection.getAccount().getUsername().equals(message.getFriend().getUsername())) {
                connection.writeMessage(new FriendRequestMessage(message.getSender(), message.getTimeSend(), message.getMessage(), new Friend(message.getSender())));
                return;
            }
        }
    }

    public void handleFriendRequestAcceptedMessage(FriendRequestAcceptedMessage message) {
        for (ConnectionHandler connection : this.client.getClients()) {
            if (connection.getAccount().getUsername().equals(message.getFriendName()) && message.isAccepted()) {
                this.client.addFriend(connection);
                return;
            }
        }
    }

    public void handleIdentificationMessage(IdentificationMessage message) {

    }

    public void handleLocationMessage(LocationMessage message) {

    }
}
