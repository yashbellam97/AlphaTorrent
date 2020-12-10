package AlphaTorrent.messages.action;

import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;

public interface Operation {

    void onMessage(ActualMessage actualMessage);
}
