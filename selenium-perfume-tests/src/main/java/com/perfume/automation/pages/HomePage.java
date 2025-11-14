package com.perfume.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object Model for Home Page (index.html)
 * Contains featured products and new arrivals sections
 */
public class HomePage extends BasePage {

    // Locators using data-testid
    private By heroSection = By.cssSelector("[data-testid='hero-section']");
    private By heroShopNowBtn = By.cssSelector("[data-testid='hero-shop-now-btn']");
    private By featuredSection = By.cssSelector("[data-testid='featured-section']");
    private By featuredProductsContainer = By.cssSelector("[data-testid='featured-products-container']");
    private By newArrivalsSection = By.cssSelector("[data-testid='new-arrivals-section']");
    private By newArrivalsContainer = By.cssSelector("[data-testid='new-arrivals-container']");
    private By bannerSection = By.cssSelector("[data-testid='banner-section']");
    private By bannerExploreBtn = By.cssSelector("[data-testid='banner-explore-btn']");
    private By productCards = By.cssSelector("[data-testid='product-card']");
    private By addToCartButtons = By.cssSelector("a.cart");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to home page
     */
    public HomePage navigateToHomePage(String baseUrl) {
        navigateTo(baseUrl);
        waitForPageLoad();
        waitHelper.waitForHeaderLoaded();
        return this;
    }

    /**
     * Wait for products to be loaded on home page
     */
    public HomePage waitForProductsToLoad() {
        waitHelper.waitForProductsLoaded();
        return this;
    }

    /**
     * Check if hero section is displayed
     */
    public boolean isHeroSectionDisplayed() {
        return isElementDisplayed(heroSection);
    }

    /**
     * Get hero section text
     */
    public String getHeroSectionText() {
        return getText(heroSection);
    }

    /**
     * Click Shop Now button in hero section
     */
    public void clickHeroShopNowButton() {
        click(heroShopNowBtn);
    }

    /**
     * Get count of featured products
     */
    public int getFeaturedProductsCount() {
        WebElement container = driver.findElement(featuredProductsContainer);
        List<WebElement> products = container.findElements(productCards);
        return products.size();
    }

    /**
     * Get count of new arrivals
     */
    public int getNewArrivalsCount() {
        WebElement container = driver.findElement(newArrivalsContainer);
        List<WebElement> products = container.findElements(productCards);
        return products.size();
    }

    /**
     * Get all featured products
     */
    public List<WebElement> getFeaturedProducts() {
        WebElement container = driver.findElement(featuredProductsContainer);
        return container.findElements(productCards);
    }

    /**
     * Get all new arrival products
     */
    public List<WebElement> getNewArrivals() {
        WebElement container = driver.findElement(newArrivalsContainer);
        return container.findElements(productCards);
    }

    /**
     * Click add to cart for specific product by ID
     */
    public HomePage addProductToCart(String productId) {
        By addToCartBtn = By.cssSelector("[data-testid='add-to-cart-btn-" + productId + "']");
        click(addToCartBtn);
        // Wait for notification or cart count update
        try {
            Thread.sleep(500); // Small wait for animation
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Click on product card to view details
     */
    public void clickProductByName(String productName) {
        List<WebElement> products = getElements(productCards);
        for (WebElement product : products) {
            if (product.getText().contains(productName)) {
                product.findElement(By.cssSelector("[data-testid='product-name-link']")).click();
                break;
            }
        }
    }

    /**
     * Check if discount badge is visible on products
     */
    public boolean areDiscountBadgesVisible() {
        List<WebElement> products = getElements(productCards);
        if (products.isEmpty()) {
            return false;
        }

        for (WebElement product : products) {
            List<WebElement> badges = product.findElements(By.cssSelector("[data-testid='discount-badge']"));
            if (badges.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get discount badge text
     */
    public String getDiscountBadgeText() {
        WebElement badge = driver.findElement(By.cssSelector("[data-testid='discount-badge']"));
        return badge.getText();
    }

    /**
     * Check if banner section is displayed
     */
    public boolean isBannerSectionDisplayed() {
        return isElementDisplayed(bannerSection);
    }

    /**
     * Get banner text
     */
    public String getBannerText() {
        return getText(bannerSection);
    }

    /**
     * Verify all page sections are loaded
     */
    public boolean areAllSectionsLoaded() {
        return isHeroSectionDisplayed() &&
               getFeaturedProductsCount() > 0 &&
               getNewArrivalsCount() > 0 &&
               isBannerSectionDisplayed();
    }
}
