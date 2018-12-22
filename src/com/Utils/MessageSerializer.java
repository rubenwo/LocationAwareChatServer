package com.Utils;

import com.Messages.*;

import java.io.IOException;

public class MessageSerializer {
    private static byte[] toByteArray(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
    }

    /**
     * @param message
     * @return
     */
    public static byte[] serialize(IMessage message) {
        String msg = message.serialize();
        System.out.println(msg);
        byte[] compressedData = null;

        try {
            compressedData = CompressionUtil.compress(msg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] prefix = toByteArray(compressedData.length);
        for (byte b : prefix) {
            System.out.println(b);

        }
        byte[] buffer = new byte[prefix.length + compressedData.length];
        System.arraycopy(prefix, 0, buffer, 0, prefix.length);
        System.arraycopy(compressedData, 0, buffer, prefix.length, compressedData.length);
        return buffer;
    }

    /**
     * @param serialized
     * @return
     */
    public static IMessage deserialize(String serialized) {
        System.out.println("SERIALIZED:" + serialized);

        if (!serialized.equals("")) {
            String[] elements = serialized.split(",");
            String messageType = "";
            for (String item : elements) {
                System.out.println(item);
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
                case Image_Message:
                    return ImageMessage.deserialize(serialized);
            }
        }
        return null;
    }
}
