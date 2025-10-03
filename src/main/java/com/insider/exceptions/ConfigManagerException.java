package com.insider.exceptions;

import lombok.Getter;

/**
 * Custom exception for configuration management operations
 */
@Getter
public class ConfigManagerException extends RuntimeException {
    
    private final String operation;
    private final String configKey;
    
    /**
     * Constructor for ConfigManagerException
     * @param message Error message
     * @param operation Operation being performed when error occurred
     * @param configKey Configuration key if applicable
     * @param cause Original exception that caused this error
     */
    public ConfigManagerException(String message, String operation, String configKey, Throwable cause) {
        super(String.format("Configuration Manager Failed - Operation: %s, Key: %s, Error: %s",
                          operation, configKey != null ? configKey : "N/A", message), cause);
        this.operation = operation;
        this.configKey = configKey;
    }

    /**
     * Constructor for ConfigManagerException without cause
     * @param message Error message
     * @param operation Operation being performed when error occurred
     * @param configKey Configuration key if applicable
     */
    public ConfigManagerException(String message, String operation, String configKey) {
        this(message, operation, configKey, null);
    }
}