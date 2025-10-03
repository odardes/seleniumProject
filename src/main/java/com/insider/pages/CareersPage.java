package com.insider.pages;

import com.insider.constants.Locators;
import com.insider.exceptions.CareersPageException;
import com.insider.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Page Object Model for Insider Careers Page
 */
public class CareersPage extends BasePage {
    private static final Logger logger = LoggerUtil.getLogger(CareersPage.class);

    private final By locationsSectionLocator = By.xpath(Locators.CAREERS_LOCATIONS_SECTION);
    private final By teamsSectionLocator = By.xpath(Locators.CAREERS_TEAMS_SECTION);
    private final By lifeAtInsiderSectionLocator = By.xpath(Locators.CAREERS_LIFE_AT_INSIDER_SECTION);

    /**
     * Verify careers page is loaded successfully
     */
    public void verifyCareersPageLoaded() {
        try {
            waitForPageLoad();

            // Verify URL contains careers
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("careers"), 
                "Careers page URL verification failed. Expected to contain 'careers', Actual: " + currentUrl);
            
            // Verify page title contains careers
            String pageTitle = driver.getTitle();
            Assert.assertTrue(pageTitle.toLowerCase().contains("careers"), 
                "Careers page title verification failed. Expected to contain 'careers', Actual: " + pageTitle);
            
            LoggerUtil.logAssertion(logger, "Careers page loaded successfully - URL: " + currentUrl + ", Title: " + pageTitle);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Careers page verification failed", e);
            takeScreenshot("careers_page_verification_error");
            throw new CareersPageException("Careers page verification failed", "Page Load", "URL and Title Verification", e);
        }
    }

    /**
     * Verify Locations section is displayed
     */
    public void verifyLocationsSectionDisplayed() {
        try {
            scrollToElement(locationsSectionLocator, "Locations Section");
            boolean isDisplayed = isElementDisplayed(locationsSectionLocator, "Locations Section");
            
            Assert.assertTrue(isDisplayed, "Locations section is not displayed on careers page");
            
            WebElement locationsSection = waitForElementVisible(locationsSectionLocator, "Locations Section");
            String sectionText = locationsSection.getText();
            Assert.assertFalse(sectionText.trim().isEmpty(), "Locations section appears to be empty");

            LoggerUtil.logInfo(logger, "Locations section content: " + sectionText.substring(0, Math.min(100, sectionText.length())) + "...");

            LoggerUtil.logAssertion(logger, "Locations section is displayed and has content");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Locations section verification failed", e);
            takeScreenshot("locations_section_error");
            throw new CareersPageException("Locations section verification failed", "Locations Section", "Section Display and Content Verification", e);
        }
    }

    /**
     * Verify Teams section is displayed
     */
    public void verifyTeamsSectionDisplayed() {
        try {
            scrollToElement(teamsSectionLocator, "Teams Section");
            boolean isDisplayed = isElementDisplayed(teamsSectionLocator, "Teams Section");
            
            Assert.assertTrue(isDisplayed, "Teams section is not displayed on careers page");
            
            WebElement teamsSection = waitForElementVisible(teamsSectionLocator, "Teams Section");
            String sectionText = teamsSection.getText();
            Assert.assertFalse(sectionText.trim().isEmpty(), "Teams section appears to be empty");

            LoggerUtil.logInfo(logger, "Teams section content: " + sectionText.substring(0, Math.min(100, sectionText.length())) + "...");

            LoggerUtil.logAssertion(logger, "Teams section is displayed and has content");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Teams section verification failed", e);
            takeScreenshot("teams_section_error");
            throw new CareersPageException("Teams section verification failed", "Teams Section", "Section Display and Content Verification", e);
        }
    }

    /**
     * Verify Life at Insider section is displayed
     */
    public void verifyLifeAtInsiderSectionDisplayed() {
        try {
            scrollToElement(lifeAtInsiderSectionLocator, "Life at Insider Section");
            boolean isDisplayed = isElementDisplayed(lifeAtInsiderSectionLocator, "Life at Insider Section");
            
            Assert.assertTrue(isDisplayed, "Life at Insider section is not displayed on careers page");
            
            WebElement lifeSection = waitForElementVisible(lifeAtInsiderSectionLocator, "Life at Insider Section");
            String sectionText = lifeSection.getText();
            Assert.assertFalse(sectionText.trim().isEmpty(), "Life at Insider section appears to be empty");

            LoggerUtil.logInfo(logger, "Life at Insider section content: " + sectionText.substring(0, Math.min(100, sectionText.length())) + "...");

            LoggerUtil.logAssertion(logger, "Life at Insider section is displayed and has content");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Life at Insider section verification failed", e);
            takeScreenshot("life_at_insider_section_error");
            throw new CareersPageException("Life at Insider section verification failed", "Life at Insider Section", "Section Display and Content Verification", e);
        }
    }

    /**
     * Verify all career page sections are displayed
     */
    public void verifyAllCareerSectionsDisplayed() {
        try {
            LoggerUtil.logInfo(logger, "Verifying all career page sections...");
            
            verifyLocationsSectionDisplayed();
            verifyTeamsSectionDisplayed();
            verifyLifeAtInsiderSectionDisplayed();
            
            LoggerUtil.logAssertion(logger, "All career page sections (Locations, Teams, Life at Insider) are displayed successfully");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Career sections verification failed", e);
            takeScreenshot("career_sections_error");
            throw new CareersPageException("Career sections verification failed", "All Sections", "Comprehensive Section Verification", e);
        }
    }

}