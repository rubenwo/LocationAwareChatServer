package com.Utils;

import com.Messages.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

public class MessageSerializer {
    /**
     * @param message
     * @return
     */
    public static byte[] serialize(IMessage message) {
        String msg = message.serialize();
        byte[] prefix = ByteBuffer.allocate(4).putInt(msg.length()).array();
        byte[] data = msg.getBytes();

        byte[] buffer = new byte[prefix.length + data.length];
        System.arraycopy(prefix, 0, buffer, 0, prefix.length);
        System.arraycopy(data, 0, buffer, prefix.length, data.length);
        byte[] compressed = null;
        try {
            compressed = CompressionUtil.compress(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compressed;
    }

    public static String serializeArrayList(ArrayList<Object> value) {
        StringBuilder serialized = new StringBuilder();
        for (int idx = 0; idx < value.size(); idx++)
            serialized.append("index:" + idx + "," + value.get(idx) + ";");
        return serialized.toString();
    }

    public static String serializeArray(Object[] value) {
        StringBuilder serialized = new StringBuilder();
        for (int idx = 0; idx < value.length; idx++)
            serialized.append("index:" + idx + "," + value[idx] + ";");
        return serialized.toString();
    }

    /**
     * @param byteData
     * @return
     */
    public static IMessage deserialize(byte[] byteData) {
        byte[] decompressed = null;
        try {
            decompressed = CompressionUtil.decompress(byteData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        String serialized = new String(decompressed);
        String[] items = serialized.split(",");
        String type = items[0];
        StringBuilder data = new StringBuilder();
        for (int i = 1; i < items.length; i++)
            data.append(items[i]);
        switch (MessageType.valueOf(type)) {
            case FriendRequest_Message:
                return FriendRequestMessage.deserialize(data.toString());
            case Disconnecting_Message:
                return DisconnectingMessage.deserialize(data.toString());
            case Identification_Message:
                return IdentificationMessage.deserialize(data.toString());
            case Location_Message:
                return null;
        }
        return null;
    }
}
