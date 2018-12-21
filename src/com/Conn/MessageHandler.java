package com.Conn;

import com.Constants;
import com.Messages.*;
import com.Utils.ImageUtil;

import java.util.Date;
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

    }

    /**
     * @param message
     */
    public void handleFriendRequestAcceptedMessage(FriendRequestAcceptedMessage message) {

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
        String imageID = UUID.randomUUID().toString();
        //    this.client.getAccount().getUser().addImageID(imageID + message.getExtension());
        this.client.getImageClient().addImageToUploadQueue(imageID, message.getExtension(), image);
        this.client.writeMessage(new ImageResponseMessage(Constants.IMAGE_SERVER_HOSTNAME + "/images/" + imageID + message.getExtension(), new Date()));
    }
}
