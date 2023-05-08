package me.akkad.config;

import me.akkad.exception.HttpConfigurationException;
import me.akkad.util.Json;

import java.io.IOException;

public class ConfigurationManager {
    private static ConfigurationManager manager;
    private Configuration currentConfig;

    private ConfigurationManager() {}

    public static ConfigurationManager getInstance() {
        if (manager == null) {
            manager = new ConfigurationManager();
        }
        return manager;
    }

    public void loadConfigFile(String filePath) throws HttpConfigurationException  {
        try {
            currentConfig = Json.readFile(filePath, Configuration.class);
        } catch (IOException e) {
            throw new HttpConfigurationException("Error handling configuration file", e);
        }
    }

    public Configuration getCurrentConfig() {
        if (currentConfig == null) {
            throw new HttpConfigurationException("No current configuration was set.");
        }
        return currentConfig;
    }
}
