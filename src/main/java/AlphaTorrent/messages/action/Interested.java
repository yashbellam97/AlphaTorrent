package AlphaTorrent.messages.action;

import AlphaTorrent.app.InitializeHost;
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.utility.Logger;

public class Interested implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage) {
        Host host = InitializeHost.host;
        Neighbour neighbour = host.getNeighbours()
                .stream()
                .filter(neigh -> actualMessage.getSenderId() == neigh.getId())
                .findFirst()
                .get();
        neighbour.setPeerInterested(Boolean.TRUE);
        Logger.write("Received interested message from neighbour: " + neighbour.getId(), host.getId());

    }
}
