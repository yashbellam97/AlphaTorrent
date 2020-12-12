package AlphaTorrent.messages.action;

import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.utility.Logger;

public class Have implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage) {
        //do nothing
        // Logger.write("Received bitfield message", neighbour.getId());
    }
}
