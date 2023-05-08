package me.akkad;

import me.akkad.config.Configuration;
import me.akkad.config.ConfigurationManager;

public class Main {

    //TODO : 1 -> Read configuration file /*done*/
    //       2 -> Open socket and listen to a port
    //       3 -> Handel multi thread for multiple requests
    //       4 -> Open and read files from the filesystem
    //       5 -> Write the proper responses
    public static void main(String[] args) {
        System.out.println("Server started ....");
        ConfigurationManager.getInstance().loadConfigFile("src/main/resources/config.json");
        Configuration config = ConfigurationManager.getInstance().getCurrentConfig();
        System.out.println("Port : "+config.getPort());
    }
}