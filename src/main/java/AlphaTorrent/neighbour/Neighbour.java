package AlphaTorrent.neighbour;

import lombok.Data;

@Data
public class Neighbour {
    private int id;
    private String host;
    private int port;
    private boolean hasFile;
    private byte[] bitfield;
    private boolean isInterested;
    private boolean isPeerInterested;
    private boolean isChoked;
    private boolean isChokedFromPeer;
    private Integer pieceReceivedInLastInterval = 0;
}