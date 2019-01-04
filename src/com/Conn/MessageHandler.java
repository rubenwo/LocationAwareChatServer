package com.Conn;


import com.Entities.Audio;
import com.Entities.Image;
import com.Entities.User;
import com.Listeners.MessageCallback;
import com.MessagingProtocol.Messages.Replies.FriendReply;
import com.MessagingProtocol.Messages.Replies.FriendsReply;
import com.MessagingProtocol.Messages.Replies.UploadAudioMessageReply;
import com.MessagingProtocol.Messages.Replies.UploadImageReply;
import com.MessagingProtocol.Messages.Requests.*;
import com.MessagingProtocol.Messages.Updates.IdentificationMessage;
import com.MessagingProtocol.Messages.Updates.LocationUpdateMessage;
import com.MessagingProtocol.Messages.Updates.SignOutMessage;
import com.MessagingProtocol.Messages.Updates.TextMessage;
import com.Services.UserAuthenticationService;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class MessageHandler {
    /**
     *
     */
    private MessageCallback callback;

    /**
     * @param callback
     */
    public MessageHandler(MessageCallback callback) {
        this.callback = callback;
    }

    /**
     * @param message
     */
    public void handleIdentificationMessage(IdentificationMessage message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            if (authenticatedUser == null)
                callback.onAuthenticationFailed();
            else
                callback.onIdentificationMessage(authenticatedUser);
        });

    }

    /**
     * @param message
     */
    public void handleUploadImageRequestMessage(UploadImageRequest message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            if (authenticatedUser == null)
                callback.onAuthenticationFailed();
            else {
                byte[] imageData = Base64.getDecoder().decode(message.getBase64EncodedImage());
                callback.onUploadImageRequest(new Image(message.getImageName(), message.getImageExtension(), imageData), message.getTarget());
            }
        });
    }

    /**
     * @param message
     */
    public void handleLocationUpdateMessage(LocationUpdateMessage message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            if (authenticatedUser == null)
                callback.onAuthenticationFailed();
            else
                callback.onLocationUpdateMessage(message.getLocation());
        });
    }

    /**
     * @param message
     */
    public void handleTextMessage(TextMessage message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            if (authenticatedUser == null)
                callback.onAuthenticationFailed();
            else
                callback.onTextMessage(message.getTextMessage(), message.getTarget());
        });
    }

    /**
     * @param message
     */
    public void handleSignOutMessage(SignOutMessage message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            if (authenticatedUser == null)
                callback.onAuthenticationFailed();
            else
                callback.onSignOutMessage(message.isSignOut());
        });
    }

    /**
     * @param message
     */
    public void handleUploadAudioRequestMessage(UploadAudioMessageRequest message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            if (authenticatedUser == null)
                callback.onAuthenticationFailed();
            else {
                byte[] audioData = Base64.getDecoder().decode(message.getBase64EncodedAudio());
                callback.onUploadAudioRequest(new Audio(message.getAudioName(), message.getAudioExtension(), audioData), message.getTarget());
            }
        });
    }

    /**
     * @param message
     */
    public void handleFriendRequest(FriendRequest message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            if (authenticatedUser == null)
                callback.onAuthenticationFailed();
            else
                callback.onFriendRequest(message.getFriendEmail());
        });
    }

    /**
     * @param message
     */
    public void handleFriendsRequest(FriendsRequest message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            if (authenticatedUser == null)
                callback.onAuthenticationFailed();
            else
                callback.onFriendsRequest();
        });
    }

    /**
     * @param message
     */
    public void handleFriendReply(FriendReply message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            if (authenticatedUser == null)
                callback.onAuthenticationFailed();
            else
                callback.onFriendReply(message.getFriend(), message.isApproved());
        });
    }

    /**
     * @param message
     */
    public void handleFriendsReply(FriendsReply message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            if (authenticatedUser == null)
                callback.onAuthenticationFailed();
            else
                callback.onFriendsReply(message.getFriends());
        });
    }

    /**
     * @param message
     */
    public void handleUploadAudioMessageReply(UploadAudioMessageReply message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            if (authenticatedUser == null)
                callback.onAuthenticationFailed();
            else
                callback.onUploadAudioReply(message.getAudioFileUrl());
        });
    }

    /**
     * @param message
     */
    public void handleUploadImageReply(UploadImageReply message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            if (authenticatedUser == null)
                callback.onAuthenticationFailed();
            else
                callback.onUploadImageReply(message.getImageUrl());
        });
    }

    /**
     * @param message
     */
    public void handleEventCreationRequest(EventCreationRequest message) {
        CompletableFuture.runAsync(() -> {
            User authenticatedUser = UserAuthenticationService.authenticate(message.getFireBaseToken());
            if (authenticatedUser == null)
                callback.onAuthenticationFailed();
            else
                callback.onEventCreationRequest();
        });
    }

}
