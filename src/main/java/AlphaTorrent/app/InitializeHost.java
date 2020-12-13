package AlphaTorrent.app;

import AlphaTorrent.config.dto.PeerInfo;
import AlphaTorrent.config.loader.ConfigLoader;
import AlphaTorrent.messages.action.Handshake;
import AlphaTorrent.messages.dto.ActualMessage;
import AlphaTorrent.messages.dto.MessageType;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.tcp.Sender;
import AlphaTorrent.utility.ChunksUtility;
import AlphaTorrent.utility.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class InitializeHost {

    public static Host host;

    public static void initializeHost() {
        int noOfChunks = ConfigLoader.getCommon().getFileSize() / ConfigLoader.getCommon().getPieceSize();
        if (ConfigLoader.getCommon().getFileSize() % ConfigLoader.getCommon().getPieceSize() != 0)
            noOfChunks++;
        String hostname = null;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
        } catch (Exception e) {

        }
        final String hn = hostname + ".cise.ufl.edu";
        // final String hn = "lin114-05.cise.ufl.edu";
        // System.out.println(hn);
        PeerInfo peerInfo = ConfigLoader.getPeerList().stream().filter(e -> e.getHostName().equals(hn)).findFirst()
        .get();
        host = new Host();
        System.out.println("Hostname: " + hn);
        Logger.write("Hostname: " + hn, host.getId());
        host.setId(peerInfo.getPeerId());
        host.setPort(peerInfo.getPort());
        host.setHasFile(peerInfo.isHasFile());
        host.setNoOfChunks(noOfChunks);
        byte[] bitfield = new byte[noOfChunks / 8 + 1];
        Map<Integer, byte[]> chunks = new HashMap<>();
        if (peerInfo.isHasFile()) {
            byte b = (byte) 0b11111111;
            Arrays.fill(bitfield, b);
            chunks = ChunksUtility.makeChunks(peerInfo.getPeerId());
            host.setMissingChunks(new HashSet<>());
        } else {
            host.setMissingChunks(getMissingChunks(host.getNoOfChunks()));
            host.setRequestedChunks(new HashSet<>());
        }
        host.setBitfield(bitfield);
        host.setChunks(chunks);
        final int nChunks = noOfChunks;
        final ActualMessage handshakeMessage = getHandshakeMessage();
        final ActualMessage bitfieldMessage = getBitfiledMessage(bitfield);
        List<Neighbour> neighbours = ConfigLoader.getPeerList().stream().filter(e -> !e.getHostName().equals(hn))
                .map(peer -> mapPeerToNeighbour(peer, nChunks, handshakeMessage, bitfieldMessage)).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        host.setNeighbours(neighbours);
    }

    private static Neighbour mapPeerToNeighbour(PeerInfo peerInfo, Integer noOfChunks, ActualMessage handshakeMessage, ActualMessage bitfieldMessage) {
        Neighbour neighbour = new Neighbour();
        neighbour.setId(peerInfo.getPeerId());
        neighbour.setHost(peerInfo.getHostName());
        neighbour.setPort(peerInfo.getPort());
        neighbour.setHasFile(peerInfo.isHasFile());
        neighbour.setChoked(Boolean.FALSE);
        neighbour.setPieceReceivedInLastInterval(0);
        neighbour.setInterested(Boolean.FALSE);
        neighbour.setPeerInterested(Boolean.FALSE);
        neighbour.setChokedFromPeer(Boolean.TRUE);
        byte[] bitfield = new byte[noOfChunks / 8 + 1];
        if (peerInfo.isHasFile()) {
            byte b = (byte) 0b11111111;
            Arrays.fill(bitfield, b);
        }
        neighbour.setBitfield(bitfield);

        Sender.send(neighbour.getHost(),neighbour.getPort(),handshakeMessage);
        Sender.send(neighbour.getHost(),neighbour.getPort(),bitfieldMessage);

        return neighbour;
    }

    private static Set<Integer> getMissingChunks(int size) {
        Set<Integer> set = new HashSet<>();
        for (int i = 1; i <= size; i++) {
            set.add(i);
        }
        return set;
    }

    private static ActualMessage getHandshakeMessage() {
        ActualMessage message = new ActualMessage();
        message.setType(MessageType.HANDSHAKE);
        UUID uuid = UUID.randomUUID();
        return message;
    }

    private static ActualMessage getBitfiledMessage(byte[] bitfield) {
        ActualMessage message = new ActualMessage();
        message.setType(MessageType.BITFIELD);
        message.setPayload(bitfield);
        UUID uuid = UUID.randomUUID();
        return message;
    }
}
