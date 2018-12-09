package com.Conn;


import com.Messages.IMessage;
import com.Utils.MessageSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ConnectionHandler implements Runnable {
    /**
     *
     */
    private Socket socket;
    /**
     *
     */
    private DataOutputStream toClient;
    /**
     *
     */
    private DataInputStream fromClient;
    /**
     *
     */
    private boolean running;

    /**
     * @param socket
     */
    public ConnectionHandler(Socket socket) {
        this.socket = socket;
        try {
            toClient = new DataOutputStream(this.socket.getOutputStream());
            toClient.flush();
            fromClient = new DataInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Override
    public void run() {
        while (running) {
            // continuously checks for a message from the client;
            IMessage message = getMessage();

        }
    }

    /**
     * Cuz java's a bitch
     *
     * @param data A byte array of 4 bytes containing the length of the prefix.
     * @return an integer value converted from the byte array
     */
    private int byteArrayToInt(byte[] data) {
        ByteBuffer intShifter = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).order(ByteOrder.LITTLE_ENDIAN);
        intShifter.clear();
        intShifter.put(data, 0, Integer.SIZE / Byte.SIZE);
        intShifter.flip();
        return intShifter.getInt();
    }

    /**
     * @return
     */
    private IMessage getMessage() {
        byte[] prefix = new byte[4];
        int bytesRead = 0;
        byte[] data;

        while (bytesRead < prefix.length) {
            try {
                bytesRead += fromClient.read(prefix, bytesRead, prefix.length - bytesRead);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bytesRead = 0;
        data = new byte[byteArrayToInt(prefix)];

        while (bytesRead < data.length) {
            try {
                bytesRead += fromClient.read(prefix, bytesRead, prefix.length - bytesRead);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return MessageSerializer.deserialize(data);
    }
}
