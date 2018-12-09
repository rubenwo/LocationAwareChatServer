import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {
    public Main() throws IOException {
        Socket socket = new Socket("localhost", 12345);
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        for (int i = 0; i < 100; i++)
            outputStream.writeChars("Index = " + i + ";");
        outputStream.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
