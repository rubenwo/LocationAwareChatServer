package com.MessagingProtocol.Messages.Replies;

import com.Constants;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class UploadAudioMessageReply implements IMessage {
    private MessageType messageType = MessageType.UploadAudioReply_Message;
    private String fireBaseToken;
    private String audioFileUrl;

    public UploadAudioMessageReply(String fireBaseToken, String audioFileUrl) {
        this.fireBaseToken = fireBaseToken;
        this.audioFileUrl = audioFileUrl;
    }

    public static UploadAudioMessageReply fromJson(String json) {
        return Constants.GSON.fromJson(json, UploadAudioMessageReply.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public String getAudioFileUrl() {
        return audioFileUrl;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
