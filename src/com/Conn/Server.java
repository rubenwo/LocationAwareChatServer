package com.Conn;

import com.Constants;
import com.Entities.Account;
import com.Entities.Event;
import com.Entities.Location;
import com.Entities.User;
import com.Services.DatabaseService;
import com.Services.IObserver;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server implements IObserver {
    /**
     *
     */
    private static Server instance;
    /**
     *
     */
    private ThreadPoolExecutor threadPool;
    /**
     * Concurrent HashMap. Key is the uid of an authenticated user. Value is the connected connection handler.
     */
    private ConcurrentHashMap<String, ConnectionHandler> clients;
    /**
     *
     */
    private ConcurrentHashMap<String, Event> events;
    /**
     *
     */
    private ServerSocket serverSocket;
    /**
     *
     */
    private Thread connectionListener;
    /**
     *
     */
    private boolean running = true;

    /**
     *
     */
    private Server() {
        try {
            InputStream serviceAccount = this.getClass().getResourceAsStream("/keys/locationawareapp-d4ad4-firebase-adminsdk-sjzdi-569d413b69.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://locationawareapp-d4ad4.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Starting server...");
        try {
            serverSocket = new ServerSocket(Constants.SERVER_PORT, 0, InetAddress.getByName(Constants.SERVER_IP_ADDRESS));
        } catch (IOException e) {
            e.printStackTrace();
        }
        threadPool = new ThreadPoolExecutor(0, 1500, 60, TimeUnit.DAYS, new ArrayBlockingQueue<>(2));
        clients = new ConcurrentHashMap<>();
        events = new ConcurrentHashMap<>();
        connectionListener = new Thread(listenForTcpConnection());
        connectionListener.start();
        System.out.println("Server has started!");

        DatabaseService databaseService = DatabaseService.getInstance();
        databaseService.subscribe(this);
        // databaseService.insertUser(new User("Ruben", "rubenwoldhuis@gmail.com", "testing_uid"));
        Account account = new Account(new User("Ruben", "test@gmail.com", "testing_UID"));
        account.setFireBaseMessagingId("FirebaseMessagingID");
        ArrayList<User> friends = new ArrayList<>();
        friends.add(new User("Ruben", "test@gmail.com", "testing_UID"));
        account.setFriends(friends);
        databaseService.insertAccount(account);
        databaseService.insertUser(new User("Ruben", "test@gmail.com", "testing_UID"));
        databaseService.insertEvent(new Event(new Location(10.23, 12343.432), "Event_name", "SOMEEVENTUID", "10-20-1203", new User("Ruben", "test@gmail.com", "testing_UID")));
    }

    /**
     * @return
     */
    public static Server getInstance() {
        if (instance == null)
            instance = new Server();
        return instance;
    }

    /**
     * @return
     */
    private Runnable listenForTcpConnection() {
        return () -> {
            while (running) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                    System.out.println("Connection established with client. Client IP: " + socket.getInetAddress().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (socket != null) {
                    ConnectionHandler client = new ConnectionHandler(socket, clients, events);
                    threadPool.execute(client);
                }
            }
        };
    }

    @Override
    public void notifyEventDataChanged(ArrayList<Event> events) {
        System.out.println("There are: " + events.size() + " events.");
    }

    @Override
    public void notifyUserDataChanged(ArrayList<User> users) {
        System.out.println("There are: " + users.size() + " users.");
    }

    @Override
    public void notifyAccountDataChanged(ArrayList<Account> accounts) {
        System.out.println("There are: " + accounts.size() + " accounts.");
    }
}
