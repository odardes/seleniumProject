package com.insider.pages;

import com.insider.constants.Locators;
import com.insider.utils.ConfigManager;
import com.insider.utils.LoggerUtil;
import com.insider.utils.WebDriverFactory;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * Page Object Model for Insider Home Page
 */
public class HomePage extends BasePage {
    private static final Logger logger = LoggerUtil.getLogger(HomePage.class);
    private final ConfigManager config = ConfigManager.getInstance();

    // Locators specific to HomePage
    private final By companyMenuLocator = By.xpath(Locators.HOME_COMPANY_MENU);
    private final By careersLinkLocator = By.xpath(Locators.HOME_CAREERS_LINK);

    /**
     * Navigate to Insider home page
     */
    public void navigateToHomePage() {
        try {
            WebDriverFactory.navigateTo(config.getBaseUrl());
            waitForPageLoad();
            LoggerUtil.logInfo(logger, "Navigated to Insider home page: " + config.getBaseUrl());
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to navigate to home page", e);
            takeScreenshot("home_page_navigation_error");
            throw new RuntimeException("Failed to navigate to home page", e);
        }
    }

    /**
     * Verify home page is loaded successfully
     */
    public void verifyHomePageLoaded() {
        try {
            waitForPageLoad();
            
            // Verify URL contains expected domain
            String currentUrl = WebDriverFactory.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("useinsider.com"), 
                "Home page URL verification failed. Expected to contain 'useinsider.com', Actual: " + currentUrl);
            
            // Verify page title is not empty
            String pageTitle = WebDriverFactory.getPageTitle();
            Assert.assertFalse(pageTitle.isEmpty(), 
                "Home page title is empty");
            
            LoggerUtil.logAssertion(logger, "Home page loaded successfully - URL: " + currentUrl + ", Title: " + pageTitle);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Home page verification failed", e);
            takeScreenshot("home_page_verification_error");
            throw new RuntimeException("Home page verification failed", e);
        }
    }

    /**
     * Hover over Company menu to reveal dropdown
     */
    public void hoverOverCompanyMenu() {
        try {
            scrollToElement(companyMenuLocator, "Company Menu");
            WebElement companyMenu = waitForElementVisible(companyMenuLocator, "Company Menu");
            
            // Hover over the Company menu
            org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
            actions.moveToElement(companyMenu).perform();
            
            // Wait a moment for dropdown to appear
            Thread.sleep(1000);
            
            LoggerUtil.logInfo(logger, "Hovered over Company menu");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to hover over Company menu", e);
            takeScreenshot("company_menu_hover_error");
            throw new RuntimeException("Failed to hover over Company menu", e);
        }
    }

    /**
     * Click on Careers link from Company dropdown
     */
    public void clickCareersLink() {
        try {
            // First hover over Company menu to reveal dropdown
            hoverOverCompanyMenu();
            
            // Wait for Careers link to be clickable and click it
            scrollToElement(careersLinkLocator, "Careers Link");
            clickElement(careersLinkLocator, "Careers Link");
            
            LoggerUtil.logInfo(logger, "Clicked on Careers link");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to click on Careers link", e);
            takeScreenshot("careers_link_click_error");
            throw new RuntimeException("Failed to click on Careers link", e);
        }
    }

    /**
     * Navigate to Careers page using direct URL (alternative method)
     */
    public void navigateToCareersPage() {
        try {
            WebDriverFactory.navigateTo(config.getCareersUrl());
            waitForPageLoad();
            LoggerUtil.logInfo(logger, "Navigated directly to Careers page: " + config.getCareersUrl());
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to navigate to Careers page", e);
            takeScreenshot("careers_page_navigation_error");
            throw new RuntimeException("Failed to navigate to Careers page", e);
        }
    }

    /**
     * Verify Company menu is displayed
     * @return true if Company menu is displayed
     */
    public boolean isCompanyMenuDisplayed() {
        return isElementDisplayed(companyMenuLocator, "Company Menu");
    }

    /**
     * Verify Careers link is displayed (after hovering over Company menu)
     * @return true if Careers link is displayed
     */
    public boolean isCareersLinkDisplayed() {
        try {
            hoverOverCompanyMenu();
            return isElementDisplayed(careersLinkLocator, "Careers Link");
        } catch (Exception e) {
            LoggerUtil.logWarning(logger, "Careers link not displayed after hovering Company menu");
            return false;
        }
    }

    /**
     * Get current page URL
     * @return current URL
     */
    public String getCurrentUrl() {
        return WebDriverFactory.getCurrentUrl();
    }

    /**
     * Get current page title
     * @return current page title
     */
    public String getCurrentTitle() {
        return WebDriverFactory.getPageTitle();
    }
}