package com.MessagingProtocol.Messages.Requests;


import com.Constants;
import com.Entities.User;
import com.MessagingProtocol.IMessage;
import com.MessagingProtocol.MessageType;

public class UploadAudioMessageRequest implements IMessage {
    private MessageType messageType = MessageType.UploadAudioRequest_Message;
    private String fireBaseToken;
    private String audioName;
    private String audioExtension;
    private String base64EncodedAudio;
    private User target;
    private User sender;

    public UploadAudioMessageRequest(String fireBaseToken, String audioName, String audioExtension, String base64EncodedAudio, User target, User sender) {
        this.fireBaseToken = fireBaseToken;
        this.audioName = audioName;
        this.audioExtension = audioExtension;
        this.base64EncodedAudio = base64EncodedAudio;
        this.target = target;
        this.sender = sender;
    }

    public static UploadAudioMessageRequest fromJson(String json) {
        return Constants.GSON.fromJson(json, UploadAudioMessageRequest.class);
    }

    @Override
    public User getSender() {
        return sender;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public String getAudioName() {
        return audioName;
    }

    public String getAudioExtension() {
        return audioExtension;
    }

    public String getBase64EncodedAudio() {
        return base64EncodedAudio;
    }

    public User getTarget() {
        return target;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
