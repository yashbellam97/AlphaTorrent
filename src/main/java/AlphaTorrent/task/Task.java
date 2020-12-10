package AlphaTorrent.task;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {
    private int peerId;
    private int chunkId;
}
