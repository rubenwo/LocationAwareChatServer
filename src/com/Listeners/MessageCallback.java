package com.Listeners;

import com.Entities.Image;
import com.Entities.Location;
import com.Entities.User;

public interface MessageCallback {
    void onIdentificationMessage(User authenticatedUser);

    void onImageMessage(Image image, User target);

    void onAudioMessage(String base64EncodedAudio, User target);

    void onLocationUpdateMessage(Location location);

    void onTextMessage(String textMessage, User target);

    void onSignOutMessage(boolean signOut);
}
