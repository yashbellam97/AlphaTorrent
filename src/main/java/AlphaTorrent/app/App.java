/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package AlphaTorrent.app;

import AlphaTorrent.app.InitializeHost;
import AlphaTorrent.config.loader.ConfigLoader;
import AlphaTorrent.state.Host;
import AlphaTorrent.tcp.Receiver;
import AlphaTorrent.utility.ByteArrayExt;
import AlphaTorrent.utility.ChunksUtility;
import AlphaTorrent.utility.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello, world!");
        System.out.println("Hello, world!");
        InitializeHost.initializeHost();
        Receiver.initiate();
//        Thread.sleep(30000);
        Simulation.simulate();
    }
}
