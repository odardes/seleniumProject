package com.insider.exceptions;

/**
 * Custom exception for element-related operations in web automation
 */
public class ElementException extends RuntimeException {
    
    private final String elementName;
    private final String operation;
    
    /**
     * Constructor for ElementException
     * @param message Error message
     * @param elementName Name of the element that caused the error
     * @param operation Operation being performed when error occurred
     * @param cause Original exception that caused this error
     */
    public ElementException(String message, String elementName, String operation, Throwable cause) {
        super(String.format("Element Operation Failed - Element: %s, Operation: %s, Error: %s", 
                          elementName, operation, message), cause);
        this.elementName = elementName;
        this.operation = operation;
    }
    
    /**
     * Constructor for ElementException without original cause
     * @param message Error message
     * @param elementName Name of the element that caused the error
     * @param operation Operation being performed when error occurred
     */
    public ElementException(String message, String elementName, String operation) {
        this(message, elementName, operation, null);
    }
    
    public String getElementName() {
        return elementName;
    }
    
    public String getOperation() {
        return operation;
    }
}