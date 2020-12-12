package AlphaTorrent.config.loader;

import AlphaTorrent.config.dto.Common;
import AlphaTorrent.config.dto.PeerInfo;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
public class ConfigLoader {
    static List<PeerInfo> peerInfoList;
    static Common common;

    public static void loadConfig() {
        HashMap<String, String> configParams = new HashMap<>();
        try {
            File commonConfig = new File("resources/Common.cfg");
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

        common = new Common(
                Integer.parseInt(configParams.get("NumberOfPreferredNeighbors")),
                Integer.parseInt(configParams.get("UnchokingInterval")),
                Integer.parseInt(configParams.get("OptimisticUnchokingInterval")),
                configParams.get("FileName"),
                Integer.parseInt(configParams.get("FileSize")),
                Integer.parseInt(configParams.get("PieceSize"))
            );
    }

    private static void loadPeers() {
        peerInfoList = new ArrayList<>();
        try {
            File peerConfig = new File("resources/PeerInfo.cfg");
            Scanner scanner = new Scanner(peerConfig);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] inputParams = data.split(" ");
                peerInfoList.add(new PeerInfo(Integer.parseInt(inputParams[0]), inputParams[1], Integer.parseInt(inputParams[2]), "1".equals(inputParams[3])));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not found!");
            e.printStackTrace();
        }
    }

    public static Common getCommon() {
        if (common == null) loadConfig();
        return common;
    }

    public static List<PeerInfo> getPeerList() {
        if (peerInfoList == null) loadPeers();
        return peerInfoList;
    }
}
