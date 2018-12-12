package com.Conn;

import com.Entities.Friend;
import com.Messages.*;
import com.Utils.ImageUtil;

import java.util.UUID;

public class MessageHandler {
    /**
     *
     */
    private ConnectionHandler client;

    /**
     * @param client
     */
    public MessageHandler(ConnectionHandler client) {
        this.client = client;
    }

    /**
     * @param message
     */
    public void handleDisconnectingMessage(DisconnectingMessage message) {
        this.client.disconnect();
    }

    /**
     * @param message
     */
    public void handleFriendRequestMessage(FriendRequestMessage message) {
        for (ConnectionHandler connection : this.client.getClients()) {
            if (connection.getAccount().getUsername().equals(message.getFriend().getUsername())) {
                connection.writeMessage(new FriendRequestMessage(message.getSender(), message.getTimeSend(), message.getMessage(), new Friend(message.getSender())));
                return;
            }
        }
    }

    /**
     * @param message
     */
    public void handleFriendRequestAcceptedMessage(FriendRequestAcceptedMessage message) {
        for (ConnectionHandler connection : this.client.getClients()) {
            if (connection.getAccount().getUsername().equals(message.getFriendName()) && message.isAccepted()) {
                this.client.addFriend(connection);
                return;
            }
        }
    }

    /**
     * @param message
     */
    public void handleIdentificationMessage(IdentificationMessage message) {

    }

    /**
     * @param message
     */
    public void handleLocationMessage(LocationMessage message) {

    }

    /**
     * @param message
     */
    public void handleImageMessage(ImageMessage message) {
        byte[] image = ImageUtil.toImage(message.getBase64EncodedString());
        this.client.getImageClient().addImageToUploadQueue(UUID.randomUUID().toString(), message.getExtension(), image);
    }
}
