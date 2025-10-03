package com.insider.pages;

import com.insider.constants.Locators;
import com.insider.utils.ConfigManager;
import com.insider.utils.LoggerUtil;
import com.insider.utils.WebDriverFactory;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Page Object Model for Insider QA Careers Page
 */
public class QACareersPage extends BasePage {
    private static final Logger logger = LoggerUtil.getLogger(QACareersPage.class);
    private final ConfigManager config = ConfigManager.getInstance();

    // Locators specific to QACareersPage
    private final By seeAllQaJobsButtonLocator = By.xpath(Locators.QA_SEE_ALL_JOBS_BUTTON);
    private final By jobListContainerLocator = By.xpath(Locators.JOB_LIST_CONTAINER);
    private final By jobCardLocator = By.xpath(Locators.JOB_CARD);
    private final By jobPositionLocator = By.xpath(Locators.JOB_POSITION);
    private final By jobDepartmentLocator = By.xpath(Locators.JOB_DEPARTMENT);
    private final By jobLocationLocator = By.xpath(Locators.JOB_LOCATION);
    private final By viewRoleButtonLocator = By.xpath(Locators.VIEW_ROLE_BUTTON);

    // Filter locators (more specific for job listings)
    private final By locationFilterDropdownLocator = By.xpath("//select[contains(@name, 'location')] | //div[contains(@class, 'location-filter')]//button");
    private final By departmentFilterDropdownLocator = By.xpath("//select[contains(@name, 'department')] | //div[contains(@class, 'department-filter')]//button");

    /**
     * Navigate to QA careers page
     */
    public void navigateToQACareersPage() {
        try {
            WebDriverFactory.navigateTo(config.getQaCareersUrl());
            waitForPageLoad();
            LoggerUtil.logInfo(logger, "Navigated to QA careers page: " + config.getQaCareersUrl());
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to navigate to QA careers page", e);
            takeScreenshot("qa_careers_navigation_error");
            throw new RuntimeException("Failed to navigate to QA careers page", e);
        }
    }

    /**
     * Click "See all QA jobs" button
     */
    public void clickSeeAllQaJobs() {
        try {
            scrollToElement(seeAllQaJobsButtonLocator, "See All QA Jobs Button");
            clickElement(seeAllQaJobsButtonLocator, "See All QA Jobs Button");
            waitForPageLoad();
            LoggerUtil.logInfo(logger, "Clicked on 'See all QA jobs' button");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to click 'See all QA jobs' button", e);
            takeScreenshot("see_all_qa_jobs_error");
            throw new RuntimeException("Failed to click 'See all QA jobs' button", e);
        }
    }

    /**
     * Filter jobs by location
     * @param location location to filter by
     */
    public void filterByLocation(String location) {
        try {
            // Try different approaches for location filter
            boolean filterApplied = false;
            
            // Try dropdown approach first
            try {
                WebElement locationDropdown = waitForElementVisible(locationFilterDropdownLocator, "Location Filter Dropdown");
                if (locationDropdown.getTagName().equals("select")) {
                    Select select = new Select(locationDropdown);
                    select.selectByVisibleText(location);
                    filterApplied = true;
                } else {
                    // For button-based filters
                    clickElement(locationFilterDropdownLocator, "Location Filter Dropdown");
                    // Wait for options to appear and click the desired one
                    By locationOptionLocator = By.xpath(String.format(Locators.LOCATION_FILTER_OPTION, location, location));
                    clickElement(locationOptionLocator, "Location Option: " + location);
                    filterApplied = true;
                }
            } catch (Exception e) {
                LoggerUtil.logWarning(logger, "Standard filter approach failed, trying alternative methods");
            }
            
            // Alternative approach: look for any clickable element with the location text
            if (!filterApplied) {
                By alternativeLocationLocator = By.xpath("//*[contains(text(), '" + location + "') and (self::a or self::button or self::option)]");
                clickElement(alternativeLocationLocator, "Alternative Location Filter: " + location);
            }
            
            // Wait for filter to apply
            Thread.sleep(2000);
            waitForLoadingToComplete();
            
            LoggerUtil.logInfo(logger, "Applied location filter: " + location);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to apply location filter: " + location, e);
            takeScreenshot("location_filter_error");
            throw new RuntimeException("Failed to apply location filter: " + location, e);
        }
    }

    /**
     * Filter jobs by department
     * @param department department to filter by
     */
    public void filterByDepartment(String department) {
        try {
            // Try different approaches for department filter
            boolean filterApplied = false;
            
            // Try dropdown approach first
            try {
                WebElement departmentDropdown = waitForElementVisible(departmentFilterDropdownLocator, "Department Filter Dropdown");
                if (departmentDropdown.getTagName().equals("select")) {
                    Select select = new Select(departmentDropdown);
                    select.selectByVisibleText(department);
                    filterApplied = true;
                } else {
                    // For button-based filters
                    clickElement(departmentFilterDropdownLocator, "Department Filter Dropdown");
                    // Wait for options to appear and click the desired one
                    By departmentOptionLocator = By.xpath(String.format(Locators.DEPARTMENT_FILTER_OPTION, department, department));
                    clickElement(departmentOptionLocator, "Department Option: " + department);
                    filterApplied = true;
                }
            } catch (Exception e) {
                LoggerUtil.logWarning(logger, "Standard department filter approach failed, trying alternative methods");
            }
            
            // Alternative approach: look for any clickable element with the department text
            if (!filterApplied) {
                By alternativeDepartmentLocator = By.xpath("//*[contains(text(), '" + department + "') and (self::a or self::button or self::option)]");
                clickElement(alternativeDepartmentLocator, "Alternative Department Filter: " + department);
            }
            
            // Wait for filter to apply
            Thread.sleep(2000);
            waitForLoadingToComplete();
            
            LoggerUtil.logInfo(logger, "Applied department filter: " + department);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to apply department filter: " + department, e);
            takeScreenshot("department_filter_error");
            throw new RuntimeException("Failed to apply department filter: " + department, e);
        }
    }

    /**
     * Apply both location and department filters
     */
    public void applyJobFilters() {
        try {
            LoggerUtil.logInfo(logger, "Applying job filters...");
            
            filterByLocation(config.getLocationFilter());
            filterByDepartment(config.getDepartmentFilter());
            
            LoggerUtil.logInfo(logger, "Applied both location and department filters successfully");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to apply job filters", e);
            takeScreenshot("job_filters_error");
            throw new RuntimeException("Failed to apply job filters", e);
        }
    }

    /**
     * Verify job list is displayed
     */
    public void verifyJobListDisplayed() {
        try {
            waitForLoadingToComplete();
            
            boolean isJobListDisplayed = isElementDisplayed(jobListContainerLocator, "Job List Container");
            Assert.assertTrue(isJobListDisplayed, "Job list is not displayed after applying filters");
            
            // Check if there are any job cards
            List<WebElement> jobCards = driver.findElements(jobCardLocator);
            Assert.assertFalse(jobCards.isEmpty(), "No job cards found in the job list");
            
            LoggerUtil.logAssertion(logger, "Job list is displayed with " + jobCards.size() + " job cards");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Job list verification failed", e);
            takeScreenshot("job_list_verification_error");
            throw new RuntimeException("Job list verification failed", e);
        }
    }

    /**
     * Get all job cards
     * @return list of job card elements
     */
    public List<WebElement> getAllJobCards() {
        try {
            waitForElementVisible(jobListContainerLocator, "Job List Container");
            List<WebElement> jobCards = driver.findElements(jobCardLocator);
            LoggerUtil.logInfo(logger, "Found " + jobCards.size() + " job cards");
            return jobCards;
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to get job cards", e);
            takeScreenshot("get_job_cards_error");
            throw new RuntimeException("Failed to get job cards", e);
        }
    }

    /**
     * Validate all jobs contain expected position, department, and location
     */
    public void validateAllJobData() {
        try {
            List<WebElement> jobCards = getAllJobCards();
            Assert.assertFalse(jobCards.isEmpty(), "No job cards found for validation");
            
            String expectedPosition = config.getExpectedPositionText();
            String expectedDepartment = config.getExpectedDepartmentText();
            String expectedLocation = config.getExpectedLocationText();
            
            int validJobs = 0;
            int totalJobs = jobCards.size();
            
            for (int i = 0; i < totalJobs; i++) {
                WebElement jobCard = jobCards.get(i);
                
                try {
                    // Get position text
                    WebElement positionElement = jobCard.findElement(jobPositionLocator);
                    String positionText = positionElement.getText();
                    
                    // Get department text
                    WebElement departmentElement = jobCard.findElement(jobDepartmentLocator);
                    String departmentText = departmentElement.getText();
                    
                    // Get location text
                    WebElement locationElement = jobCard.findElement(jobLocationLocator);
                    String locationText = locationElement.getText();
                    
                    // Validate position contains expected text
                    Assert.assertTrue(positionText.contains(expectedPosition), 
                        "Job " + (i + 1) + " position does not contain expected text. Expected: " + expectedPosition + ", Actual: " + positionText);
                    
                    // Validate department contains expected text
                    Assert.assertTrue(departmentText.contains(expectedDepartment), 
                        "Job " + (i + 1) + " department does not contain expected text. Expected: " + expectedDepartment + ", Actual: " + departmentText);
                    
                    // Validate location contains expected text
                    Assert.assertTrue(locationText.contains(expectedLocation), 
                        "Job " + (i + 1) + " location does not contain expected text. Expected: " + expectedLocation + ", Actual: " + locationText);
                    
                    validJobs++;
                    LoggerUtil.logInfo(logger, "Job " + (i + 1) + " validation passed - Position: " + positionText + ", Department: " + departmentText + ", Location: " + locationText);
                    
                } catch (Exception e) {
                    LoggerUtil.logError(logger, "Failed to validate job " + (i + 1), e);
                    takeScreenshot("job_validation_error_" + (i + 1));
                    throw e;
                }
            }
            
            LoggerUtil.logAssertion(logger, "All " + validJobs + " jobs validated successfully. Position contains '" + expectedPosition + "', Department contains '" + expectedDepartment + "', Location contains '" + expectedLocation + "'");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Job data validation failed", e);
            takeScreenshot("job_data_validation_error");
            throw new RuntimeException("Job data validation failed", e);
        }
    }

    /**
     * Click View Role button for the first available job
     */
    public void clickViewRoleButton() {
        try {
            List<WebElement> jobCards = getAllJobCards();
            Assert.assertFalse(jobCards.isEmpty(), "No job cards available to click View Role button");
            
            // Find the first job card with a View Role button
            for (int i = 0; i < jobCards.size(); i++) {
                WebElement jobCard = jobCards.get(i);
                try {
                    WebElement viewRoleButton = jobCard.findElement(viewRoleButtonLocator);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewRoleButton);
                    viewRoleButton.click();
                    
                    LoggerUtil.logInfo(logger, "Clicked View Role button for job " + (i + 1));
                    return;
                } catch (Exception e) {
                    LoggerUtil.logWarning(logger, "View Role button not found for job " + (i + 1) + ", trying next job");
                }
            }
            
            throw new RuntimeException("No View Role button found in any job card");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to click View Role button", e);
            takeScreenshot("view_role_click_error");
            throw new RuntimeException("Failed to click View Role button", e);
        }
    }

    /**
     * Verify redirection to Lever application form
     */
    public void verifyLeverApplicationRedirect() {
        try {
            waitForPageLoad();
            
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("jobs.lever.co"), 
                "Not redirected to Lever application form. Expected URL to contain 'jobs.lever.co', Actual: " + currentUrl);
            
            // Verify Lever application form is displayed
            boolean isFormDisplayed = isElementDisplayed(By.xpath(Locators.LEVER_APPLICATION_FORM), "Lever Application Form");
            Assert.assertTrue(isFormDisplayed, "Lever application form is not displayed");
            
            LoggerUtil.logAssertion(logger, "Successfully redirected to Lever application form: " + currentUrl);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Lever application redirect verification failed", e);
            takeScreenshot("lever_redirect_error");
            throw new RuntimeException("Lever application redirect verification failed", e);
        }
    }

    /**
     * Get number of available jobs
     * @return number of job cards
     */
    public int getJobCount() {
        return getAllJobCards().size();
    }
}