package com.Conn;

import com.Constants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
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
     *
     */
    private ArrayList<ConnectionHandler> clients;
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
        System.out.println("Starting server...");
        try {
            serverSocket = new ServerSocket(Constants.SERVER_PORT, 0, InetAddress.getByName(Constants.SERVER_IP_ADDRESS));
        } catch (IOException e) {
            e.printStackTrace();
        }
        threadPool = new ThreadPoolExecutor(0, 1500, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2));
        clients = new ArrayList<>();
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
                    updateClients(client);
                    threadPool.execute(client);
                }
            }
        };
    }

    private void updateClients(ConnectionHandler client) {
        this.clients.add(client);
        for (ConnectionHandler handler : clients)
            handler.updateClients(this.clients);
    }
}
