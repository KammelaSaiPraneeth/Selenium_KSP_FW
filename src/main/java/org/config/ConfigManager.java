package org.config;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties;
    private static ConfigManager instance;

    ConfigManager() {

        loadProperties();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties() {
        // Read ENV from Maven -Denv=qa  OR  system default
        String env = System.getProperty("env", "qa").toLowerCase();
        String filePath = "src/test/resources/config/" + env + ".properties";
        try {
            FileInputStream fis = new FileInputStream(filePath);
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Config file not found for env: " + env + " at path: " + filePath);
        }
    }

    public String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public String getEnvironment()
    {
        return System.getProperty("env");
    }

    // ================================================
    // DB CONFIG GETTERS
    // Grabbed from properties based on runtime env
    // ================================================
    public String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public String getDbUsername() {
        return properties.getProperty("db.username");
    }

    public String getDbPassword() {
        return properties.getProperty("db.password");
    }

    public String getDbDriver() {
        return properties.getProperty("db.driver");
    }
}


