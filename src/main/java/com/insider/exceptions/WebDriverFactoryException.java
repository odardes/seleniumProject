package com.insider.exceptions;

import lombok.Getter;

/**
 * Custom exception for WebDriver factory operations
 */
@Getter
public class WebDriverFactoryException extends RuntimeException {
    
    private final String operation;
    private final String browser;
    
    /**
     * Constructor for WebDriverFactoryException
     * @param message Error message
     * @param operation Operation being performed when error occurred
     * @param browser Browser name if applicable
     * @param cause Original exception that caused this error
     */
    public WebDriverFactoryException(String message, String operation, String browser, Throwable cause) {
        super(String.format("WebDriver Factory Failed - Operation: %s, Browser: %s, Error: %s", operation, browser != null ? browser : "N/A", message), cause);
        this.operation = operation;
        this.browser = browser;
    }

}