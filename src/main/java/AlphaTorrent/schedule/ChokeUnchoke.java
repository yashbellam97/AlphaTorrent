package AlphaTorrent.schedule;

import AlphaTorrent.app.InitializeHost;
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.messages.dto.MessageType;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.tcp.Sender;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.zip.Deflater;

public class ChokeUnchoke extends Thread{

    Integer interval;
    Integer concurrentConnections;

    public ChokeUnchoke (Integer interval, Integer concurrentConnections) {
        this.interval = interval;
        this.concurrentConnections = concurrentConnections;
    }

    public void run() {
        while (true) {
            chokeAndUnchoke(concurrentConnections);
            try {
                Thread.sleep(interval * 1000);
            } catch (Exception e) {

            }

        }
    }

    public static void chokeAndUnchoke(Integer concurrentConnections) {
        Host host = InitializeHost.host;
        List<Neighbour> neighbours = host.getNeighbours();
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
        for (Neighbour neighbour: neighbours) {
            ActualMessage message = new ActualMessage();
            if (neighbour.isChoked())
                message.setType(MessageType.CHOKE);
            else
                message.setType(MessageType.UNCHOKE);
            message.setSenderId(host.getId());
            Sender.send(neighbour.getHost(), neighbour.getPort(), message);
        }

        Random random = new Random();
        Integer  optimisticallyUnchokedNeighbor = random.nextInt(neighbours.size()-concurrentConnections);
        neighbours.get(concurrentConnections +optimisticallyUnchokedNeighbor -1).setChoked(Boolean.FALSE);

    }
}
