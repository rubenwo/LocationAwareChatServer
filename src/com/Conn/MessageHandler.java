package com.Conn;


import com.Entities.Image;
import com.Entities.User;
import com.Listeners.MessageCallback;
import com.MessagingProtocol.Messages.*;
import com.Services.UserAuthenticationService;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class MessageHandler {
    private MessageCallback callback;

    public MessageHandler(MessageCallback callback) {
        this.callback = callback;
    }

    public void handleIdentificationMessage(IdentificationMessage message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            callback.onIdentificationMessage(authenticatedUser);
        });

    }

    public void handleImageMessage(ImageMessage message) {
        CompletableFuture.runAsync(() -> {
            byte[] imageData = Base64.getDecoder().decode(message.getBase64EncodedImage());
            callback.onImageMessage(new Image(message.getImageName(), message.getImageExtension(), imageData), message.getTarget());
        });
    }

    public void handleLocationUpdateMessage(LocationUpdateMessage message) {
        CompletableFuture.runAsync(() -> {
            callback.onLocationUpdateMessage(message.getLocation());
        });
    }

    public void handleTextMessage(TextMessage message) {
        CompletableFuture.runAsync(() -> {
            callback.onTextMessage(message.getTextMessage(), message.getTarget());
        });
    }

    public void handleSignOutMessage(SignOutMessage message) {
        CompletableFuture.runAsync(() -> {
            callback.onSignOutMessage(message.isSignOut());
        });
    }

    public void handleAudioMessage(AudioMessage message) {
        CompletableFuture.runAsync(() -> {
            callback.onAudioMessage(message.getBase64EncodedAudio(), message.getTarget());
        });
    }
}
