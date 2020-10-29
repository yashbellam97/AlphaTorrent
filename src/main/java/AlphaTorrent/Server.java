package AlphaTorrent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    int port;
    String fileName;

    public Server(int port, String fileName) {
        this.port = port;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        Socket socket = null;
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (true) {
                System.out.println("Waiting...");
                try {
                    socket = serverSocket.accept();
                    System.out.println("Accepted connection at socket " + socket + "...");
                    System.out.println("Preparing to send file...");
                    File file = new File(this.fileName);
                    byte[] fileBytes = new byte[(int) file.length()];
                    fileInputStream = new FileInputStream(file);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    bufferedInputStream.read(fileBytes, 0, fileBytes.length);
                    outputStream = socket.getOutputStream();
                    System.out.println("Sending file...");
                    outputStream.write(fileBytes, 0, fileBytes.length);
                    outputStream.flush();
                    System.out.println("Done!");
                } finally {
                    if (bufferedInputStream != null) bufferedInputStream.close();
                    if (outputStream != null) outputStream.close();
                    if (socket != null) socket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred in the server!");
            e.printStackTrace();
        }
    }
}
