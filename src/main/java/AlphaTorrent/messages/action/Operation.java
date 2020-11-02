package AlphaTorrent.messages.action;

import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neigbour;
import AlphaTorrent.state.Host;

public interface Operation {

    void onMessage(ActualMessage actualMessage, Neigbour neigbour, Host host);
}
