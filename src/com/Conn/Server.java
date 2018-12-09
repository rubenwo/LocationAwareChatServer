package com.Conn;

import com.Constants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
            serverSocket = new ServerSocket(Constants.SERVER_PORT, 0, InetAddress.getByName(Constants.SERVER_IP_ADDRESS));
        } catch (IOException e) {
            e.printStackTrace();
        }
        threadPool = new ThreadPoolExecutor(0, 1500, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2));
        connectionListener = new Thread(listenForTcpConnection());
        connectionListener.start();
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
                if (socket != null)
                    threadPool.execute(new ConnectionHandler(socket));
            }
        };
    }
}
