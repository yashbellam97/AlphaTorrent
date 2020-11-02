package AlphaTorrent.messages.action;

import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neigbour;
import AlphaTorrent.state.Host;

public class Have implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage, Neigbour neigbour, Host host) {

    }
}
