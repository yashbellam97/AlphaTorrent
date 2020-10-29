package AlphaTorrent;

public class Configuration {
    int numberOfPreferredNeighbors;
    int unchokingInterval;
    int optimisticUnchokingInterval;
    String fileName;
    long fileSize;
    long pieceSize;

    public Configuration(String numberOfPreferredNeighbors, String unchokingInterval, String optimisticUnchokingInterval, String fileName, String fileSize, String pieceSize) {
        this.numberOfPreferredNeighbors = Integer.parseInt(numberOfPreferredNeighbors);
        this.unchokingInterval = Integer.parseInt(unchokingInterval);
        this.optimisticUnchokingInterval = Integer.parseInt(optimisticUnchokingInterval);
        this.fileName = fileName;
        this.fileSize = Long.parseLong(fileSize);
        this.pieceSize = Long.parseLong(pieceSize);
    }
}