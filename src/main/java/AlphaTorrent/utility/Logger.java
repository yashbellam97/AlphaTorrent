package AlphaTorrent.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    public static void write(String details, int peerID) {
        File logFile;
        File logFileCopy;
        try {
            logFile = new File("resources/" + peerID +  "/log.txt");
            logFileCopy = new File("resources/log.txt");
            if (logFile.createNewFile()) {
                System.out.println("[" + new java.util.Date() + "] " + "Log file created!");
            }
            FileWriter logWriter = new FileWriter("resources/" + peerID + "/log.txt", true);
            FileWriter logWriterCopy = new FileWriter("resources/log.txt", true);
            if (details.startsWith("Chunks")) logWriterCopy.write(details + "\n");
            else logWriter.write("[" + new java.util.Date() + "] " + details + "\n");
            logWriter.close();
            logWriterCopy.close();
        } catch (IOException e) {
            System.out.println("Unable to write to log file!");
            e.printStackTrace();
        }
    }
}
