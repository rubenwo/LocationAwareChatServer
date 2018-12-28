package com.Conn;


import com.Entities.Account;
import com.Entities.Image;
import com.Entities.Location;
import com.Entities.User;
import com.Listeners.MessageCallback;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.Messages.*;
import com.Utils.MessageSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;


public class ConnectionHandler implements Runnable {
    /**
     *
     */
    private ConcurrentHashMap<String, ConnectionHandler> clients;
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
    private User user;
    /**
     *
     */
    private boolean running = true;

    /**
     * @param socket
     * @param clients
     */
    public ConnectionHandler(Socket socket, ConcurrentHashMap<String, ConnectionHandler> clients) {
        this.socket = socket;
        this.clients = clients;
        this.imageClient = ImageClient.getInstance();
        try {
            toClient = new DataOutputStream(this.socket.getOutputStream());
            toClient.flush();
            fromClient = new DataInputStream(this.socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.messageHandler = new MessageHandler(new MessageCallback() {
            @Override
            public void onIdentificationMessage(User authenticatedUser) {
                clients.put(authenticatedUser.getUid(), ConnectionHandler.this);
                user = authenticatedUser;
                System.out.println(user.toString());
                writeMessage(new IdentificationMessage("Server", "My name is"));
            }

            @Override
            public void onImageMessage(Image image, User target) {
                imageClient.addImageToUploadQueue(image.getName(), image.getExtension(), image.getData());
                if (clients.containsKey(target.getUid()))
                    clients.get(target.getUid()).writeMessage(new ImageUploadedMessage("SERVER", "http://206.189.3.15/images/" + image.getName() + image.getExtension()));
                writeMessage(new ImageUploadedMessage("SERVER", "http://206.189.3.15/images/" + image.getName() + image.getExtension()));
            }

            @Override
            public void onAudioMessage(String base64EncodedAudio, User target) {
                if (clients.containsKey(target.getUid()))
                    clients.get(target.getUid()).writeMessage(new AudioMessage("SERVER", base64EncodedAudio, null));
            }

            @Override
            public void onLocationUpdateMessage(Location location) {
                user.setLocation(location);
            }

            @Override
            public void onTextMessage(String textMessage, User target) {
                if (clients.containsKey(target.getUid()))
                    clients.get(target.getUid()).writeMessage(new TextMessage("SERVER", textMessage, null));
            }

            @Override
            public void onSignOutMessage(boolean signOut) {
                if (signOut)
                    disconnect();
            }
        });
    }

    /**
     * Continuously checks for a message from the client.
     */
    @Override
    public void run() {
        while (running) {
            IMessage message = getMessage();
            if (message == null) {
                disconnect();
                return;
            }
            switch (message.getMessageType()) {
                case Image_Message:
                    messageHandler.handleImageMessage((ImageMessage) message);
                    break;
                case Identification_Message:
                    messageHandler.handleIdentificationMessage((IdentificationMessage) message);
                    break;
                case Text_Message:
                    messageHandler.handleTextMessage((TextMessage) message);
                    break;
                case Audio_Message:
                    messageHandler.handleAudioMessage((AudioMessage) message);
                    break;
                case SignOut_Message:
                    messageHandler.handleSignOutMessage((SignOutMessage) message);
                    break;
                case LocationUpdate_Message:
                    messageHandler.handleLocationUpdateMessage((LocationUpdateMessage) message);
                    break;
            }
        }
    }

    /**
     * @param message
     */
    public void writeMessage(IMessage message) {
        System.out.println("SENDING: " + message.toJson());
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
                if (bytesRead < 0)
                    return null;
                System.out.println("Reading bytes: " + bytesRead);
            } catch (IOException e) {
                return null;
            }
        }
        System.out.println("Got prefix: " + prefix.length + " Bytes.");
        bytesRead = 0;
        byte[] compressedData = new byte[byteArrayToInt(prefix)];
        while (bytesRead < compressedData.length) {
            try {
                bytesRead += fromClient.read(compressedData, bytesRead, compressedData.length - bytesRead);
            } catch (IOException e) {
                return null;
            }
        }
        System.out.println("Got data");
        String data = null;
/*
        try {
            data = CompressionUtil.decompress(compressedData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }*/
        String ser = "";
        try {
            ser = new String(compressedData, 0, compressedData.length, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return MessageSerializer.deserialize(ser);
    }

    /**
     *
     */
    public void disconnect() {
        System.out.println("Disconnecting...");
        try {
            running = false;
            toClient.flush();
            toClient.close();
            fromClient.close();
            socket.close();
            /*if (user != null)
                if (clients)
                clients.remove(user.getUid());*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Closed connection successfully.");
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
    public ConcurrentHashMap<String, ConnectionHandler> getClients() {
        return clients;
    }

    /**
     * @return
     */
    public ImageClient getImageClient() {
        return imageClient;
    }
}
