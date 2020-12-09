package AlphaTorrent.messages.action;

import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;

public class Interested implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage, Neighbour neighbour, Host host) {
        neighbour.setPeerInterested(Boolean.TRUE);
    }
}
