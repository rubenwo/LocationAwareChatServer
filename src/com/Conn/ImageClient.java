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

    /**
     *
     */
    private ImageClient() {
        uploadQueue = new ArrayList<>();
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
    public void addImageToUploadQueue(String imageID, String imageExtension, byte[] image) {
        uploadQueue.add(new Image(imageID, imageExtension, image));
    }

    /**
     *
     */
    private void batchWriteImagesToServer() {
        new Thread(() -> {
            for (Image img : uploadQueue) {
                uploadImage(this.toImageServer, img.getName() + img.getExtension(), img.getData());
            }
        }).start();
    }

    /**
     * @param imageID
     * @param image
     */
    private void uploadImage(DataOutputStream toImageServer, String imageID, byte[] image) {
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
