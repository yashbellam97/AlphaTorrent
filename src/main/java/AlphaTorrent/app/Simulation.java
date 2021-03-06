package AlphaTorrent.app;

import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.messages.dto.MessageType;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.task.Task;
import AlphaTorrent.task.TaskManager;
import AlphaTorrent.tcp.Sender;
import AlphaTorrent.utility.ByteArrayExt;
import AlphaTorrent.utility.ChunksUtility;
import AlphaTorrent.utility.Logger;

import javax.swing.text.html.Option;
import java.util.*;

public class Simulation {

    public static void simulate() {
        Host host = InitializeHost.host;
        Logger.write("Starting simulation...", host.getId());
        Long st = System.currentTimeMillis();
        while (true) {
            if (!host.isHasFile() && !host.getMissingChunks().isEmpty()) {
                requestChunks(host);
            }
            //// sendChunks(host);
            // // System.out.println("Chunks" + host.getChunks().size());
            Logger.write("Chunks" + host.getChunks().size(), host.getId());
            if (!host.isHasFile() && host.getChunks().size() == host.getNoOfChunks()) {
                host.setHasFile(Boolean.TRUE);
                ChunksUtility.generateFileFromBytes(host.getChunks(), host.getId());

                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    //TODO: handle exception
                }

                for (Neighbour neighbour : host.getNeighbours()) {
                    ActualMessage message = new ActualMessage();

                    message.setType(MessageType.HAVE);
                    message.setSenderId(host.getId());

                    Sender.send(neighbour.getHost(), neighbour.getPort(), message);
                }
            }

            boolean everyNeighbourHas = true;

            for (Neighbour neighbour : host.getNeighbours()) {
                if (!neighbour.isHasFile()) everyNeighbourHas = false;               
            }

            if (host.isHasFile() && everyNeighbourHas) break;
        }
    }

    private static void requestChunks(Host host) {
        List<Neighbour> neighbours = host.getNeighbours();
        Iterator<Neighbour> it = neighbours.iterator();
        while (it.hasNext()) {
            Neighbour neighbour = it.next();

            if (!neighbour.isChoked()) {

                Optional<Integer> optionalChunk = host.getMissingChunks().stream()
                        .filter(chunkId -> ByteArrayExt.getBit(neighbour.getBitfield(), chunkId)).findAny();
                if (optionalChunk.isPresent()) {

                    Integer chunkId = optionalChunk.get();
                    host.getMissingChunks().remove(chunkId);
                    host.getRequestedChunks().add(chunkId);
                    ActualMessage message = new ActualMessage();

                    message.setType(MessageType.REQUEST);
                    message.setLength(chunkId);
                    message.setSenderId(host.getId());

                    Sender.send(neighbour.getHost(), neighbour.getPort(), message);
                    System.out
                            .println("Piece requested with id: " + message.getLength() + " from: " + neighbour.getId());
                    Logger.write("Piece requested with id: " + message.getLength() + " from: " + neighbour.getId(),
                            host.getId());
                }
            }
        }
    }

    // private static void sendChunks(Host host) {
    // Queue<Task> tasks = TaskManager.tasks;
    // if (!tasks.isEmpty())
    // // System.out.println("No of chunks with Manager:"+ tasks.size());
    // Logger.write("No of chunks with Manager:"+ tasks.size());
    //
    // while (!tasks.isEmpty()) {
    // Task task = tasks.remove();
    // ActualMessage message = new ActualMessage();
    // message.setType(MessageType.PIECE);
    // message.setLength(task.getChunkId());
    // message.setPayload(host.getChunks().get(task.getChunkId()));
    // message.setSenderId(host.getId());
    // Neighbour neighbour = host.getNeighbours().
    // stream()
    // .filter(neigh -> task.getPeerId() == neigh.getId())
    // .findFirst()
    // .get();
    // Sender.send(neighbour.getHost(), neighbour.getPort(), message);
    // // System.out.println("Simulation: Send chunks : Piece sent with id:
    // "+message.getLength()+" to: "+ neighbour.getId());
    // Logger.write("Simulation: Send chunks : Piece sent with id:
    // "+message.getLength()+" to: "+ neighbour.getId());
    // }
    // }

}
