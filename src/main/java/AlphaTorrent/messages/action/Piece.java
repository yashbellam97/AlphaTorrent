package AlphaTorrent.messages.action;

import AlphaTorrent.app.InitializeHost;
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;

public class Piece implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage) {
        Host host = InitializeHost.host;
        Neighbour neighbour = host.getNeighbours().get(actualMessage.getSenderId());
        host.getChunks().put(actualMessage.getLength(), actualMessage.getPayload());
        neighbour.setPieceReceivedInLastInterval(neighbour.getPieceReceivedInLastInterval()+1);
    }
}
