package AlphaTorrent.messages.action;

import AlphaTorrent.app.InitializeHost;
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.utility.Logger;

public class Choke implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage) {
        Host host = InitializeHost.host;
        Neighbour neighbour = host.getNeighbours()
                .stream()
                .filter(neigh -> actualMessage.getSenderId() == neigh.getId())
                .findFirst()
                .get();
        neighbour.setChokedFromPeer(Boolean.TRUE);
        Logger.write("Received choke message from neighbour: " + neighbour.getId(), host.getId());
    }
}
