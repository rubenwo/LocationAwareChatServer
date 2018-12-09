package Conn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ImageClient {
    private static ImageClient instance;
    private Socket socket;
    private DataOutputStream toImageServer;
    private DataInputStream fromImageServer;
    private Thread backgroundTcpListener;
    private boolean running;
    private List<byte[]> uploadQueue;

    private ImageClient() {
        uploadQueue = new ArrayList<>();

        backgroundTcpListener = new Thread(listenToImageServer());
        backgroundTcpListener.start();
    }

    public static ImageClient getInstance() {
        if (instance == null)
            instance = new ImageClient();
        return instance;
    }

    public synchronized void addImageToUploadQueue(byte[] image) {
        uploadQueue.add(image);
    }

    private void writeImageToServer() {
        byte[] img = uploadQueue.get(0);
    }


    public void deletedImage(String imageID) {

    }

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
