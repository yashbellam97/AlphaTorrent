package AlphaTorrent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileHandler {
    static Map<Integer, byte[]> chunks;

    static {
        makeChunks();
    }

    private static void makeChunks() {
        try {
            chunks = new HashMap<>();
            byte[] allFileBytes = Files.readAllBytes(Paths.get("src/main/java/AlphaTorrent/resources/target.txt"));
            int chunkSize = (int) Utility.getConfiguration().pieceSize;
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
    }

    static byte[] getChunk(int index) {
        return chunks.get(index);
    }

    static boolean generateFileFromBytes(Map<Integer, byte[]> bytes, String filePath) {
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

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(fileBytes);
        } catch (IOException e) {
            System.out.println("An error occurred while creating a file from the byte array!");
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
