package com.insider.exceptions;

import lombok.Getter;

/**
 * Custom exception for careers page verification failures
 */
@Getter
public class CareersPageException extends RuntimeException {
    
    private final String pageSection;
    private final String verificationType;
    
    /**
     * Constructor for CareersPageException
     * @param message Error message
     * @param pageSection Section of the careers page where error occurred
     * @param verificationType Type of verification being performed
     * @param cause Original exception that caused this error
     */
    public CareersPageException(String message, String pageSection, String verificationType, Throwable cause) {
        super(String.format("Careers Page Verification Failed - Section: %s, Verification: %s, Error: %s",
                          pageSection, verificationType, message), cause);
        this.pageSection = pageSection;
        this.verificationType = verificationType;
    }
}