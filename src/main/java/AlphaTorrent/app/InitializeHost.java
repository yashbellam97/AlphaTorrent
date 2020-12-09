package AlphaTorrent.app;

import AlphaTorrent.config.dto.PeerInfo;
import AlphaTorrent.config.loader.ConfigLoader;
import AlphaTorrent.neighbour.Neighbour;
import AlphaTorrent.state.Host;
import AlphaTorrent.utility.ChunksUtility;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InitializeHost {

    public static Host initializeHost() {
        int noOfChunks = ConfigLoader.getCommon().getFileSize()/ConfigLoader.getCommon().getPieceSize();
        if (ConfigLoader.getCommon().getFileSize()%ConfigLoader.getCommon().getPieceSize() !=0)
            noOfChunks++;
        String hostname = null;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
        }catch (Exception e) {

        }
//        final String hn = hostname;
        final String hn = "localhost3";
        PeerInfo peerInfo = ConfigLoader.getPeerList().stream().filter(e -> e.getHostName().equals(hn)).findFirst().get();
        Host host = new Host();
        host.setId(peerInfo.getPeerId());
        host.setPort(peerInfo.getPort());
        host.setHasFile(peerInfo.isHasFile());
        byte[] bitfield = new byte[noOfChunks/8+1];
        Map<Integer, byte[]> chunks = new HashMap<>();
        if (peerInfo.isHasFile()) {
            byte b = (byte) 0b11111111;
            Arrays.fill(bitfield, b);
            chunks = ChunksUtility.makeChunks(peerInfo.getPeerId());
        }
        host.setBitfield(bitfield);
        host.setChunks(chunks);
        List<Neighbour> neighbours = ConfigLoader.getPeerList()
                .stream()
                .filter(e -> !e.getHostName().equals(hn))
                .map(peer -> mapPeerToNeighbour(peer))
                .collect(Collectors.toList());
        host.setNeighbours(neighbours);
        return host;
    }

    private static Neighbour mapPeerToNeighbour(PeerInfo peerInfo) {
        Neighbour neighbour = new Neighbour();
        neighbour.setId(peerInfo.getPeerId());
        neighbour.setHost(peerInfo.getHostName());
        neighbour.setPort(peerInfo.getPort());
        neighbour.setHasFile(peerInfo.isHasFile());
        neighbour.setChoked(Boolean.TRUE);
        neighbour.setPieceReceivedInLastInterval(0);
        neighbour.setInterested(Boolean.FALSE);
        neighbour.setPeerInterested(Boolean.FALSE);
        neighbour.setChokedFromPeer(Boolean.TRUE);
        return neighbour;
    }
}
