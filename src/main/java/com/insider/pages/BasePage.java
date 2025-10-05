package com.insider.pages;

import com.insider.exceptions.ElementException;
import com.insider.utils.LoggerUtil;
import com.insider.utils.WebDriverFactory;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Base page class containing common web element operations
 */
public abstract class BasePage {
    protected static final Logger logger = LoggerUtil.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected BasePage() {
        this.driver = WebDriverFactory.getDriver();
        this.wait = WebDriverFactory.getWait();
    }

    /**
     * Click element by locator
     * @param locator element locator
     * @param elementName element name for logging
     */
    protected void clickElement(By locator, String elementName) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            LoggerUtil.logInfo(logger, "Clicked on element: " + elementName);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to click on element: " + elementName, e);
            takeScreenshot("click_error_" + elementName);
            throw new ElementException("Failed to click on element", elementName, "CLICK", e);
        }
    }

    /**
     * Click element by locator with JavaScript
     * @param locator element locator
     * @param elementName element name for logging
     */
    protected void clickElementWithJS(By locator, String elementName) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            LoggerUtil.logInfo(logger, "Clicked on element with JavaScript: " + elementName);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to click on element with JavaScript: " + elementName, e);
            takeScreenshot("js_click_error_" + elementName);
            throw new ElementException("Failed to click on element with JavaScript", elementName, "JS_CLICK", e);
        }
    }

    /**
     * Get text from element
     * @param locator element locator
     * @param elementName element name for logging
     * @return element text
     */
    protected String getElementText(By locator, String elementName) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String text = element.getText();
            LoggerUtil.logInfo(logger, "Retrieved text from element: " + elementName + " - Text: " + text);
            return text;
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to get text from element: " + elementName, e);
            takeScreenshot("get_text_error_" + elementName);
            throw new ElementException("Failed to get text from element", elementName, "GET_TEXT", e);
        }
    }

    /**
     * Check if element is displayed
     * @param locator element locator
     * @param elementName element name for logging
     * @return true if element is displayed
     */
    protected boolean isElementDisplayed(By locator, String elementName) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            boolean displayed = element.isDisplayed();
            LoggerUtil.logInfo(logger, "Element display status: " + elementName + " - Displayed: " + displayed);
            return displayed;
        } catch (Exception e) {
            LoggerUtil.logWarning(logger, "Element not displayed: " + elementName);
            return false;
        }
    }

    /**
     * Wait for element to be visible
     * @param locator element locator
     * @param elementName element name for logging
     * @return WebElement
     */
    protected WebElement waitForElementVisible(By locator, String elementName) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            LoggerUtil.logInfo(logger, "Element is now visible: " + elementName);
            return element;
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Element did not become visible: " + elementName, e);
            takeScreenshot("wait_error_" + elementName);
            throw new ElementException("Element did not become visible", elementName, "WAIT_VISIBLE", e);
        }
    }

    /**
     * Wait for element to be clickable
     *
     * @param locator     element locator
     * @param elementName element name for logging
     */
    protected void waitForElementClickable(By locator, String elementName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            LoggerUtil.logInfo(logger, "Element is now clickable: " + elementName);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Element did not become clickable: " + elementName, e);
            takeScreenshot("wait_clickable_error_" + elementName);
            throw new ElementException("Element did not become clickable", elementName, "WAIT_CLICKABLE", e);
        }
    }

    /**
     * Scroll to element
     * @param locator element locator
     * @param elementName element name for logging
     */
    protected void scrollToElement(By locator, String elementName) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            LoggerUtil.logInfo(logger, "Scrolled to element: " + elementName);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to scroll to element: " + elementName, e);
            throw new ElementException("Failed to scroll to element", elementName, "SCROLL", e);
        }
    }

    /**
     * Hover over WebElement
     * @param element WebElement to hover over
     * @param elementName element name for logging
     */
    protected void hoverOverElement(WebElement element, String elementName) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
            LoggerUtil.logInfo(logger, "Hovered over element: " + elementName);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to hover over element: " + elementName, e);
            takeScreenshot("hover_error_" + elementName);
            throw new ElementException("Failed to hover over element", elementName, "HOVER", e);
        }
    }

    /**
     * Wait for page to load completely
     */
    protected void waitForPageLoad() {
        try {
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
            LoggerUtil.logInfo(logger, "Page loaded completely");
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Page did not load completely", e);
            throw new ElementException("Page did not load completely", "PAGE", "PAGE_LOAD", e);
        }
    }

    /**
     * Take screenshot
     * @param fileName screenshot file name
     */
    protected void takeScreenshot(String fileName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String screenshotPath = "screenshots/" + fileName + "_" + timestamp + ".png";
            
            Files.createDirectories(Paths.get("screenshots"));
            Files.copy(screenshot.toPath(), Paths.get(screenshotPath));
            
            LoggerUtil.logInfo(logger, "Screenshot saved: " + screenshotPath);
        } catch (IOException e) {
            LoggerUtil.logError(logger, "Failed to take screenshot: " + fileName, e);
        }
    }

    /**
     * Verify current URL contains expected text
     * @param expectedUrlText expected URL text
     */
    protected void verifyUrlContains(String expectedUrlText) {
        try {
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains(expectedUrlText), 
                "URL does not contain expected text. Expected: " + expectedUrlText + ", Actual: " + currentUrl);
            LoggerUtil.logAssertion(logger, "URL contains expected text: " + expectedUrlText);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "URL verification failed for: " + expectedUrlText, e);
            takeScreenshot("url_verification_error");
            throw e;
        }
    }

    /**
     * Verify page title contains expected text
     * @param expectedTitleText expected title text
     */
    protected void verifyTitleContains(String expectedTitleText) {
        try {
            String actualTitle = driver.getTitle();
            Assert.assertTrue(actualTitle.contains(expectedTitleText), 
                "Title does not contain expected text. Expected: " + expectedTitleText + ", Actual: " + actualTitle);
            LoggerUtil.logAssertion(logger, "Title contains expected text: " + expectedTitleText);
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Title verification failed for: " + expectedTitleText, e);
            takeScreenshot("title_verification_error");
            throw e;
        }
    }

    /**
     * Get current page URL
     * @return current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get current page title
     * @return current page title
     */
    public String getCurrentTitle() {
        return driver.getTitle();
    }
}