package me.akkad;

import me.akkad.config.Configuration;
import me.akkad.config.ConfigurationManager;
import me.akkad.core.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    private static final Logger log = LoggerFactory.getLogger((Main.class));
    //TODO : 1 -> Read configuration file /*done*/
    //       2 -> Open socket and listen to a port
    //       3 -> Handel multi thread for multiple requests
    //       4 -> Open and read files from the filesystem
    //       5 -> Write the proper responses
    public static void main(String[] args) {
        log.info("Server started ....");
        ConfigurationManager.getInstance().loadConfigFile("src/main/resources/config.json");
        Configuration config = ConfigurationManager.getInstance().getCurrentConfig();
        try {
            Listener listener = new Listener(config.getPort(), config.getWebroot());
            listener.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}