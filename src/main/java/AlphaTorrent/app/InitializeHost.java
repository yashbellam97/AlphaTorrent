package AlphaTorrent.app;

import AlphaTorrent.config.dto.PeerInfo;
import AlphaTorrent.config.loader.ConfigLoader;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.utility.ChunksUtility;

import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;

public class InitializeHost {

    public static Host host;

    public static void initializeHost() {
        int noOfChunks = ConfigLoader.getCommon().getFileSize()/ConfigLoader.getCommon().getPieceSize();
        if (ConfigLoader.getCommon().getFileSize()%ConfigLoader.getCommon().getPieceSize() !=0)
            noOfChunks++;
        String hostname = null;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
        }catch (Exception e) {

        }
        final String hn = hostname;
//        final String hn = "lin114-05.cise.ufl.edu";
        PeerInfo peerInfo = ConfigLoader.getPeerList().stream().filter(e -> e.getHostName().equals(hn)).findFirst().get();
        host = new Host();
        host.setId(peerInfo.getPeerId());
        host.setPort(peerInfo.getPort());
        host.setHasFile(peerInfo.isHasFile());
        host.setNoOfChunks(noOfChunks);
        byte[] bitfield = new byte[noOfChunks/8+1];
        Map<Integer, byte[]> chunks = new HashMap<>();
        if (peerInfo.isHasFile()) {
            byte b = (byte) 0b11111111;
            Arrays.fill(bitfield, b);
            chunks = ChunksUtility.makeChunks(peerInfo.getPeerId());
        } else {
            host.setMissingChunks(getMissingChunks(host.getNoOfChunks()));
            host.setRequestedChunks(new HashSet<>());
        }
        host.setBitfield(bitfield);
        host.setChunks(chunks);
        List<Neighbour> neighbours = ConfigLoader.getPeerList()
                .stream()
                .filter(e -> !e.getHostName().equals(hn))
                .map(peer -> mapPeerToNeighbour(peer))
                .collect(Collectors.toList());
        host.setNeighbours(neighbours);
    }

    private static Neighbour mapPeerToNeighbour(PeerInfo peerInfo) {
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
        return neighbour;
    }

    private static Set<Integer> getMissingChunks(int size) {
        Set<Integer> set = new HashSet<>();
        for (int i = 1; i<=size; i++) {
            set.add(i);
        }
        return set;
    }
}
