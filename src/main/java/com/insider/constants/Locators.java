package com.insider.constants;

/**
 * Constants class containing all element locators
 */
public final class Locators {
    
    // Private constructor to prevent instantiation
    private Locators() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    // HomePage Locators
    public static final String HOME_COMPANY_MENU = "//nav//a[contains(text(), 'Company')]";
    public static final String HOME_CAREERS_LINK = "//a[contains(text(), 'Careers')]";
    public static final String ACCEPT_COOKIES_BUTTON = "a#wt-cli-accept-all-btn";
    
    // CareersPage Locators
    public static final String CAREERS_LOCATIONS_SECTION = "#career-our-location";
    public static final String CAREERS_TEAMS_SECTION = "#career-find-our-calling";
    public static final String CAREERS_LIFE_AT_INSIDER_SECTION = "section.elementor-section[data-id='a8e7b90']";
    
    // QA Careers Page Locators
    public static final String QA_SEE_ALL_JOBS_BUTTON = "//a[contains(text(), 'See all QA jobs')]";
    
    // Job Listing Page Locators
    public static final String LOCATION_FILTER_DROPDOWN = "select[name*='location']";
    public static final String DEPARTMENT_FILTER_DROPDOWN = "select[name*='department']";
    public static final String LOCATION_FILTER_OPTION = "//option[contains(text(), '%s')]";
    public static final String DEPARTMENT_FILTER_OPTION = "//option[contains(text(), '%s')]";
    public static final String JOB_LIST_CONTAINER = "#jobs-list";
    public static final String JOB_CARD = "#jobs-list > div";
    public static final String JOB_POSITION = "p[class*='position']";
    public static final String JOB_DEPARTMENT = "span[class*='department']";
    public static final String JOB_LOCATION = ".position-location";
    public static final String VIEW_ROLE_BUTTON = "//a[text()='View Role']";
    
    // Lever Application Page Locators
    public static final String LEVER_APPLICATION_FORM = "//div[contains(@class, 'application')] | //form[contains(@class, 'application')]";
}