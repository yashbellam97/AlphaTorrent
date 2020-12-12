package AlphaTorrent.tcp;

// A Java program for a Client
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.messages.dto.MessageType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.*;
import java.io.*;

public class Sender {

    public static void send(final String host, final int port, final byte[] message) throws IOException {
        Socket s;
        DataOutputStream dos = null;
        try {
            s = new Socket(host, port);
            dos = new DataOutputStream(s.getOutputStream());
            dos.write(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dos.close();
    }

    public static void send(final String host, final int port, final ActualMessage msg) {
        try {
            ObjectMapper ob = new ObjectMapper();
            ob.configure(
                    JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,
                    true
            );
            send(host, port, ob.writeValueAsBytes(msg));
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper ob = new ObjectMapper();
        ActualMessage msg = new ActualMessage();
        msg.setLength(56);
        msg.setType(MessageType.CHOKE);
        send("localhost", 1988, ob.writeValueAsBytes(msg));
        send("localhost", 1988, ob.writeValueAsBytes(msg));
    }
}
