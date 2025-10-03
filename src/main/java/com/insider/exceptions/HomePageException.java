package com.insider.exceptions;

import lombok.Getter;

/**
 * Custom exception for home page operations and verification failures
 */
@Getter
public class HomePageException extends RuntimeException {
    
    private final String operation;
    private final String elementName;
    
    /**
     * Constructor for HomePageException
     * @param message Error message
     * @param operation Operation being performed when error occurred
     * @param elementName Name of the element or section where error occurred
     * @param cause Original exception that caused this error
     */
    public HomePageException(String message, String operation, String elementName, Throwable cause) {
        super(String.format("Home Page Operation Failed - Operation: %s, Element: %s, Error: %s",
                          operation, elementName, message), cause);
        this.operation = operation;
        this.elementName = elementName;
    }
}