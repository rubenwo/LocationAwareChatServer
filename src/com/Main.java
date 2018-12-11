package com;

import com.Entities.Friend;
import com.Messages.FriendRequestMessage;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    private static ArrayList<String> strings;
    private static ArrayList strings2;

    /* public Main() throws IOException {
         Socket socket = new Socket(Constants.IMAGE_SERVER_HOSTNAME, Constants.IMAGE_SERVER_PORT);
         DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
         outputStream.flush();
         BufferedImage img = ImageIO.read(new File("C:/Users/diabl/Documents/background.jpg"));
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ImageIO.write(img, "jpg", baos);
         baos.flush();
         byte[] data = baos.toByteArray();
         baos.close();

         outputStream.writeChars("filee.jpg\n");
         outputStream.flush();
         outputStream.write(data, 0, data.length);
         outputStream.flush();
         outputStream.close();
     }
     */
    public Main() throws IOException {
        FriendRequestMessage msg = new FriendRequestMessage("Ruben", LocalDateTime.now(), "Hello there", new Friend());
        String ser = msg.serialize();
        System.out.println(ser);
        Gson gson = new Gson();
        FriendRequestMessage deser = gson.fromJson(ser, FriendRequestMessage.class);
        System.out.println(deser.toString());
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
