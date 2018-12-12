package com.Utils;

import java.util.Base64;

public class ImageUtil {

    public static byte[] toImage(String base64Str) throws IllegalArgumentException {
        return Base64.getDecoder().decode(base64Str.getBytes());
    }

    public static String toBaste64String(byte[] image) {

        return Base64.getEncoder().encodeToString(image);
    }
}
