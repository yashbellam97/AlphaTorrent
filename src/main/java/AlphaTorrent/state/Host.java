package AlphaTorrent.state;

import AlphaTorrent.neighbour.Neigbour;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public class Host {
    private String id;
    private String port;
    private boolean hasFile;
    private byte[] bitfield;
    private List<Neigbour> neighbours;
    private Map<Integer, byte[]> fileMap = new HashMap<>();

    public void addNeighbour(Neigbour neigbour) {
        if (neighbours == null)
            neighbours = new LinkedList<>();
        neighbours.add(neigbour);
    }

    public void updateFileMap(Integer index, byte[] piece) {
        fileMap.put(index, piece);
    }

    public byte[] getPiece(Integer index) {
        return fileMap.get(index);
    }
}