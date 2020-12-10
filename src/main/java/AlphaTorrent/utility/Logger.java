package AlphaTorrent.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    public static void write(String details) {
        File logFile;
        try {
            logFile = new File("src/main/java/AlphaTorrent/resources/log.txt");
            if (logFile.createNewFile()) {
                System.out.println("Log file created!");
            }
            FileWriter logWriter = new FileWriter("src/main/java/AlphaTorrent/resources/log.txt", true);
            logWriter.write(details + "\n");
            logWriter.close();
        } catch (IOException e) {
            System.out.println("Unable to write to log file!");
            e.printStackTrace();
        }
    }
}
