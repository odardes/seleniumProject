package com.insider.pages;

import com.insider.constants.Locators;
import com.insider.exceptions.HomePageException;
import com.insider.utils.ConfigManager;
import com.insider.utils.LoggerUtil;
import com.insider.utils.WebDriverFactory;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

/**
 * Page Object Model for Insider Home Page
 */
public class HomePage extends BasePage {
    private static final Logger logger = LoggerUtil.getLogger(HomePage.class);
    private final ConfigManager config = ConfigManager.getInstance();

    private static final String COMPANY_MENU = "Company Menu";
    private static final String CAREERS_LINK = "Careers Link";
    
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
            throw new HomePageException("Failed to navigate to home page", "Navigation", "Home Page", e);
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
            Assert.assertTrue(currentUrl.contains("useinsider.com"), "Home page URL verification failed. Expected to contain 'useinsider.com', Actual: " + currentUrl);
            
            // Verify page title is not empty
            String pageTitle = WebDriverFactory.getPageTitle();
            Assert.assertFalse(pageTitle.isEmpty(), "Home page title is empty");
            
            LoggerUtil.logAssertion(logger, "Home page loaded successfully - URL: " + currentUrl + ", Title: " + pageTitle);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Home page verification failed", e);
            takeScreenshot("home_page_verification_error");
            throw new HomePageException("Home page verification failed", "Verification", "Home Page", e);
        }
    }

    /**
     * Navigate to Careers page
     */
    public void navigateToCareersPage() {
        try {
            clickCareersLink();
            waitForPageLoad();
            LoggerUtil.logInfo(logger, "Navigated to Careers page");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to navigate to Careers page", e);
            takeScreenshot("careers_page_navigation_error");
            throw new HomePageException("Failed to navigate to Careers page", "Navigation", "Careers Page", e);
        }
    }

    /**
     * Click on Careers link from Company dropdown
     */
    public void clickCareersLink() {
        try {
            Assert.assertTrue(isCompanyMenuDisplayed(), "Company menu is not displayed, cannot click Careers link");

            hoverOverCompanyMenu();

            Assert.assertTrue(isCareersLinkDisplayed(), "Careers link is not displayed after hovering over Company menu");

            scrollToElement(careersLinkLocator, CAREERS_LINK);
            clickElement(careersLinkLocator, CAREERS_LINK);

            LoggerUtil.logInfo(logger, "Clicked on Careers link");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to click on Careers link", e);
            takeScreenshot("careers_link_click_error");
            throw new HomePageException("Failed to click on Careers link", "Click", CAREERS_LINK, e);
        }
    }

    /**
     * Hover over Company menu to reveal dropdown
     */
    public void hoverOverCompanyMenu() {
        try {
            scrollToElement(companyMenuLocator, COMPANY_MENU);
            WebElement companyMenu = waitForElementVisible(companyMenuLocator, COMPANY_MENU);
            hoverOverElement(companyMenu, COMPANY_MENU);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LoggerUtil.logError(logger, "Thread interrupted while waiting for dropdown", e);
            takeScreenshot("company_menu_hover_error");
            throw new HomePageException("Thread interrupted while waiting for " + COMPANY_MENU + " dropdown", "Hover", COMPANY_MENU, e);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to hover over " + COMPANY_MENU, e);
            takeScreenshot("company_menu_hover_error");
            throw new HomePageException("Failed to hover over " + COMPANY_MENU, "Hover", COMPANY_MENU, e);
        }
    }

    /**
     * Verify Company menu is displayed
     * @return true if Company menu is displayed
     */
    public boolean isCompanyMenuDisplayed() {
        return isElementDisplayed(companyMenuLocator, COMPANY_MENU);
    }

    /**
     * Verify Careers link is displayed (after hovering over Company menu)
     * @return true if Careers link is displayed
     */
    public boolean isCareersLinkDisplayed() {
        try {
            hoverOverCompanyMenu();
            return isElementDisplayed(careersLinkLocator, CAREERS_LINK);
        } catch (Exception e) {
            LoggerUtil.logWarning(logger, "Careers link not displayed after hovering  Company menu");
            return false;
        }
    }

}