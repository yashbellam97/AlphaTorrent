package AlphaTorrent.messages.action;

import AlphaTorrent.app.InitializeHost;
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;

public class NotInterested implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage) {
        Host host = InitializeHost.host;
        Neighbour neighbour = host.getNeighbours().get(actualMessage.getSenderId());
        neighbour.setPeerInterested(Boolean.FALSE);
    }
}
