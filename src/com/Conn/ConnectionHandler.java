package com.Conn;


import com.Constants;
import com.Entities.*;
import com.Listeners.MessageCallback;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.Messages.Replies.*;
import com.MessagingProtocol.Messages.Requests.*;
import com.MessagingProtocol.Messages.Updates.*;
import com.Services.Database.DatabaseService;
import com.Utils.MessageSerializer;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


public class ConnectionHandler implements Runnable {
    /**
     *
     */
    private ConcurrentHashMap<String, ConnectionHandler> clients;
    /**
     *
     */
    private ConcurrentHashMap<String, Event> events;
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
    public ConnectionHandler(Socket socket, ConcurrentHashMap<String, ConnectionHandler> clients, ConcurrentHashMap<String, Event> events) {
        this.socket = socket;
        this.clients = clients;
        this.events = events;
        this.imageClient = ImageClient.getInstance();
        try {
            fromClient = new DataInputStream(this.socket.getInputStream());
            toClient = new DataOutputStream(this.socket.getOutputStream());
            toClient.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.messageHandler = new MessageHandler(new MessageCallback() {
            /**
             *
             * @param authenticatedUser
             */
            @Override
            public void onIdentificationMessage(User authenticatedUser, String fireBaseMessagingId) {
                clients.put(authenticatedUser.getUid(), ConnectionHandler.this);
                user = authenticatedUser;
                System.out.println(user.toString() + " came online.");
                DatabaseService.getInstance().insertUser(authenticatedUser);
                account = DatabaseService.getInstance().getAccount(user);
                if (account == null) {
                    account = new Account(user);
                    account.setFireBaseMessagingId(fireBaseMessagingId);
                    DatabaseService.getInstance().insertAccount(account);
                }

                writeMessage(new AuthenticationSuccesfulMessage("SERVER", user));

                Message message = Message.builder()
                        .putData("score", "850")
                        .putData("time", "2:45")
                        .setToken(fireBaseMessagingId)
                        .build();

                String response = null;
                try {
                    response = FirebaseMessaging.getInstance().send(message);
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
                System.out.println("Successfully sent message: " + response);
            }

            /**
             *
             * @param image
             * @param target
             */
            @Override
            public void onUploadImageRequest(Image image, User target) {
                imageClient.addImageToUploadQueue(image.getName(), image.getExtension(), image.getData());
                if (clients.containsKey(target.getUid()))
                    clients.get(target.getUid()).writeMessage(new UploadImageReply("SERVER", Constants.IMAGE_SERVER_LINK + image.getName() + image.getExtension(), user));
                writeMessage(new UploadImageReply("SERVER", Constants.IMAGE_SERVER_LINK + image.getName() + image.getExtension(), user));
            }

            /**
             *
             * @param audio
             * @param target
             */
            @Override
            public void onUploadAudioRequest(Audio audio, User target) {
                // TODO: Upload audio to a REST server. Also create this server :D
                if (clients.containsKey(target.getUid()))
                    clients.get(target.getUid()).writeMessage(new UploadAudioMessageReply("SERVER", user, Constants.AUDIO_SERVER_LINK + audio.getName() + audio.getExtension()));
                writeMessage(new UploadAudioMessageReply("SERVER", user, Constants.AUDIO_SERVER_LINK + audio.getName() + audio.getExtension()));
            }

            /**
             *
             * @param location
             */
            @Override
            public void onLocationUpdateMessage(Location location) {
                if (account != null)
                    account.getFriends().forEach(friend -> clients.get(friend.getUid()).writeMessage(new LocationUpdateMessage("SERVER", location, user)));
                user.setLocation(location);
            }

            /**
             *
             * @param textMessage
             * @param target
             */
            @Override
            public void onTextMessage(String textMessage, User target) {
                if (clients.containsKey(target.getUid()))
                    clients.get(target.getUid()).writeMessage(new TextMessage("SERVER", textMessage, target, user));
            }

            /**
             *
             * @param signOut
             */
            @Override
            public void onSignOutMessage(boolean signOut) {
                if (signOut)
                    disconnect();
            }

            /**
             *
             */
            @Override
            public void onAuthenticationFailed() {
                writeMessage(new AuthenticationFailedMessage());
            }

            /**
             *
             * @param email
             */
            @Override
            public void onFriendRequest(String email) {
                ConnectionHandler targetHandler = null;
                for (ConnectionHandler handler : clients.values()) {
                    if (handler.user.getEmail().equals(email)) {
                        targetHandler = handler;
                        break;
                    }
                }
                if (targetHandler != null)
                    targetHandler.writeMessage(new FriendRequest("SERVER", user.getEmail(), user));
                else
                    writeMessage(new FriendReply("SERVER", user, null, false));
            }

            /**
             *
             */
            @Override
            public void onFriendsRequest() {
                if (account != null)
                    writeMessage(new FriendsReply("SERVER", user, account.getFriends()));
                else
                    writeMessage(new FriendsReply("SERVER", user, null));
            }

            /**
             *
             * @param friend
             * @param approved
             */
            @Override
            public void onFriendReply(User friend, boolean approved) {
                if (!approved) {
                    writeMessage(new FriendReply("SERVER", user, null, false));
                } else {
                    account.getFriends().add(friend);
                    DatabaseService.getInstance().insertAccount(account);
                    writeMessage(new FriendReply("SERVER", user, friend, true));
                }
            }

            /**
             *
             * @param users
             */
            @Override
            public void onFriendsReply(ArrayList<User> users) {
                // Server should never get this message
            }

            /**
             *
             * @param url
             */
            @Override
            public void onUploadAudioReply(String url) {
                // Server should never get this message
            }

            /**
             *
             * @param url
             */
            @Override
            public void onUploadImageReply(String url) {
                // Server should never get this message
            }

            @Override
            public void onEventCreationRequest(Event event) {
                events.put(event.getEventUID(), event);
                DatabaseService.getInstance().insertEvent(event);
                clients.values().forEach(client -> client.writeMessage(new EventCreationReply("SERVER", event.getEventCreator(), event.getLocation(), event.getEventName(), event.getEventUID(), event.getExpirationDateAsString())));
                checkExpiredEvents();
            }

            @Override
            public void onGetAllEventsRequest() {
                ArrayList<Event> eventsList = new ArrayList();
                for (Event event : events.values())
                    eventsList.add(event);
                writeMessage(new GetAllEventsReply("SERVER", null, eventsList));
            }

            @Override
            public void onEventSubscriptionRequest(String eventUID) {

            }

            @Override
            public void onUnsubscribeFromEventRequest(String eventUID) {

            }
        });
    }

    private void checkExpiredEvents() {

        events.values().forEach(event -> {
            LocalDate expirationDate = LocalDate.parse(event.getExpirationDateAsString());
            if (expirationDate.isBefore(LocalDate.now())) {
                events.remove(event.getEventUID());
                DatabaseService.getInstance().deleteEvent(event);
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
                case UploadImageRequest_Message:
                    messageHandler.handleUploadImageRequestMessage((UploadImageRequest) message);
                    break;
                case UploadImageReply_Message:
                    messageHandler.handleUploadImageReply((UploadImageReply) message);
                    break;
                case FriendsRequest_Message:
                    messageHandler.handleFriendsRequest((FriendsRequest) message);
                    break;
                case FriendsReply_Message:
                    messageHandler.handleFriendsReply((FriendsReply) message);
                    break;
                case UploadAudioRequest_Message:
                    messageHandler.handleUploadAudioRequestMessage((UploadAudioMessageRequest) message);
                    break;
                case UploadAudioReply_Message:
                    messageHandler.handleUploadAudioMessageReply((UploadAudioMessageReply) message);
                    break;
                case Identification_Message:
                    messageHandler.handleIdentificationMessage((IdentificationMessage) message);
                    break;
                case Text_Message:
                    messageHandler.handleTextMessage((TextMessage) message);
                    break;
                case SignOut_Message:
                    messageHandler.handleSignOutMessage((SignOutMessage) message);
                    break;
                case LocationUpdate_Message:
                    messageHandler.handleLocationUpdateMessage((LocationUpdateMessage) message);
                    break;
                case FriendRequest_Message:
                    messageHandler.handleFriendRequest((FriendRequest) message);
                    break;
                case FriendReply_Message:
                    messageHandler.handleFriendReply((FriendReply) message);
                    break;
                case EventCreationRequest_Message:
                    messageHandler.handleEventCreationRequest((EventCreationRequest) message);
                    break;
                case UnsubscribeFromEventRequest_Message:
                    messageHandler.handleUnsubscribeFromEventRequest((UnsubscribeFromEventRequest) message);
                    break;
                case SubscribeToEventRequest_Message:
                    messageHandler.handleSubscribeToEventRequest((SubscribeToEventRequest) message);
                    break;
                case GetAllEventsRequest_Message:
                    messageHandler.handleGetAllEventsRequest((GetAllEventsRequest) message);
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

        return MessageSerializer.deserialize(compressedData, false);
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
            if (user != null)
                clients.remove(user.getUid());
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
