package com.Conn;


import com.Entities.Account;
import com.Messages.IMessage;
import com.Messages.LocationMessage;
import com.Utils.CompressionUtil;
import com.Utils.MessageSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.UUID;
import java.util.zip.DataFormatException;

public class ConnectionHandler implements Runnable {
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
    }

    public void updateClients(ArrayList<ConnectionHandler> clients) {
        this.clients = clients;
    }

    /**
     *
     */
    @Override
    public void run() {
        while (running) {
            // continuously checks for a message from the client;
            IMessage message = getMessage();
            switch (message.getMessageType()) {
                case Location_Message:
                    handleLocationMessage((LocationMessage) message);
                    break;
                case Disconnecting_Message:
                    break;
                case FriendRequest_Message:
                    break;
                case Identification_Message:
                    break;
            }
        }
    }

    private void handleLocationMessage(LocationMessage message) {

        System.out.println(message.toString());
    }

    private void uploadImage(byte[] image) {
        String imageID = "images/" + UUID.randomUUID().toString() + ".jpg";
        this.imageClient.addImageToUploadQueue(imageID, image);
        this.account.getUser().getImageIDs().add(imageID);
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
}
