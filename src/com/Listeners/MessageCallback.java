package com.Listeners;

import com.Entities.*;
import com.MessagingProtocol.IMessage;

import java.util.ArrayList;

public interface MessageCallback {
    void onIdentificationMessage(User authenticatedUser, String fireBaseMessagingId);

    void onAuthenticationFailed();

    void onUploadImageRequest(Image image, User target);

    void onUploadAudioRequest(Audio audio, User target);


    void onLocationUpdateMessage(Location location);

    void onTextMessage(String textMessage, User target);

    void onSignOutMessage(boolean signOut);

    void onFriendRequest(String email);

    void onFriendsRequest();

    void onFriendReply(User friend, boolean approved);

    void onFriendsReply(ArrayList<User> users);

    void onUploadAudioReply(String url);

    void onUploadImageReply(String url);

    void onEventCreationRequest(Event event);

    void onEventChatMessage(String eventUID, User sender, IMessage... content);

    void onGetAllEventsRequest();

    void onEventSubscriptionRequest(String eventUID);

    void onUnsubscribeFromEventRequest(String eventUID);

    void onSyncMissedMessagesRequest();

    void onProfilePictureUpdate(String profilePictureURL);
}
