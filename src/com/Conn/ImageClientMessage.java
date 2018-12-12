package com.Conn;

import com.Constants;

public class ImageClientMessage {
    private String imageID;
    private String data;

    public ImageClientMessage(String imageID, String data) {
        this.imageID = imageID;
        this.data = data;
    }

    public String serialize() {
        return Constants.GSON.toJson(this);
    }
}
