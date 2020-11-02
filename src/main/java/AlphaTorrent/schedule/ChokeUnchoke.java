package AlphaTorrent.schedule;

import AlphaTorrent.neighbour.Neigbour;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ChokeUnchoke {

    public static void chokeAndUnchoke(List<Neigbour> neigbours, Integer concurrentConnections) {
        Collections.sort(neigbours, new Comparator<Neigbour>() {
            @Override
            public int compare(Neigbour p1, Neigbour p2) {
                return p2.getPieceReceivedInLastInterval().compareTo(p1.getPieceReceivedInLastInterval());
            }
        });
        for(int i = 0; i< neigbours.size(); i++) {
            if (i<concurrentConnections)
                neigbours.get(i).setChoked(Boolean.FALSE);
            else
                neigbours.get(i).setChoked(Boolean.TRUE);
        }
        Random random = new Random();
        Integer  optimisticallyUnchokedNeighbor = random.nextInt(neigbours.size()-concurrentConnections);
        neigbours.get(concurrentConnections +optimisticallyUnchokedNeighbor -1).setChoked(Boolean.FALSE);

    }
}
