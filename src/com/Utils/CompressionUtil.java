package com.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

//TODO: Implement compression success/failed flag to byte[]
public class CompressionUtil {
    public static byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        System.out.println("Original: " + data.length + " bytes");
        System.out.println("Compressed: " + output.length + " bytes");
        return output;
    }

    public static String decompress(byte[] data) throws IOException, DataFormatException {
        String encodedString = java.util.Base64.getEncoder().encodeToString(data);
        byte[] output = Base64.getDecoder().decode(encodedString);

        Inflater inflater = new Inflater();
        inflater.setInput(output);

        byte[] result = encodedString.getBytes();
        int resultLength = inflater.inflate(result);
        inflater.end();

        System.out.println("Compressed: " + data.length + " bytes");
        System.out.println("Decompressed: " + resultLength + " bytes");
        return new String(result, 0, resultLength, "UTF-8");
    }

}
