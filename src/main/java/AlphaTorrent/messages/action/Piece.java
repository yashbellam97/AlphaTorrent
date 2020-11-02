package AlphaTorrent.messages.action;

import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neigbour;
import AlphaTorrent.state.Host;

public class Piece implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage, Neigbour neigbour, Host host) {
        neigbour.setPieceReceivedInLastInterval(neigbour.getPieceReceivedInLastInterval()+1);

    }
}
