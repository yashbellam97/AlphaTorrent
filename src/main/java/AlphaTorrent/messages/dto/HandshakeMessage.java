package AlphaTorrent.messages.dto;

import lombok.Data;

@Data
public class HandshakeMessage {
    private String header;
    private int peerId;
}