package AlphaTorrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Configuration {
    int numberOfPreferredNeighbors;
    int unchokingInterval;
    int optimisticUnchokingInterval;
    String fileName;
    long fileSize;
    long pieceSize;

    private Configuration() {
        loadConfiguration();
    }

    private void loadConfiguration() {
        HashMap<String, String> configParams = new HashMap<>();
        try {
            File commonConfig = new File("src/main/java/AlphaTorrent/resources/Common.cfg");
            Scanner scanner = new Scanner(commonConfig);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] inputParams = data.split(" ");
                configParams.put(inputParams[0], inputParams[1]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not found!");
            e.printStackTrace();
        }

        this.numberOfPreferredNeighbors = Integer.parseInt(configParams.get("NumberOfPreferredNeighbors"));
        this.unchokingInterval = Integer.parseInt(configParams.get("UnchokingInterval"));
        this.optimisticUnchokingInterval = Integer.parseInt(configParams.get("OptimisticUnchokingInterval"));
        this.fileName = configParams.get("FileName");
        this.fileSize = Long.parseLong(configParams.get("FileSize"));
        this.pieceSize = Long.parseLong(configParams.get("PieceSize"));
    }

    public static Configuration getConfiguration() {
        return new Configuration();
    }
}