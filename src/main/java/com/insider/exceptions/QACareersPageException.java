package com.insider.exceptions;

import lombok.Getter;

/**
 * Custom exception for QA careers page operations and verification failures
 */
@Getter
public class QACareersPageException extends RuntimeException {
    
    private final String operation;
    private final String elementName;
    
    /**
     * Constructor for QACareersPageException
     * @param message Error message
     * @param operation Operation being performed when error occurred
     * @param elementName Name of the element or section where error occurred
     * @param cause Original exception that caused this error
     */
    public QACareersPageException(String message, String operation, String elementName, Throwable cause) {
        super(String.format("QA Careers Page Operation Failed - Operation: %s, Element: %s, Error: %s",
                          operation, elementName, message), cause);
        this.operation = operation;
        this.elementName = elementName;
    }

    /**
     * Constructor for QACareersPageException without original cause
     * @param message Error message
     * @param operation Operation being performed when error occurred
     * @param elementName Name of the element or section where error occurred
     */
    public QACareersPageException(String message, String operation, String elementName) {
        this(message, operation, elementName, null);
    }

}