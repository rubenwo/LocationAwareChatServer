package com;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {
    public Main() throws IOException {
        Socket socket = new Socket(Constants.IMAGE_SERVER_HOSTNAME, Constants.IMAGE_SERVER_PORT);
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        for (int i = 0; i < 100; i++)
            outputStream.writeChars("Index = " + i + ";");
        //  while (true) ;
        outputStream.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
