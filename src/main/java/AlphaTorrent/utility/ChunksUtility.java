package AlphaTorrent.utility;

import AlphaTorrent.Logger;
import AlphaTorrent.config.loader.ConfigLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ChunksUtility {

    public static Map<Integer,byte[]> makeChunks(int peerId) {
        Map<Integer,byte[]> chunks = new HashMap<>();
        try {
            byte[] allFileBytes = Files.readAllBytes(Paths.get("src/main/java/AlphaTorrent/resources/target.txt"));
            int chunkSize = ConfigLoader.getCommon().getPieceSize();
            int mapIndex = 1;
            int index = 0;
            byte[] chunk = new byte[chunkSize];
            for (byte b : allFileBytes) {
                if (index >= chunkSize) {
                    chunks.put(mapIndex++, chunk);
                    chunk = new byte[chunkSize];
                    index = 0;
                }
                chunk[index++] = b;
            }
            Logger.write("Divided the file into " + mapIndex + " chunks...");
        } catch (IOException e) {
            System.out.println("Unable to read file");
            e.printStackTrace();
        }
        return chunks;
    }
}
