package AlphaTorrent.messages.action;

import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.utility.Logger;

public interface Operation {

    void onMessage(ActualMessage actualMessage);
    // Logger.write("Received operation message", neighbour.getId());
}
