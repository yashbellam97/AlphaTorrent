package AlphaTorrent.messages.action;

import AlphaTorrent.app.InitializeHost;
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.utility.ByteArrayExt;
import AlphaTorrent.utility.Logger;

public class Piece implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage) {
        Host host = InitializeHost.host;
        Neighbour neighbour = host.getNeighbours()
                .stream()
                .filter(neigh -> actualMessage.getSenderId() == neigh.getId())
                .findFirst()
                .get();
        host.getChunks().put(actualMessage.getLength(), actualMessage.getPayload());
        neighbour.setPieceReceivedInLastInterval(neighbour.getPieceReceivedInLastInterval()+1);
        host.getRequestedChunks().remove(actualMessage.getLength());
        ByteArrayExt.setBit(host.getBitfield(), actualMessage.getLength(), Boolean.TRUE);
        System.out.println("Piece received with id: "+actualMessage.getLength()+" from: "+ actualMessage.getSenderId());
        Logger.write("Piece received with id: "+actualMessage.getLength()+" from: "+ actualMessage.getSenderId(), host.getId());
    }
}
