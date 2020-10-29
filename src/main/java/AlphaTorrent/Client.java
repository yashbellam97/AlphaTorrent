package AlphaTorrent;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client implements Runnable {
    public final static int FILE_SIZE = Integer.MAX_VALUE;
    String serverAddress;
    int serverPort;
    String fileName;

    public Client(String serverAddress, int serverPort, String fileName) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        int bytesRead = 0;
        int currentBytes = 0;

        try (Socket socket = new Socket(serverAddress, serverPort)) {
            System.out.println("Connecting...");
            try {
                byte[] fileBytes = new byte[FILE_SIZE];
                InputStream inputStream = socket.getInputStream();
                fileOutputStream = new FileOutputStream(fileName);
                bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                bytesRead = inputStream.read(fileBytes, 0, fileBytes.length);
                currentBytes = bytesRead;

                do {
                    bytesRead = inputStream.read(fileBytes, currentBytes, (fileBytes.length - currentBytes));
                    if (bytesRead >= 0) currentBytes += bytesRead;
                } while (bytesRead > -1);

                bufferedOutputStream.write(fileBytes, 0, currentBytes);
                bufferedOutputStream.flush();
                System.out.println("Done!");
            } finally {
                if (fileOutputStream != null) fileOutputStream.close();
                if (bufferedOutputStream != null) bufferedOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
