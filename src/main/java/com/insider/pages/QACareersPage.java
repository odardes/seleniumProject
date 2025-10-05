package com.insider.pages;

import com.insider.constants.Locators;
import com.insider.exceptions.QACareersPageException;
import com.insider.utils.ConfigManager;
import com.insider.utils.LoggerUtil;
import com.insider.utils.WebDriverFactory;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

/**
 * Page Object Model for Insider QA Careers Page
 */
public class QACareersPage extends BasePage {
    private static final Logger logger = LoggerUtil.getLogger(QACareersPage.class);
    private final ConfigManager config = ConfigManager.getInstance();

    private static final String SEE_ALL_QA_JOBS_BUTTON = "See All QA Jobs Button";
    private static final String JOB_LIST_CONTAINER = "Job List Container";
    private static final String LOCATION_FILTER_DROPDOWN = "Location Filter Dropdown";
    private static final String DEPARTMENT_FILTER_DROPDOWN = "Department Filter Dropdown";
    private static final String JOB_CARD = "Job Card";
    private static final String VIEW_ROLE_BUTTON = "View Role Button";
    private static final String LEVER_APPLICATION_FORM = "Lever Application Form";
    private static final String ACTUAL_TEXT_PREFIX = ", Actual: ";
    
    private final By seeAllQaJobsButtonLocator = By.xpath(Locators.QA_SEE_ALL_JOBS_BUTTON);
    private final By jobListContainerLocator = By.cssSelector(Locators.JOB_LIST_CONTAINER);
    private final By jobCardLocator = By.cssSelector(Locators.JOB_CARD);
    private final By jobPositionLocator = By.xpath(Locators.JOB_POSITION);
    private final By jobDepartmentLocator = By.xpath(Locators.JOB_DEPARTMENT);
    private final By jobLocationLocator = By.xpath(Locators.JOB_LOCATION);
    private final By viewRoleButtonLocator = By.xpath(Locators.VIEW_ROLE_BUTTON);
    private final By locationFilterDropdownLocator = By.cssSelector(Locators.LOCATION_FILTER_DROPDOWN);
    private final By departmentFilterDropdownLocator = By.cssSelector(Locators.DEPARTMENT_FILTER_DROPDOWN);

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
            throw new QACareersPageException("Failed to navigate to QA careers page", "Navigation", "QA Careers Page", e);
        }
    }

    /**
     * Click "See all QA jobs" button
     */
    public void clickSeeAllQaJobs() {
        try {
            scrollToElement(seeAllQaJobsButtonLocator, SEE_ALL_QA_JOBS_BUTTON);
            clickElement(seeAllQaJobsButtonLocator, SEE_ALL_QA_JOBS_BUTTON);
            waitForPageLoad();
            LoggerUtil.logInfo(logger, "Clicked on 'See all QA jobs' button");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to click 'See all QA jobs' button", e);
            takeScreenshot("see_all_qa_jobs_error");
            throw new QACareersPageException("Failed to click 'See all QA jobs' button", "Click", SEE_ALL_QA_JOBS_BUTTON, e);
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
            throw new QACareersPageException("Failed to apply job filters", "Filter", "Job Filters", e);
        }
    }


    /**
     * Filter jobs by location
     * @param location location to filter by
     */
    public void filterByLocation(String location) {
        try {
            boolean filterApplied = applyStandardLocationFilter(location);
            
            if (!filterApplied) {
                applyAlternativeLocationFilter(location);
            }
            
            waitForFilterToApply();
            LoggerUtil.logInfo(logger, "Applied location filter: " + location);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LoggerUtil.logError(logger, "Thread interrupted while applying location filter: " + location, e);
            takeScreenshot("location_filter_error");
            throw new QACareersPageException("Thread interrupted while applying location filter: " + location, "Filter", LOCATION_FILTER_DROPDOWN, e);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to apply location filter: " + location, e);
            takeScreenshot("location_filter_error");
            throw new QACareersPageException("Failed to apply location filter: " + location, "Filter", LOCATION_FILTER_DROPDOWN, e);
        }
    }

    /**
     * Apply standard location filter using dropdown or button approach
     * @param location location to filter by
     * @return true if filter was applied successfully
     */
    private boolean applyStandardLocationFilter(String location) {
        try {
            WebElement locationDropdown = waitForElementVisible(locationFilterDropdownLocator, LOCATION_FILTER_DROPDOWN);
            if (locationDropdown.getTagName().equals("select")) {
                Select select = new Select(locationDropdown);
                select.selectByVisibleText(location);
            } else {
                // For button-based filters
                clickElement(locationFilterDropdownLocator, LOCATION_FILTER_DROPDOWN);
                By locationOptionLocator = By.xpath(String.format(Locators.LOCATION_FILTER_OPTION, location));
                clickElement(locationOptionLocator, "Location Option: " + location);
            }
            return true;
        } catch (Exception e) {
            LoggerUtil.logWarning(logger, "Standard location filter approach failed, trying alternative methods");
            return false;
        }
    }

    /**
     * Apply alternative location filter by finding clickable elements with location text
     * @param location location to filter by
     */
    private void applyAlternativeLocationFilter(String location) {
        By alternativeLocationLocator = By.xpath("//option[contains(text(), '" + location + "')]");
        clickElement(alternativeLocationLocator, "Alternative Location Filter: " + location);
    }

    /**
     * Wait for filter to apply and loading to complete
     * @throws InterruptedException if thread is interrupted during sleep
     */
    private void waitForFilterToApply() throws InterruptedException {
        Thread.sleep(2000);
        waitForLoadingToComplete();
    }

    /**
     * Filter jobs by department
     * @param department department to filter by
     */
    public void filterByDepartment(String department) {
        try {
            boolean filterApplied = applyStandardDepartmentFilter(department);
            
            if (!filterApplied) {
                applyAlternativeDepartmentFilter(department);
            }
            
            waitForFilterToApply();
            LoggerUtil.logInfo(logger, "Applied department filter: " + department);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LoggerUtil.logError(logger, "Thread interrupted while applying department filter: " + department, e);
            takeScreenshot("department_filter_error");
            throw new QACareersPageException("Thread interrupted while applying department filter: " + department, "Filter", DEPARTMENT_FILTER_DROPDOWN, e);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to apply department filter: " + department, e);
            takeScreenshot("department_filter_error");
            throw new QACareersPageException("Failed to apply department filter: " + department, "Filter", DEPARTMENT_FILTER_DROPDOWN, e);
        }
    }

    /**
     * Apply standard department filter using dropdown or button approach
     * @param department department to filter by
     * @return true if filter was applied successfully
     */
    private boolean applyStandardDepartmentFilter(String department) {
        try {
            WebElement departmentDropdown = waitForElementVisible(departmentFilterDropdownLocator, DEPARTMENT_FILTER_DROPDOWN);
            if (departmentDropdown.getTagName().equals("select")) {
                Select select = new Select(departmentDropdown);
                select.selectByVisibleText(department);
            } else {
                // For button-based filters
                clickElement(departmentFilterDropdownLocator, DEPARTMENT_FILTER_DROPDOWN);
                By departmentOptionLocator = By.xpath(String.format(Locators.DEPARTMENT_FILTER_OPTION, department));
                clickElement(departmentOptionLocator, "Department Option: " + department);
            }
            return true;
        } catch (Exception e) {
            LoggerUtil.logWarning(logger, "Standard department filter approach failed, trying alternative methods");
            return false;
        }
    }

    /**
     * Apply alternative department filter by finding clickable elements with department text
     * @param department department to filter by
     */
    private void applyAlternativeDepartmentFilter(String department) {
        By alternativeDepartmentLocator = By.xpath("//option[contains(text(), '" + department + "')]");
        clickElement(alternativeDepartmentLocator, "Alternative Department Filter: " + department);
    }

    /**
     * Verify job list is displayed
     */
    public void verifyJobListDisplayed() {
        try {
            waitForLoadingToComplete();
            
            boolean isJobListDisplayed = isElementDisplayed(jobListContainerLocator, JOB_LIST_CONTAINER);
            Assert.assertTrue(isJobListDisplayed, "Job list is not displayed after applying filters");
            
            // Check if there are any job cards
            List<WebElement> jobCards = driver.findElements(jobCardLocator);
            Assert.assertFalse(jobCards.isEmpty(), "No job cards found in the job list");
            
            LoggerUtil.logAssertion(logger, "Job list is displayed with " + jobCards.size() + " job cards");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Job list verification failed", e);
            takeScreenshot("job_list_verification_error");
            throw new QACareersPageException("Job list verification failed", "Verification", JOB_LIST_CONTAINER, e);
        }
    }

    /**
     * Get number of available jobs
     * @return number of job cards
     */
    public int getJobCount() {
        return getAllJobCards().size();
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
                validateSingleJob(jobCard, i + 1, expectedPosition, expectedDepartment, expectedLocation);
                validJobs++;
            }
            
            LoggerUtil.logAssertion(logger, "All " + validJobs + " jobs validated successfully. Position contains '" + expectedPosition + "', Department contains '" + expectedDepartment + "', Location contains '" + expectedLocation + "'");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Job data validation failed", e);
            takeScreenshot("job_data_validation_error");
            throw new QACareersPageException("Job data validation failed", "Validation", "Job Data", e);
        }
    }

    /**
     * Validate a single job card's position, department, and location
     * @param jobCard the job card element to validate
     * @param jobNumber the job number for logging
     * @param expectedPosition expected position text
     * @param expectedDepartment expected department text
     * @param expectedLocation expected location text
     */
    private void validateSingleJob(WebElement jobCard, int jobNumber, String expectedPosition, String expectedDepartment, String expectedLocation) {
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
                    "Job " + jobNumber + " position does not contain expected text. Expected: " + expectedPosition + ACTUAL_TEXT_PREFIX + positionText);

            // Validate department contains expected text
            Assert.assertTrue(departmentText.contains(expectedDepartment),
                    "Job " + jobNumber + " department does not contain expected text. Expected: " + expectedDepartment + ACTUAL_TEXT_PREFIX + departmentText);

            // Validate location contains expected text
            Assert.assertTrue(locationText.contains(expectedLocation),
                    "Job " + jobNumber + " location does not contain expected text. Expected: " + expectedLocation + ACTUAL_TEXT_PREFIX + locationText);

            LoggerUtil.logInfo(logger, "Job " + jobNumber + " validation passed - Position: " + positionText + ", Department: " + departmentText + ", Location: " + locationText);

        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to validate job " + jobNumber, e);
            takeScreenshot("job_validation_error_" + jobNumber);
            throw e;
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
                if (tryClickViewRoleButton(jobCard, i + 1)) {
                    return;
                }
            }
            
            throw new QACareersPageException("No View Role button found in any job card", "Click", VIEW_ROLE_BUTTON);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to click View Role button", e);
            takeScreenshot("view_role_click_error");
            throw new QACareersPageException("Failed to click View Role button", "Click", VIEW_ROLE_BUTTON, e);
        }
    }

    /**
     * Get all job cards
     * @return list of job card elements
     */
    public List<WebElement> getAllJobCards() {
        try {
            waitForElementVisible(jobListContainerLocator, JOB_LIST_CONTAINER);
            List<WebElement> jobCards = driver.findElements(jobCardLocator);
            LoggerUtil.logInfo(logger, "Found " + jobCards.size() + " job cards");
            return jobCards;
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to get job cards", e);
            takeScreenshot("get_job_cards_error");
            throw new QACareersPageException("Failed to get job cards", "Retrieval", JOB_CARD, e);
        }
    }

    /**
     * Try to click View Role button on a job card
     * @param jobCard the job card element
     * @param jobNumber the job number for logging
     * @return true if button was clicked successfully, false otherwise
     */
    private boolean tryClickViewRoleButton(WebElement jobCard, int jobNumber) {
        try {
            WebElement viewRoleButton = jobCard.findElement(viewRoleButtonLocator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewRoleButton);
            viewRoleButton.click();

            LoggerUtil.logInfo(logger, "Clicked View Role button for job " + jobNumber);
            return true;
        } catch (Exception e) {
            LoggerUtil.logWarning(logger, "View Role button not found for job " + jobNumber + ", trying next job");
            return false;
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
            boolean isFormDisplayed = isElementDisplayed(By.xpath(Locators.LEVER_APPLICATION_FORM), LEVER_APPLICATION_FORM);
            Assert.assertTrue(isFormDisplayed, "Lever application form is not displayed");
            
            LoggerUtil.logAssertion(logger, "Successfully redirected to Lever application form: " + currentUrl);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Lever application redirect verification failed", e);
            takeScreenshot("lever_redirect_error");
            throw new QACareersPageException("Lever application redirect verification failed", "Verification", LEVER_APPLICATION_FORM, e);
        }
    }
}