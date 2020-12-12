package AlphaTorrent.messages.action;

import AlphaTorrent.task.Task;
import AlphaTorrent.task.TaskManager;
import AlphaTorrent.app.InitializeHost;
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;

public class Request implements Operation{
    @Override
    public void onMessage(ActualMessage actualMessage) {
        Host host = InitializeHost.host;
        Neighbour neighbour = host.getNeighbours().get(actualMessage.getSenderId());
        System.out.println("Request received with id: "+actualMessage.getLength()+" from: "+ actualMessage.getSenderId());
        TaskManager.tasks.add(new Task(actualMessage.getSenderId(), actualMessage.getLength()));
    }
}
