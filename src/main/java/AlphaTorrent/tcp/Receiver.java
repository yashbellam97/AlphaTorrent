package AlphaTorrent.tcp;

// A Java program for a Server
import AlphaTorrent.messages.dto.ActualMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.*;
import java.io.*;

public class Receiver extends Thread {

    private ServerSocket ss;

    public Receiver(int port) {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                Socket clientSock = ss.accept();
                saveFile(clientSock);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(Socket clientSock) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        byte[] buffer = new byte[4096];

        int filesize = 10000232; // Send file size in separate msg
        int read = 0;
        int totalRead = 0;
        int remaining = filesize;
        dis.read(buffer);
        ObjectMapper ob = new ObjectMapper();
        ActualMessage msg = ob.readValue(buffer, ActualMessage.class);

        dis.close();
    }

    public static void main(String[] args) {
        Receiver fs = new Receiver(1988);
        fs.start();
    }
}