package AlphaTorrent.messages.action;

import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;

public class Piece implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage, Neighbour neighbour, Host host) {
        neighbour.setPieceReceivedInLastInterval(neighbour.getPieceReceivedInLastInterval()+1);
//        host

    }
}
