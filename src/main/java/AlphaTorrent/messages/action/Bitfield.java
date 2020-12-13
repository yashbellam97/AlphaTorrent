package AlphaTorrent.messages.action;

import AlphaTorrent.app.InitializeHost;
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.messages.dto.MessageType;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.tcp.Sender;
import AlphaTorrent.utility.ByteArrayExt;
import AlphaTorrent.utility.Logger;

import java.util.Set;

public class Bitfield implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage) {
        Host host = InitializeHost.host;
        Neighbour neighbour = host.getNeighbours()
        .stream()
        .filter(neigh -> actualMessage.getSenderId() == neigh.getId())
        .findFirst()
        .get();
        neighbour.setBitfield(actualMessage.getPayload());
        Logger.write("Received bitfield message from neighbour: " + neighbour.getId(), host.getId());
        Set<Integer> missingChunks = host.getMissingChunks();
        boolean interesting = missingChunks.stream().anyMatch(e ->  ByteArrayExt.getBit(actualMessage.getPayload(),e));
        ActualMessage message = new ActualMessage();
        if (interesting)
            message.setType(MessageType.INTERESTED);
        else
            message.setType(MessageType.NOTINTERESTED);
        message.setSenderId(host.getId());
        Sender.send(neighbour.getHost(), neighbour.getPort(), message);
    }
}