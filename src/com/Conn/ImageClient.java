package com.Conn;

import com.Constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


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
    private HashMap<String, byte[]> uploadQueue;

    /**
     *
     */
    private ImageClient() {
        uploadQueue = new HashMap<>();
        try {
            createConnection();
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
    private void createConnection() throws IOException {
        socket = new Socket(Constants.IMAGE_SERVER_HOSTNAME, Constants.IMAGE_SERVER_PORT);
        toImageServer = new DataOutputStream(socket.getOutputStream());
        toImageServer.flush();

        fromImageServer = new DataInputStream(socket.getInputStream());
    }

    private void closeConnection() throws IOException {
        toImageServer.flush();
        toImageServer.close();
        fromImageServer.close();
        socket.close();
    }

    /**
     * @param image
     */
    public void addImageToUploadQueue(String imageID, byte[] image) {
        uploadQueue.put(imageID, image);
    }

    /**
     *
     */
    private void batchWriteImagesToServer() {
        new Thread(() -> {
            for (Map.Entry<String, byte[]> entry : uploadQueue.entrySet()) {
                uploadImage(entry.getKey(), entry.getValue());
            }
        }).start();
    }

    /**
     * @param imageID
     * @param image
     */
    private void uploadImage(String imageID, byte[] image) {
        try {
            toImageServer.writeChars(imageID + "\n");
            toImageServer.flush();
            toImageServer.write(image, 0, image.length);
            toImageServer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
