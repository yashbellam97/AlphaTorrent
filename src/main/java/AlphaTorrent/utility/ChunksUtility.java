package AlphaTorrent.utility;

import AlphaTorrent.config.loader.ConfigLoader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ChunksUtility {

    public static Map<Integer,byte[]> makeChunks(int peerId) {
        Map<Integer,byte[]> chunks = new HashMap<>();
        try {
            byte[] allFileBytes = Files.readAllBytes(Paths.get("resources/"+peerId+"/thefile"));
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
            chunks.put(mapIndex++, chunk);
            Logger.write("Divided the file into " + mapIndex + " chunks...", peerId);
        } catch (IOException e) {
            // System.out.println("Unable to read file");
            Logger.write("Unable to read file", peerId);
            e.printStackTrace();
        }
        return chunks;
    }

    public static void generateFileFromBytes(Map<Integer, byte[]> bytes, int peerId) {
        // System.out.println("Generate file");
        Logger.write("Everything recieved. Generating file from bytes...", peerId);
        int size = 0;
        for (byte[] arr : bytes.values()) {
            size += arr.length;
        }

        byte[] fileBytes = new byte[size];
        int index = 0;
        for (byte[] arr : bytes.values()) {
            for (byte b : arr) {
                fileBytes[index++] = b;
            }
        }
        try (FileOutputStream fos = new FileOutputStream("resources/"+peerId+"/thefile")) {
            fos.write(fileBytes);
        } catch (IOException e) {
            // System.out.println("An error occurred while creating a file from the byte array!");
            Logger.write("An error occurred while creating a file from the byte array!", peerId);
            e.printStackTrace();
        }
    }
}
