package com.insider.tests;

import com.insider.pages.CareersPage;
import com.insider.pages.HomePage;
import com.insider.pages.QACareersPage;
import com.insider.utils.LoggerUtil;
import com.insider.utils.WebDriverFactory;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Test class for Insider Careers functionality
 * Implements the complete test scenario with 5 steps
 */
public class InsiderCareersTest {
    private static final Logger logger = LoggerUtil.getLogger(InsiderCareersTest.class);
    
    private HomePage homePage;
    private CareersPage careersPage;
    private QACareersPage qaCareersPage;

    /**
     * Test setup - initialize WebDriver and page objects
     */
    @BeforeTest
    public void setUp() {
        try {
            LoggerUtil.logTestStart(logger, "Insider Careers Test");
            
            // Initialize WebDriver
            WebDriverFactory.initializeDriver();

            // Initialize page objects
            homePage = new HomePage();
            careersPage = new CareersPage();
            qaCareersPage = new QACareersPage();
            
            LoggerUtil.logInfo(logger, "Test setup completed successfully");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Test setup failed", e);
            throw new RuntimeException("Test setup failed", e);
        }
    }

    /**
     * Test Step 1: Visit <a href="https://useinsider.com/">...</a> and check Insider home page is opened or not
     */
    @Test(priority = 1, description = "Verify Insider home page is opened successfully")
    public void testHomePageOpened() {
        try {
            LoggerUtil.logStep(logger, "Step 1: Navigate to Insider home page and verify it's opened");
            
            homePage.navigateToHomePage();
            homePage.verifyHomePageLoaded();
            
            LoggerUtil.logAssertion(logger, "Home page opened successfully - Step 1 PASSED");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Step 1 failed: Home page verification failed", e);
            Assert.fail("Step 1 failed: Home page verification failed - " + e.getMessage());
        }
    }

    /**
     * Test Step 2: Select the "Company" menu in the navigation bar, select "Careers" 
     * and check Career page, its Locations, Teams, and Life at Insider blocks are open or not
     */
    @Test(priority = 2, description = "Navigate to Careers page and verify all sections are displayed", dependsOnMethods = "testHomePageOpened")
    public void testCareerPageSections() {
        try {
            LoggerUtil.logStep(logger, "Step 2: Navigate to Careers page and verify Locations, Teams, and Life at Insider sections");
            
            homePage.navigateToCareersPage();
            careersPage.verifyCareersPageLoaded();
            careersPage.verifyAllCareerSectionsDisplayed();
            
            LoggerUtil.logAssertion(logger, "All career sections displayed successfully - Step 2 PASSED");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Step 2 failed: Career page sections verification failed", e);
            Assert.fail("Step 2 failed: Career page sections verification failed - " + e.getMessage());
        }
    }

    /**
     * Test Step 3: Go to <a href="https://useinsider.com/careers/quality-assurance/">...</a>,
     * click "See all QA jobs", filter jobs by Location: "Istanbul, Turkey", 
     * and Department: "Quality Assurance", check the presence of the jobs list
     */
    @Test(priority = 3, description = "Navigate to QA careers page, apply filters, and verify job list", dependsOnMethods = "testCareerPageSections")
    public void testQAJobsFiltering() {
        try {
            LoggerUtil.logStep(logger, "Step 3: Navigate to QA careers page, apply filters, and verify job list");
            
            qaCareersPage.navigateToQACareersPage();
            qaCareersPage.clickSeeAllQaJobs();
            qaCareersPage.applyJobFilters();
            qaCareersPage.verifyJobListDisplayed();
            
            int jobCount = qaCareersPage.getJobCount();
            LoggerUtil.logInfo(logger, "Found " + jobCount + " jobs after applying filters");
            Assert.assertTrue(jobCount > 0, "No jobs found after applying filters");
            
            LoggerUtil.logAssertion(logger, "Job list displayed successfully with " + jobCount + " jobs - Step 3 PASSED");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Step 3 failed: QA jobs filtering failed", e);
            Assert.fail("Step 3 failed: QA jobs filtering failed - " + e.getMessage());
        }
    }

    /**
     * Test Step 4: Check that all jobs' Position contains "Quality Assurance", 
     * Department contains "Quality Assurance", and Location contains "Istanbul, Turkey"
     */
    @Test(priority = 4, description = "Validate all job data contains expected position, department, and location", dependsOnMethods = "testQAJobsFiltering")
    public void testJobDataValidation() {
        try {
            LoggerUtil.logStep(logger, "Step 4: Validate all jobs contain expected position, department, and location text");

            qaCareersPage.validateAllJobData();
            
            LoggerUtil.logAssertion(logger, "All job data validation passed - Step 4 PASSED");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Step 4 failed: Job data validation failed", e);
            Assert.fail("Step 4 failed: Job data validation failed - " + e.getMessage());
        }
    }

    /**
     * Test Step 5: Click the "View Role" button and check that this action 
     * redirects us to the Lever Application form page
     */
    @Test(priority = 5, description = "Click View Role button and verify Lever application redirect", dependsOnMethods = "testJobDataValidation")
    public void testViewRoleRedirect() {
        try {
            LoggerUtil.logStep(logger, "Step 5: Click View Role button and verify Lever application redirect");
            
            qaCareersPage.clickViewRoleButton();
            qaCareersPage.verifyLeverApplicationRedirect();
            
            LoggerUtil.logAssertion(logger, "Successfully redirected to Lever application form - Step 5 PASSED");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Step 5 failed: View Role redirect verification failed", e);
            Assert.fail("Step 5 failed: View Role redirect verification failed - " + e.getMessage());
        }
    }

    /**
     * Complete test scenario - runs all steps in sequence
     */
    @Test(priority = 6, description = "Complete Insider Careers test scenario", enabled = false)
    public void testCompleteInsiderCareersScenario() {
        try {
            LoggerUtil.logStep(logger, "Running complete Insider Careers test scenario");
            
            // Step 1: Home page verification
            testHomePageOpened();
            
            // Step 2: Career page sections verification
            testCareerPageSections();
            
            // Step 3: QA jobs filtering
            testQAJobsFiltering();
            
            // Step 4: Job data validation
            testJobDataValidation();
            
            // Step 5: View Role redirect
            testViewRoleRedirect();
            
            LoggerUtil.logAssertion(logger, "Complete Insider Careers test scenario PASSED");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Complete test scenario failed", e);
            Assert.fail("Complete test scenario failed - " + e.getMessage());
        }
    }

    /**
     * Test cleanup - quit WebDriver
     */
    @AfterTest
    public void tearDown() {
        try {
            LoggerUtil.logInfo(logger, "Cleaning up test resources");
            WebDriverFactory.quitDriver();
            LoggerUtil.logTestEnd(logger, "Insider Careers Test");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Test cleanup failed", e);
        }
    }
}