package AlphaTorrent.neighbour;

import lombok.Data;

@Data
public class Neigbour {
    private String id;
    private String host;
    private String port;
    private boolean hasFile;
    private byte[] bitfield;
    private boolean isInterested;
    private boolean isPeerInterested;
    private boolean isChoked;
    private boolean isChokedFromPeer;
    private Integer pieceReceivedInLastInterval = 0;
}