package com.perfume.automation.pages;

import com.perfume.automation.components.Header;
import com.perfume.automation.utils.WaitHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * BasePage class containing common methods for all page objects
 * All page classes should extend this base class
 */
public class BasePage {

    protected WebDriver driver;
    public WaitHelper waitHelper;  // Public for access from step definitions
    protected JavascriptExecutor js;
    protected Header header;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        this.js = (JavascriptExecutor) driver;
        this.header = new Header(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Click on element using data-testid
     */
    protected void clickByTestId(String testId) {
        WebElement element = waitHelper.waitForTestId(testId);
        element.click();
    }

    /**
     * Click on element by locator
     */
    protected void click(By locator) {
        WebElement element = waitHelper.waitForElementClickable(locator);
        element.click();
    }

    /**
     * Enter text into input field
     */
    protected void enterText(By locator, String text) {
        WebElement element = waitHelper.waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Enter text using data-testid
     */
    protected void enterTextByTestId(String testId, String text) {
        WebElement element = waitHelper.waitForTestId(testId);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from element
     */
    protected String getText(By locator) {
        return waitHelper.waitForElementVisible(locator).getText();
    }

    /**
     * Get text using data-testid
     */
    protected String getTextByTestId(String testId) {
        return waitHelper.waitForTestId(testId).getText();
    }

    /**
     * Check if element is displayed
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Check if element exists by test ID
     */
    protected boolean isElementDisplayedByTestId(String testId) {
        try {
            By locator = By.cssSelector("[data-testid='" + testId + "']");
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Get all elements by locator
     */
    protected List<WebElement> getElements(By locator) {
        waitHelper.waitForElementVisible(locator);
        return driver.findElements(locator);
    }

    /**
     * Select dropdown option by visible text
     */
    protected void selectDropdownByText(By locator, String text) {
        WebElement element = waitHelper.waitForElementVisible(locator);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    /**
     * Select dropdown option by value
     */
    protected void selectDropdownByValue(By locator, String value) {
        WebElement element = waitHelper.waitForElementVisible(locator);
        Select select = new Select(element);
        select.selectByValue(value);
    }

    /**
     * Scroll to element
     */
    protected void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Wait for page load
     */
    public void waitForPageLoad() {
        waitHelper.waitForPageLoad();
    }

    /**
     * Navigate to URL
     */
    protected void navigateTo(String url) {
        driver.get(url);
        waitForPageLoad();
    }

    /**
     * Get current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Refresh page
     */
    protected void refreshPage() {
        driver.navigate().refresh();
        waitForPageLoad();
    }

    /**
     * Execute JavaScript
     */
    protected Object executeScript(String script) {
        return js.executeScript(script);
    }

    /**
     * Clear localStorage
     */
    protected void clearLocalStorage() {
        js.executeScript("localStorage.clear();");
    }

    /**
     * Get localStorage item
     */
    protected String getLocalStorageItem(String key) {
        return (String) js.executeScript("return localStorage.getItem('" + key + "');");
    }

    /**
     * Set localStorage item
     */
    protected void setLocalStorageItem(String key, String value) {
        js.executeScript("localStorage.setItem('" + key + "', '" + value + "');");
    }

    /**
     * Get element by test ID
     */
    protected WebElement getElementByTestId(String testId) {
        return waitHelper.waitForTestId(testId);
    }

    /**
     * Get cart count from header
     */
    public int getCartCount() {
        return header.getCartCount();
    }

    /**
     * Navigate using header menu
     */
    public void navigateToHome() {
        header.clickHomeLink();
    }

    public void navigateToShop() {
        header.clickShopLink();
    }

    public void navigateToCart() {
        header.clickCartLink();
    }

    public void navigateToContact() {
        header.clickContactLink();
    }
}
