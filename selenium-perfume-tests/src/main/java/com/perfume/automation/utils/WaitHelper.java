package com.perfume.automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * WaitHelper utility for custom waiting strategies
 * Handles dynamic content and custom JavaScript events
 */
public class WaitHelper {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private static ConfigReader config = ConfigReader.getInstance();

    public WaitHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
        this.js = (JavascriptExecutor) driver;
    }

    /**
     * Wait for element to be visible by locator
     */
    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable
     */
    public WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for element to be visible by data-testid attribute
     */
    public WebElement waitForTestId(String testId) {
        By locator = By.cssSelector("[data-testid='" + testId + "']");
        return waitForElementVisible(locator);
    }

    /**
     * Wait for header to be loaded
     * Waits for custom 'headerLoaded' event
     */
    public void waitForHeaderLoaded() {
        wait.until(driver ->
            js.executeScript("return document.getElementById('header-loaded') !== null")
        );
    }

    /**
     * Wait for footer to be loaded
     * Waits for custom 'footerLoaded' event
     */
    public void waitForFooterLoaded() {
        wait.until(driver ->
            js.executeScript("return document.getElementById('footer-loaded') !== null")
        );
    }

    /**
     * Wait for products to be loaded
     * Waits for custom 'productsLoaded' event
     */
    public void waitForProductsLoaded() {
        wait.until(driver ->
            js.executeScript("return document.getElementById('products-loaded') !== null")
        );
    }

    /**
     * Wait for single product to be loaded (product detail page)
     * Waits for custom 'productLoaded' event
     */
    public void waitForProductLoaded() {
        wait.until(driver ->
            js.executeScript("return document.getElementById('product-loaded') !== null")
        );
    }

    /**
     * Wait for cart to be updated
     * Waits for custom 'cartUpdated' event
     */
    public void waitForCartUpdated() {
        // Set up event listener flag
        js.executeScript(
            "window.cartUpdateEventFired = false;" +
            "document.addEventListener('cartUpdated', function() {" +
            "    window.cartUpdateEventFired = true;" +
            "}, { once: true });"
        );

        // Wait for event to fire
        wait.until(driver ->
            (Boolean) js.executeScript("return window.cartUpdateEventFired === true")
        );
    }

    /**
     * Wait for page load to complete
     */
    public void waitForPageLoad() {
        wait.until(driver ->
            js.executeScript("return document.readyState").equals("complete")
        );
    }

    /**
     * Wait for element with specific text to be visible
     */
    public WebElement waitForElementWithText(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text))
                ? driver.findElement(locator)
                : null;
    }

    /**
     * Wait for element to disappear
     */
    public boolean waitForElementToDisappear(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Custom wait with timeout
     */
    public WebElement waitForElement(By locator, int timeoutSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for JavaScript condition to be true
     */
    public void waitForJSCondition(String jsCondition) {
        wait.until(driver -> (Boolean) js.executeScript("return " + jsCondition));
    }
}
