package AlphaTorrent.schedule;

import AlphaTorrent.app.InitializeHost;
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.messages.dto.MessageType;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.tcp.Sender;
import java.util.List;
import java.util.Random;

public class OptimisticallyUnchoke extends Thread{

    Integer interval;

    public OptimisticallyUnchoke (Integer interval) {
        this.interval = interval;
    }

    public void run() {
        while (true) {
            try {
                optimisticallyUnchoke();
                Thread.sleep(interval * 1000);
            } catch (Exception e) {

            }

        }
    }

    public static void optimisticallyUnchoke() {
        Host host = InitializeHost.host;
        List<Neighbour> neighbours = host.getNeighbours();
        Random random = new Random();
        Integer  optimisticallyUnchokedNeighbor = random.nextInt(neighbours.size());
        Neighbour neighbour = neighbours.get(optimisticallyUnchokedNeighbor -1);
        neighbour.setChoked(Boolean.FALSE);
        ActualMessage message = new ActualMessage();
        message.setType(MessageType.UNCHOKE);
        message.setSenderId(host.getId());
        Sender.send(neighbour.getHost(), neighbour.getPort(), message);
    }
}
