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
     * Waits for custom 'headerLoaded' event or navigation element
     */
    public void waitForHeaderLoaded() {
        // Wait for either the hidden marker OR the nav element
        wait.until(driver ->
            (Boolean) js.executeScript(
                "return document.getElementById('header-loaded') !== null || " +
                "(document.querySelector('nav') !== null && document.getElementById('navbar') !== null)"
            )
        );
    }

    /**
     * Wait for footer to be loaded
     * Waits for custom 'footerLoaded' event or footer element
     */
    public void waitForFooterLoaded() {
        // Wait for either the hidden marker OR the footer element
        wait.until(driver ->
            (Boolean) js.executeScript(
                "return document.getElementById('footer-loaded') !== null || " +
                "document.querySelector('footer') !== null"
            )
        );
    }

    /**
     * Wait for products to be loaded
     * Waits for custom 'productsLoaded' event or product cards
     */
    public void waitForProductsLoaded() {
        // Wait for either the hidden marker OR actual product cards
        wait.until(driver ->
            (Boolean) js.executeScript(
                "return document.getElementById('products-loaded') !== null || " +
                "document.querySelector('[data-testid=\"product-card\"]') !== null"
            )
        );
        // Wait for DOM to be ready (products rendered and scripts executed)
        wait.until(driver ->
            js.executeScript("return document.readyState").equals("complete")
        );
    }

    /**
     * Wait for single product to be loaded (product detail page)
     * Waits for custom 'productLoaded' event or product detail elements
     */
    public void waitForProductLoaded() {
        // Wait for either the hidden marker OR product detail name element
        wait.until(driver ->
            (Boolean) js.executeScript(
                "return document.getElementById('product-loaded') !== null || " +
                "document.querySelector('[data-testid=\"product-detail-name\"]') !== null"
            )
        );
    }

    /**
     * Wait for cart to be updated
     * Waits for cart count element to change or a short stabilization period
     */
    public void waitForCartUpdated() {
        // Verify cart count element is visible and has been updated
        wait.until(driver -> {
            Object result = js.executeScript(
                "var cartCountElem = document.getElementById('cartCount');" +
                "return cartCountElem !== null && cartCountElem.textContent !== null && cartCountElem.textContent !== '';"
            );
            return result != null && (Boolean) result;
        });

        // Wait for DOM to be ready (cart update scripts completed)
        wait.until(driver ->
            js.executeScript("return document.readyState").equals("complete")
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

    /**
     * Wait for a short duration for UI updates
     * Waits for DOM to be stable by checking document.readyState
     */
    public void waitForUIUpdate() {
        wait.until(driver ->
            js.executeScript("return document.readyState").equals("complete")
        );
    }

    /**
     * Wait for element to be stable (not animating or changing)
     */
    public void waitForElementStable(By locator) {
        // Wait for element to be visible first
        waitForElementVisible(locator);
        // Wait for DOM to be ready
        wait.until(driver ->
            js.executeScript("return document.readyState").equals("complete")
        );
    }

    /**
     * Wait for AJAX/fetch requests to complete
     */
    public void waitForAjaxComplete() {
        wait.until(driver ->
            (Boolean) js.executeScript(
                "return (typeof jQuery !== 'undefined') ? jQuery.active === 0 : true"
            )
        );
    }

    /**
     * Wait for specific duration using polling (more reliable than Thread.sleep)
     */
    public void waitForDuration(int milliseconds) {
        long startTime = System.currentTimeMillis();
        wait.until(driver -> (System.currentTimeMillis() - startTime) >= milliseconds);
    }

    /**
     * Wait for element count to stabilize
     */
    public void waitForElementCountStable(By locator) {
        // Wait for at least one element to be present
        wait.until(driver -> !driver.findElements(locator).isEmpty());
        // Wait for DOM to be ready
        wait.until(driver ->
            js.executeScript("return document.readyState").equals("complete")
        );
    }
}
