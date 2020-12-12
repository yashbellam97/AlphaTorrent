package AlphaTorrent.messages.action;

import AlphaTorrent.app.InitializeHost;
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.messages.dto.MessageType;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.tcp.Sender;
import AlphaTorrent.utility.ByteArrayExt;
import AlphaTorrent.utility.Logger;

public class Have implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage) {
        Host host = InitializeHost.host;
        Neighbour neighbour = host.getNeighbours()
                .stream()
                .filter(neigh -> actualMessage.getSenderId() == neigh.getId())
                .findFirst()
                .get();

        boolean interesting = host.getMissingChunks().contains(actualMessage.getLength());
        ActualMessage message = new ActualMessage();
        if (interesting)
            message.setType(MessageType.INTERESTED);
        else
            message.setType(MessageType.NOTINTERESTED);
        message.setSenderId(host.getId());
        Sender.send(neighbour.getHost(), neighbour.getPort(), message);
        //do nothing
        // Logger.write("Received bitfield message", neighbour.getId());
    }
}
