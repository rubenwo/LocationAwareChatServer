package com.Conn;

import com.Constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ImageClient {
    /**
     *
     */
    private static ImageClient instance;
    /**
     *
     */
    private Socket socket;
    /**
     *
     */
    private DataOutputStream toImageServer;
    /**
     *
     */
    private DataInputStream fromImageServer;
    /**
     *
     */
    private Thread backgroundTcpListener;
    /**
     *
     */
    private boolean running;
    /**
     *
     */
    private List<byte[]> uploadQueue;

    /**
     *
     */
    private ImageClient() {
        uploadQueue = new ArrayList<>();
        try {
            initializeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        backgroundTcpListener = new Thread(listenToImageServer());
        backgroundTcpListener.start();
    }

    /**
     * @return
     */
    public static ImageClient getInstance() {
        if (instance == null)
            instance = new ImageClient();
        return instance;
    }

    /**
     * @throws IOException
     */
    private void initializeConnection() throws IOException {
        socket = new Socket(Constants.IMAGE_SERVER_HOSTNAME, Constants.IMAGE_SERVER_PORT);
        toImageServer = new DataOutputStream(socket.getOutputStream());
        toImageServer.flush();

        fromImageServer = new DataInputStream(socket.getInputStream());
    }

    /**
     * @param image
     */
    public void addImageToUploadQueue(byte[] image) {
        uploadQueue.add(image);
    }

    /**
     *
     */
    private void writeImageToServer() {
        new Thread(() -> {
            for (byte[] image : uploadQueue) {
                uploadImage(image);
            }
        }).start();
    }

    /**
     * @param image
     */
    private void uploadImage(byte[] image) {

    }

    /**
     * @param imageID
     */
    public void deletedImage(String imageID) {

    }

    /**
     * @return
     */
    private Runnable listenToImageServer() {
        return () -> {
            try {
                while (running) {
                    fromImageServer.readInt();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }
}
