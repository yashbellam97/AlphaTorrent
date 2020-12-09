package AlphaTorrent.config.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PeerInfo {
    int peerId;
    String hostName;
    int port;
    boolean hasFile;
}
