package com.insider.utils;

import com.insider.exceptions.ConfigManagerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration manager to handle application properties
 */
public class ConfigManager {
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);

    /**
     * Initialization-on-demand holder idiom for thread-safe lazy initialization
     */
    private static class ConfigManagerHolder {
        private static final ConfigManager INSTANCE = new ConfigManager();
    }

    private final Properties properties;

    private ConfigManager() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.error("Unable to find config.properties");
                throw new ConfigManagerException("config.properties file not found", "LOAD_CONFIG", "config.properties");
            }
            properties.load(input);
            logger.info("Configuration loaded successfully");
        } catch (IOException ex) {
            logger.error("Error loading configuration", ex);
            throw new ConfigManagerException("Failed to load configuration", "LOAD_CONFIG", "config.properties", ex);
        }
    }

    /**
     * Get singleton instance of ConfigManager
     * @return ConfigManager instance
     */
    public static ConfigManager getInstance() {
        return ConfigManagerHolder.INSTANCE;
    }

    /**
     * Get property value by key
     * @param key property key
     * @return property value
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value by key with default value
     * @param key property key
     * @param defaultValue default value if key not found
     * @return property value or default value
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get integer property value
     * @param key property key
     * @return integer value
     */
    public int getIntProperty(String key) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException e) {
            logger.error("Invalid integer value for key: {}", key, e);
            throw new ConfigManagerException("Invalid integer value for key: %s".formatted(key), "GET_INT_PROPERTY", key, e);
        }
    }

    /**
     * Get boolean property value
     * @param key property key
     * @return boolean value
     */
    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    /**
     * Get base URL
     * @return base URL
     */
    public String getBaseUrl() {
        return getProperty("base.url");
    }

    /**
     * Get careers URL
     * @return careers URL
     */
    public String getCareersUrl() {
        return getProperty("careers.url");
    }

    /**
     * Get QA careers URL
     * @return QA careers URL
     */
    public String getQaCareersUrl() {
        return getProperty("qa.careers.url");
    }

    /**
     * Get location filter value
     * @return location filter
     */
    public String getLocationFilter() {
        return getProperty("location.filter");
    }

    /**
     * Get department filter value
     * @return department filter
     */
    public String getDepartmentFilter() {
        return getProperty("department.filter");
    }

    /**
     * Get expected position text
     * @return expected position text
     */
    public String getExpectedPositionText() {
        return getProperty("expected.position.text");
    }

    /**
     * Get expected department text
     * @return expected department text
     */
    public String getExpectedDepartmentText() {
        return getProperty("expected.department.text");
    }

    /**
     * Get expected location text
     * @return expected location text
     */
    public String getExpectedLocationText() {
        return getProperty("expected.location.text");
    }

    /**
     * Get implicit wait time
     * @return implicit wait time in seconds
     */
    public int getImplicitWait() {
        return getIntProperty("implicit.wait");
    }

    /**
     * Get explicit wait time
     * @return explicit wait time in seconds
     */
    public int getExplicitWait() {
        return getIntProperty("explicit.wait");
    }

    /**
     * Get page load timeout
     * @return page load timeout in seconds
     */
    public int getPageLoadTimeout() {
        return getIntProperty("page.load.timeout");
    }

    /**
     * Get browser name
     * @return browser name
     */
    public String getBrowser() {
        return getProperty("browser", "chrome");
    }

    /**
     * Check if headless mode is enabled
     * @return true if headless mode is enabled
     */
    public boolean isHeadless() {
        return getBooleanProperty("headless");
    }

    /**
     * Get window size
     * @return window size as string
     */
    public String getWindowSize() {
        return getProperty("window.size", "1920,1080");
    }
}