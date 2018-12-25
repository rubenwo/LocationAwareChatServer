package com.Utils;

import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;
import com.MessagingProtocol.Messages.*;
import org.json.JSONObject;

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
        String msg = message.toJson();
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
     * @param json
     * @return
     */
    public static IMessage deserialize(String json) {
        System.out.println("SERIALIZED:" + json);
        if (!json.isEmpty()) {
            JSONObject obj = new JSONObject(json);
            String messageType = obj.getString("messageType");

            System.out.println(messageType);
            MessageType type = MessageType.valueOf(messageType);
            switch (type) {
                case LocationUpdate_Message:
                    return LocationUpdateMessage.deserialize(json);
                case SignOut_Message:
                    return SignOutMessage.deserialize(json);
                case Audio_Message:
                    return AudioMessage.deserialize(json);
                case Text_Message:
                    return TextMessage.deserialize(json);
                case Identification_Message:
                    return IdentificationMessage.deserialize(json);
                case Image_Message:
                    return ImageMessage.deserialize(json);
            }
        }
        return null;
    }
}
