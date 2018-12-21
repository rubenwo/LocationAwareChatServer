package com.Conn;

import com.Constants;
import com.Entities.Image;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


public class ImageClient {
    /**
     *
     */
    private static volatile ImageClient instance;
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
    private boolean running = true;
    /**
     *
     */
    private ArrayList<Image> uploadQueue;
    private ArrayList<Image> uploadProcessing;
    private Thread uploadThread = null;

    /**
     *
     */
    private ImageClient() {
        uploadQueue = new ArrayList<>();
        uploadProcessing = new ArrayList<>();
        // backgroundTcpListener = new Thread(listenToImageServer());
        // backgroundTcpListener.start();
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
     * @param imageID
     * @param imageExtension
     * @param image
     */
    public synchronized void addImageToUploadQueue(String imageID, String imageExtension, byte[] image) {
        uploadQueue.add(new Image(imageID, imageExtension, image));
        if (uploadThread == null) {
            uploadProcessing.addAll(uploadQueue);
            this.uploadQueue.clear();
            uploadThread = new Thread(batchWriteImagesToServer());
            uploadThread.start();
        }
    }

    /**
     * @return
     */
    private Runnable batchWriteImagesToServer() {
        return () -> {
            try {
                this.createConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Image img : this.uploadProcessing) {
                System.out.println("uploading: " + img.getName());
                uploadImage(this, img);
            }
            try {
                this.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.uploadProcessing.clear();
            uploadThread = null;
        };
    }

    private static byte[] toByteArray(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
    }

    /**
     * @param imageClient
     * @param image
     * @return
     */
    private void uploadImage(ImageClient imageClient, Image image) {
        String imageID = image.getName() + image.getExtension();
        byte[] data = image.getData();
        try {
            imageClient.createConnection();
            imageClient.toImageServer.writeChars(imageID + "\n");
            imageClient.toImageServer.flush();
            imageClient.toImageServer.write(data, 0, data.length);
            imageClient.toImageServer.flush();
            imageClient.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param imageID
     */
    public void deletedImage(String imageID) {

    }

}
