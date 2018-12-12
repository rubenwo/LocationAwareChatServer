package com.Conn;


import com.Entities.Account;
import com.Messages.*;
import com.Utils.CompressionUtil;
import com.Utils.MessageSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

public class ConnectionHandler implements Runnable {
    /**
     *
     */
    private ArrayList<ConnectionHandler> friends;
    /**
     *
     */
    private ArrayList<ConnectionHandler> clients;
    /**
     *
     */
    private Socket socket;
    /**
     *
     */
    private DataOutputStream toClient;
    /**
     *
     */
    private DataInputStream fromClient;
    /**
     *
     */
    private ImageClient imageClient;
    /**
     *
     */
    private MessageHandler messageHandler;
    /**
     *
     */
    private Account account;
    /**
     *
     */
    private boolean running = true;

    /**
     * @param socket
     */
    public ConnectionHandler(Socket socket, ArrayList<ConnectionHandler> clients) {
        this.socket = socket;
        this.clients = clients;
        //  this.imageClient = ImageClient.getInstance();
        try {
            toClient = new DataOutputStream(this.socket.getOutputStream());
            toClient.flush();
            fromClient = new DataInputStream(this.socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.messageHandler = new MessageHandler(this);
    }

    public void updateClients(ArrayList<ConnectionHandler> clients) {
        this.clients = clients;
    }

    /**
     * Continuously checks for a message from the client.
     */
    @Override
    public void run() {
        while (running) {
            IMessage message = getMessage();
            if (message != null) {
                switch (message.getMessageType()) {
                    case Location_Message:
                        messageHandler.handleLocationMessage((LocationMessage) message);
                        break;
                    case Disconnecting_Message:
                        messageHandler.handleDisconnectingMessage((DisconnectingMessage) message);
                        break;
                    case FriendRequest_Message:
                        messageHandler.handleFriendRequestMessage((FriendRequestMessage) message);
                        break;
                    case Identification_Message:
                        messageHandler.handleIdentificationMessage((IdentificationMessage) message);
                        break;
                    case FriendRequestAccepted_Message:
                        messageHandler.handleFriendRequestAcceptedMessage((FriendRequestAcceptedMessage) message);
                        break;
                    case Image_Message:
                        messageHandler.handleImageMessage((ImageMessage) message);
                        break;
                }
            }
        }
    }

    /**
     * @param friend
     */
    public void addFriend(ConnectionHandler friend) {
        this.friends.add(friend);
    }

    /**
     * @param message
     */
    public void writeMessage(IMessage message) {
        byte[] buffer = MessageSerializer.serialize(message);
        try {
            toClient.write(buffer, 0, buffer.length);
            toClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cuz java's a bitch
     *
     * @param data A byte array of 4 bytes containing the length of the prefix.
     * @return an integer value converted from the byte array
     */
    private int byteArrayToInt(byte[] data) {
        ByteBuffer wrapped = ByteBuffer.wrap(data);
        return wrapped.getInt();
    }

    /**
     * @return
     */
    private IMessage getMessage() {
        byte[] prefix = new byte[4];
        int bytesRead = 0;

        while (bytesRead < prefix.length) {
            try {
                bytesRead += fromClient.read(prefix, bytesRead, prefix.length - bytesRead);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Got prefix");
        bytesRead = 0;
        byte[] compressedData = new byte[byteArrayToInt(prefix)];
        while (bytesRead < compressedData.length) {
            try {
                bytesRead += fromClient.read(compressedData, bytesRead, compressedData.length - bytesRead);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Got data");
        String data = null;

        try {
            data = CompressionUtil.decompress(compressedData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }

        return MessageSerializer.deserialize(data);
    }

    /**
     *
     */
    public void disconnect() {

    }

    /**
     * @return
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @return
     */
    public ArrayList<ConnectionHandler> getClients() {
        return clients;
    }

    /**
     * @return
     */
    public ImageClient getImageClient() {
        return imageClient;
    }
}
