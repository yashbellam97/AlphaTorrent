package AlphaTorrent.messages.action;

import AlphaTorrent.messages.dto.MessageType;
import AlphaTorrent.task.Task;
import AlphaTorrent.task.TaskManager;
import AlphaTorrent.app.InitializeHost;
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.tcp.Sender;

public class Request implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage) {
        Host host = InitializeHost.host;
        Neighbour neighbour = host.getNeighbours()
                .stream()
                .filter(neigh -> actualMessage.getSenderId() == neigh.getId())
                .findFirst()
                .get();
        System.out.println("Request received with id: "+actualMessage.getLength()+" from: "+ actualMessage.getSenderId());
//        TaskManager.tasks.add(new Task(actualMessage.getSenderId(), actualMessage.getLength()));
//        Task task = tasks.remove();
        ActualMessage message = new ActualMessage();
        message.setType(MessageType.PIECE);
        message.setLength(actualMessage.getLength());
        message.setPayload(host.getChunks().get(actualMessage.getLength()));
        message.setSenderId(host.getId());
        Sender.send(neighbour.getHost(), neighbour.getPort(), message);
    }
}
