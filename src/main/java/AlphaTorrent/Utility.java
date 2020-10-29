package AlphaTorrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Utility {
    static List<Peer> peerList;
    static Configuration configuration;

    public static void loadConfig() {
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

        configuration = new Configuration(
                configParams.get("NumberOfPreferredNeighbors"),
                configParams.get("UnchokingInterval"),
                configParams.get("OptimisticUnchokingInterval"),
                configParams.get("FileName"),
                configParams.get("FileSize"),
                configParams.get("PieceSize")
            );
    }

    private static void loadPeers() {
        peerList = new ArrayList<>();
        try {
            File peerConfig = new File("src/main/java/AlphaTorrent/resources/PeerInfo.cfg");
            Scanner scanner = new Scanner(peerConfig);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] inputParams = data.split(" ");
                peerList.add(new Peer(inputParams[0], inputParams[1], inputParams[2], inputParams[3]));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not found!");
            e.printStackTrace();
        }
    }

    public static Configuration getConfiguration() {
        if (configuration == null) loadConfig();
        return configuration;
    }

    public static List<Peer> getPeerList() {
        if (peerList == null) loadPeers();
        return peerList;
    }
}
