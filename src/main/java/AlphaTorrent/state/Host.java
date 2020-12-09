package AlphaTorrent.state;

import AlphaTorrent.neighbour.Neighbour;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public class Host {
    private int id;
    private int port;
    private boolean hasFile;
    private byte[] bitfield;
    private List<Neighbour> neighbours;
    private Map<Integer, byte[]> chunks = new HashMap<>();

    public void addNeighbour(Neighbour neighbour) {
        if (neighbours == null)
            neighbours = new LinkedList<>();
        neighbours.add(neighbour);
    }

    public void updateFileMap(Integer index, byte[] piece) {
        chunks.put(index, piece);
    }

    public byte[] getPiece(Integer index) {
        return chunks.get(index);
    }
}