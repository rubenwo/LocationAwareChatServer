package com;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    private static ArrayList<String> strings;
    private static ArrayList strings2;

    public Main() throws IOException {
        Socket socket = new Socket(Constants.IMAGE_SERVER_HOSTNAME, Constants.IMAGE_SERVER_PORT);
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        for (int i = 0; i < 100; i++)
            outputStream.writeChars("Index = " + i + ";");
        //  while (true) ;
        outputStream.close();
        socket.close();
    }

    public static void main(String[] args) {
     /*   try {
            new Main();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String hello = "Lorem ipsum dolor sit amet consectetur adipiscing elit sagittis torquent primis, dignissim mollis scelerisque natoque fermentum magna fames conubia facilisis, phasellus etiam pretium luctus feugiat id nibh integer iaculis. Pellentesque rutrum scelerisque habitasse sollicitudin primis taciti netus nullam egestas, purus urna posuere sapien eget ultrices congue fringilla condimentum ligula, curae tincidunt feugiat mauris hendrerit ad tempus praesent. Rutrum aptent integer libero dictumst faucibus, semper consequat rhoncus purus porta vehicula, ut cubilia eros ultricies.\n" +
                    "\n" +
                    "Scelerisque tortor fermentum arcu id himenaeos facilisi ridiculus vel tristique class tempor, elementum accumsan curabitur montes augue dapibus phasellus nisl erat ut. At quisque molestie rutrum netus nullam volutpat pulvinar bibendum, sociis senectus ut magnis tellus habitant pharetra vehicula, cursus malesuada eget mi cras metus montes. Fusce lectus tortor pharetra arcu ultrices leo ante parturient lacus volutpat, mollis eget natoque est orci aptent neque felis habitasse turpis, imperdiet nisl scelerisque posuere nibh varius porttitor metus curae.";
            System.out.println(hello);
            byte[] data = hello.getBytes();
            String helloDat = new String(data);
            System.out.println(helloDat);
            byte[] compressed = CompressionUtil.compress(data);
            String helloCom = new String(compressed);
            System.out.println(helloCom);
            byte[] decompressed = CompressionUtil.decompress(compressed);
            String helloDecom = new String(decompressed);
            System.out.println(helloDecom);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }*/
        listTest();
    }

    private static void listTest() {
        strings = new ArrayList<>();
        strings2 = strings;
        strings2.add("Hello World");
        System.out.println("Printing strings");
        printArrayList(strings);
        System.out.println("Printing strings2");
        printArrayList(strings2);
    }

    private static void printArrayList(ArrayList<String> list) {
        for (String str : list) {
            System.out.println(str);
        }
    }
}
