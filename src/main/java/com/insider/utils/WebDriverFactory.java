package com.insider.utils;

import com.insider.exceptions.WebDriverFactoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Factory class for WebDriver management
 */
public class WebDriverFactory {
    
    // Private constructor to prevent instantiation
    private WebDriverFactory() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    private static final Logger logger = LogManager.getLogger(WebDriverFactory.class);
    private static WebDriver driver;
    private static WebDriverWait wait;

    /**
     * Initialize WebDriver instance
     */
    public static void initializeDriver() {
        ConfigManager config = ConfigManager.getInstance();
        
        try {
            if (config.getBrowser().equalsIgnoreCase("chrome")) {
                setupChromeDriver();
            } else {
                throw new IllegalArgumentException("Unsupported browser: %s".formatted(config.getBrowser()));
            }
            
            configureDriver();
            logger.info("WebDriver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver", e);
            throw new WebDriverFactoryException("WebDriver initialization failed", "INITIALIZATION", config.getBrowser(), e);
        }
    }

    /**
     * Setup Chrome WebDriver
     */
    private static void setupChromeDriver() {
        // Selenium 4+ otomatik driver yönetimi kullanılıyor

        ChromeOptions options = new ChromeOptions();
        ConfigManager config = ConfigManager.getInstance();
        
        // Add headless option if configured
        if (config.isHeadless()) {
            options.addArguments("--headless");
            logger.info("Running in headless mode");
        }
        
        // Add common Chrome options
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=%s".formatted(config.getWindowSize()));
        options.addArguments("--remote-allow-origins=*");
        
        // Disable infobars
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        
        driver = new ChromeDriver(options);
        logger.info("Chrome WebDriver created with Selenium 4+ automatic driver management");
    }

    /**
     * Configure WebDriver with timeouts and other settings
     */
    private static void configureDriver() {
        ConfigManager config = ConfigManager.getInstance();
        
        // Set timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(config.getExplicitWait()));
        
        // Maximize window
        driver.manage().window().maximize();
        
        // Initialize WebDriverWait
        wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
        
        logger.info("WebDriver configured with timeouts");
    }

    /**
     * Get current WebDriver instance
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver not initialized. Call initializeDriver() first.");
        }
        return driver;
    }

    /**
     * Get WebDriverWait instance
     * @return WebDriverWait instance
     */
    public static WebDriverWait getWait() {
        if (wait == null) {
            throw new IllegalStateException("WebDriverWait not initialized. Call initializeDriver() first.");
        }
        return wait;
    }

    /**
     * Quit WebDriver and clean up resources
     */
    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
                logger.info("WebDriver quit successfully");
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver", e);
            } finally {
                driver = null;
                wait = null;
            }
        }
    }

    /**
     * Navigate to URL
     * @param url URL to navigate to
     */
    public static void navigateTo(String url) {
        if (driver == null) {
            throw new IllegalStateException("WebDriver not initialized");
        }
        try {
            driver.get(url);
            logger.info("Navigated to: {}", url);
        } catch (Exception e) {
            logger.error("Failed to navigate to: {}", url, e);
            throw new WebDriverFactoryException("Navigation failed", "NAVIGATION", null, e);
        }
    }

    /**
     * Get current page title
     * @return page title
     */
    public static String getPageTitle() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver not initialized");
        }
        return driver.getTitle();
    }

    /**
     * Get current URL
     * @return current URL
     */
    public static String getCurrentUrl() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver not initialized");
        }
        return driver.getCurrentUrl();
    }
}