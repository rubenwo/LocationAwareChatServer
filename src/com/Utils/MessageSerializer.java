package com.Utils;

import com.Messages.*;

import java.io.IOException;
import java.nio.ByteBuffer;

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

    /**
     * @param serialized
     * @return
     */
    public static IMessage deserialize(String serialized) {
        System.out.println(serialized);
        String[] elements = serialized.split(",");
        String messageType = "";
        for (String item : elements) {
            if (item.contains("messageType")) {
                messageType = item;
                break;
            }
        }
        messageType = messageType.split(":")[1];
        messageType = messageType.substring(1, messageType.length() - 1);
        MessageType type = MessageType.valueOf(messageType);
        switch (type) {
            case FriendRequest_Message:
                return FriendRequestMessage.deserialize(serialized);
            case Disconnecting_Message:
                return DisconnectingMessage.deserialize(serialized);
            case Identification_Message:
                return IdentificationMessage.deserialize(serialized);
            case Location_Message:
                return LocationMessage.deserialize(serialized);
        }
        return null;
    }
}
