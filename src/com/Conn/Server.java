package com.Conn;

import com.Constants;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
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
        connectionListener = new Thread(listenForTcpConnection());
        connectionListener.start();
        System.out.println("Server has started!");
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
                    System.out.println("Connection established with client.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (socket != null) {
                    ConnectionHandler client = new ConnectionHandler(socket, clients);
                    threadPool.execute(client);
                }
            }
        };
    }
}
