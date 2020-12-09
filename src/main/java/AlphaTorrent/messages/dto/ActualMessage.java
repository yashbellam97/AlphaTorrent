package AlphaTorrent.messages.dto;

import lombok.Data;

@Data
public class ActualMessage {
    private int senderId;
    private int length;
    private MessageType type;
    private byte[] payload;

}