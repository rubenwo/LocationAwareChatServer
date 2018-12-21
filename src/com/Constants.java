package com;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;

public class Constants {
    /**
     *
     */
    public static final int VERSION = 1;

    // Image server constants
    /**
     *
     */
    public static final String IMAGE_SERVER_HOSTNAME = "206.189.3.15";
    /**
     *
     */
    public static final int IMAGE_SERVER_PORT = 12345;

    // Chat server constants
    /**
     *
     */
    public static final String SERVER_IP_ADDRESS = "192.168.2.125";
    /**
     *
     */
    public static final int SERVER_PORT = 9000;
    /**
     *
     */
    public static final Gson GSON = new Gson();
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
}
