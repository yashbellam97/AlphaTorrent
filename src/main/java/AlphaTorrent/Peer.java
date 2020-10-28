package AlphaTorrent;

public class Peer {
    int peerId;
    String hostName;
    int port;
    boolean hasFile;

    public Peer(String peerId, String hostName, String port, String hasFile) {
        this.peerId = Integer.parseInt(peerId);
        this.hostName = hostName;
        this.port = Integer.parseInt(port);
        this.hasFile = hasFile.equals("1");
    }
}
