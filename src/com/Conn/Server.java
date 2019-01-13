package com.Conn;

import com.Constants;
import com.Entities.Account;
import com.Entities.Event;
import com.Services.Database.DatabaseService;
import com.Services.Database.IObserver;
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
    private ArrayList<Account> accounts;
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
        //   databaseService.storeMessage("TEST", new TextMessage("FireBaseToken", "Hallo", new User("Bla", "test", "testUID"), new User("Bla", "test", "testUID")));

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
                    ConnectionHandler client = new ConnectionHandler(socket, clients, accounts, events);
                    new Thread(client).start();
                    //threadPool.execute(client);
                }
            }
        };
    }

    @Override
    public void notifyDataChanged() {
        for (Event event : DatabaseService.getInstance().getCachedEvents())
            this.events.put(event.getEventUID(), event);
        System.out.println("There are: " + events.size() + " events.");
        System.out.println("There are: " + DatabaseService.getInstance().getCachedUsers().size() + " users.");
        accounts = DatabaseService.getInstance().getCachedAccounts();
        System.out.println("There are: " + accounts.size() + " accounts.");
    }
}
