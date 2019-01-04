package com.Listeners;

import com.Entities.Audio;
import com.Entities.Image;
import com.Entities.Location;
import com.Entities.User;

import java.util.ArrayList;

public interface MessageCallback {
    void onIdentificationMessage(User authenticatedUser);

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

    void onEventCreationRequest();
}
