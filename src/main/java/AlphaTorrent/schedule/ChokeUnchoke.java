package AlphaTorrent.schedule;

import AlphaTorrent.neighbour.Neighbour;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ChokeUnchoke {

    public static void chokeAndUnchoke(List<Neighbour> neighbours, Integer concurrentConnections) {
        Collections.sort(neighbours, new Comparator<Neighbour>() {
            @Override
            public int compare(Neighbour p1, Neighbour p2) {
                return p2.getPieceReceivedInLastInterval().compareTo(p1.getPieceReceivedInLastInterval());
            }
        });
        for(int i = 0; i< neighbours.size(); i++) {
            if (i<concurrentConnections) {
                neighbours.get(i).setChoked(Boolean.FALSE);
            }
            else {
                neighbours.get(i).setChoked(Boolean.TRUE);
            }
        }
        Random random = new Random();
        Integer  optimisticallyUnchokedNeighbor = random.nextInt(neighbours.size()-concurrentConnections);
        neighbours.get(concurrentConnections +optimisticallyUnchokedNeighbor -1).setChoked(Boolean.FALSE);

    }
}
