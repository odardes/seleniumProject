package com.insider.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for logging operations
 */
public class LoggerUtil {
    
    /**
     * Get logger for the specified class
     * @param clazz class for which to get logger
     * @return logger instance
     */
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
    
    /**
     * Log test start
     * @param logger logger instance
     * @param testName test name
     */
    public static void logTestStart(Logger logger, String testName) {
        logger.info("========== STARTING TEST: {} ==========", testName);
    }
    
    /**
     * Log test completion
     * @param logger logger instance
     * @param testName test name
     */
    public static void logTestEnd(Logger logger, String testName) {
        logger.info("========== COMPLETED TEST: {} ==========", testName);
    }
    
    /**
     * Log test step
     * @param logger logger instance
     * @param stepDescription step description
     */
    public static void logStep(Logger logger, String stepDescription) {
        logger.info("STEP: {}", stepDescription);
    }
    
    /**
     * Log assertion
     * @param logger logger instance
     * @param assertionDescription assertion description
     */
    public static void logAssertion(Logger logger, String assertionDescription) {
        logger.info("ASSERTION: {}", assertionDescription);
    }
    
    /**
     * Log error
     * @param logger logger instance
     * @param message error message
     * @param throwable exception
     */
    public static void logError(Logger logger, String message, Throwable throwable) {
        logger.error("ERROR: {}", message, throwable);
    }
    
    /**
     * Log warning
     * @param logger logger instance
     * @param message warning message
     */
    public static void logWarning(Logger logger, String message) {
        logger.warn("WARNING: {}", message);
    }
    
    /**
     * Log info
     * @param logger logger instance
     * @param message info message
     */
    public static void logInfo(Logger logger, String message) {
        logger.info("INFO: {}", message);
    }
    
    /**
     * Log debug
     * @param logger logger instance
     * @param message debug message
     */
    public static void logDebug(Logger logger, String message) {
        logger.debug("DEBUG: {}", message);
    }
}