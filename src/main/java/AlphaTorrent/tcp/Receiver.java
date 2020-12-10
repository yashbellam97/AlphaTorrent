package AlphaTorrent.tcp;

// A Java program for a Server
import AlphaTorrent.app.InitializeHost;
import AlphaTorrent.messages.action.Operation;
import AlphaTorrent.messages.action.OperationFactory;
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
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
                onMessage(clientSock);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onMessage(Socket clientSock) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        byte[] buffer = new byte[94096];
        dis.read(buffer);
        ObjectMapper ob = new ObjectMapper();
        ActualMessage msg = ob.readValue(buffer, ActualMessage.class);
        Operation op = OperationFactory.getOperation(msg.getType());
        op.onMessage(msg);
        dis.close();
    }

    public static void initiate() {
        int port = InitializeHost.host.getPort();
        Receiver fs = new Receiver(port);
        fs.start();

    }
}