package com.perfume.automation.components;

import com.perfume.automation.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Header component present on all pages
 * Contains navigation menu and cart count
 */
public class Header {

    private WebDriver driver;
    private WaitHelper waitHelper;

    // Locators using data-testid
    private By logoLink = By.cssSelector("[data-testid='logo-link']");
    private By navHome = By.cssSelector("[data-testid='nav-home']");
    private By navShop = By.cssSelector("[data-testid='nav-shop']");
    private By navAbout = By.cssSelector("[data-testid='nav-about']");
    private By navContact = By.cssSelector("[data-testid='nav-contact']");
    private By navCartLink = By.cssSelector("[data-testid='nav-cart-link']");
    private By cartCount = By.cssSelector("[data-testid='cart-count']");
    private By mobileMenuToggle = By.cssSelector("[data-testid='mobile-menu-toggle']");
    private By mobileCloseBtn = By.cssSelector("[data-testid='mobile-close-btn']");

    public Header(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    /**
     * Wait for header to be loaded
     */
    public void waitForHeaderLoaded() {
        waitHelper.waitForHeaderLoaded();
    }

    /**
     * Click Home link in navigation
     */
    public void clickHomeLink() {
        waitHelper.waitForElementClickable(navHome).click();
    }

    /**
     * Click Shop link in navigation
     */
    public void clickShopLink() {
        waitHelper.waitForElementClickable(navShop).click();
    }

    /**
     * Click About link in navigation
     */
    public void clickAboutLink() {
        waitHelper.waitForElementClickable(navAbout).click();
    }

    /**
     * Click Contact link in navigation
     */
    public void clickContactLink() {
        waitHelper.waitForElementClickable(navContact).click();
    }

    /**
     * Click Cart link in navigation
     */
    public void clickCartLink() {
        waitHelper.waitForElementClickable(navCartLink).click();
    }

    /**
     * Get cart count from badge
     * @return cart item count as integer
     */
    public int getCartCount() {
        WebElement countElement = waitHelper.waitForElementVisible(cartCount);
        String countText = countElement.getText().trim();

        // If empty, default to 0
        if (countText.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(countText);
    }

    /**
     * Check if cart count is displayed
     */
    public boolean isCartCountDisplayed() {
        try {
            return driver.findElement(cartCount).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Open mobile menu
     */
    public void openMobileMenu() {
        waitHelper.waitForElementClickable(mobileMenuToggle).click();
    }

    /**
     * Close mobile menu
     */
    public void closeMobileMenu() {
        waitHelper.waitForElementClickable(mobileCloseBtn).click();
    }

    /**
     * Check if navigation link is active
     */
    public boolean isNavLinkActive(String linkName) {
        By linkLocator = null;
        switch (linkName.toLowerCase()) {
            case "home":
                linkLocator = navHome;
                break;
            case "shop":
                linkLocator = navShop;
                break;
            case "about":
                linkLocator = navAbout;
                break;
            case "contact":
                linkLocator = navContact;
                break;
        }

        if (linkLocator != null) {
            WebElement link = driver.findElement(linkLocator);
            String classAttribute = link.getAttribute("class");
            return classAttribute != null && classAttribute.contains("active");
        }
        return false;
    }
}
