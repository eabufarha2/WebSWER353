package com.perfume.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object Model for Shop Page (shop.html)
 * Contains search, sort, and product display functionality
 */
public class ShopPage extends BasePage {

    // Locators using data-testid
    private By searchInput = By.cssSelector("[data-testid='search-input']");
    private By sortSelect = By.cssSelector("[data-testid='sort-select']");
    private By shopProductsContainer = By.cssSelector("[data-testid='shop-products-container']");
    private By productCards = By.cssSelector("[data-testid='product-card']");
    private By productNames = By.cssSelector("[data-testid='product-name-link']");
    private By productPrices = By.cssSelector("[data-testid='discounted-price']");

    public ShopPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to shop page
     */
    public ShopPage navigateToShopPage(String baseUrl) {
        String shopUrl = baseUrl.replace("index.html", "shop.html");
        navigateTo(shopUrl);
        waitForPageLoad();
        waitHelper.waitForHeaderLoaded();
        return this;
    }

    /**
     * Wait for products to load
     */
    public ShopPage waitForProductsToLoad() {
        waitHelper.waitForProductsLoaded();
        return this;
    }

    /**
     * Search for products
     */
    public ShopPage searchForProduct(String searchTerm) {
        enterText(searchInput, searchTerm);
        // Wait for search results to stabilize
        waitHelper.waitForProductsLoaded();
        return this;
    }

    /**
     * Clear search input and trigger filter update
     */
    public ShopPage clearSearch() {
        WebElement search = driver.findElement(searchInput);
        search.clear();
        // Trigger input event to update product display
        executeScript("document.getElementById('searchInput').dispatchEvent(new Event('input'));");
        // Wait for products to reload
        waitHelper.waitForProductsLoaded();
        return this;
    }

    /**
     * Sort products by option
     * Options: "nameAsc", "nameDesc", "priceAsc", "priceDesc"
     */
    public ShopPage sortProductsBy(String sortOption) {
        selectDropdownByValue(sortSelect, sortOption);
        // Wait for products to be re-sorted
        waitHelper.waitForProductsLoaded();
        return this;
    }

    /**
     * Sort by visible text
     */
    public ShopPage sortByText(String sortText) {
        selectDropdownByText(sortSelect, sortText);
        // Wait for products to be re-sorted
        waitHelper.waitForProductsLoaded();
        return this;
    }

    /**
     * Get count of displayed products
     */
    public int getDisplayedProductsCount() {
        WebElement container = driver.findElement(shopProductsContainer);
        List<WebElement> products = container.findElements(productCards);
        return products.size();
    }

    /**
     * Get all product names
     */
    public List<String> getAllProductNames() {
        List<WebElement> nameElements = getElements(productNames);
        return nameElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get all product prices
     */
    public List<String> getAllProductPrices() {
        List<WebElement> priceElements = getElements(productPrices);
        return priceElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get all product prices as double values
     */
    public List<Double> getAllProductPricesAsDouble() {
        List<String> prices = getAllProductPrices();
        return prices.stream()
                .map(price -> Double.parseDouble(price.replace("₪", "").trim()))
                .collect(Collectors.toList());
    }

    /**
     * Check if products are sorted by name ascending
     */
    public boolean areProductsSortedByNameAsc() {
        List<String> names = getAllProductNames();
        for (int i = 0; i < names.size() - 1; i++) {
            if (names.get(i).compareTo(names.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if products are sorted by price ascending
     */
    public boolean areProductsSortedByPriceAsc() {
        List<Double> prices = getAllProductPricesAsDouble();
        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) > prices.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if products are sorted by price descending
     */
    public boolean areProductsSortedByPriceDesc() {
        List<Double> prices = getAllProductPricesAsDouble();
        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) < prices.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if search results contain keyword
     */
    public boolean doAllProductsContainKeyword(String keyword) {
        List<String> names = getAllProductNames();
        if (names.isEmpty()) {
            return false;
        }

        return names.stream()
                .allMatch(name -> name.toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Add product to cart by product ID
     */
    public ShopPage addProductToCart(String productId) {
        By addToCartBtn = By.cssSelector("[data-testid='add-to-cart-btn-" + productId + "']");
        click(addToCartBtn);
        // Wait for cart count update
        waitHelper.waitForCartUpdated();
        return this;
    }

    /**
     * Add product to cart by product name
     */
    public ShopPage addProductToCartByName(String productName) {
        List<WebElement> products = getElements(productCards);
        for (WebElement product : products) {
            String name = product.findElement(By.cssSelector("[data-testid='product-name-link']")).getText();
            if (name.equalsIgnoreCase(productName)) {
                product.findElement(By.cssSelector("a.cart")).click();
                // Wait for cart count update
                waitHelper.waitForCartUpdated();
                break;
            }
        }
        return this;
    }

    /**
     * Click on product to view details
     */
    public void clickProductByName(String productName) {
        List<WebElement> products = getElements(productCards);
        for (WebElement product : products) {
            String name = product.findElement(By.cssSelector("[data-testid='product-name-link']")).getText();
            if (name.equalsIgnoreCase(productName)) {
                product.findElement(By.cssSelector("[data-testid='product-name-link']")).click();
                break;
            }
        }
    }

    /**
     * Get first product name
     */
    public String getFirstProductName() {
        List<String> names = getAllProductNames();
        return names.isEmpty() ? "" : names.get(0);
    }

    /**
     * Get last product name
     */
    public String getLastProductName() {
        List<String> names = getAllProductNames();
        return names.isEmpty() ? "" : names.get(names.size() - 1);
    }

    /**
     * Get first product price
     */
    public String getFirstProductPrice() {
        List<String> prices = getAllProductPrices();
        return prices.isEmpty() ? "" : prices.get(0);
    }

    /**
     * Get last product price
     */
    public String getLastProductPrice() {
        List<String> prices = getAllProductPrices();
        return prices.isEmpty() ? "" : prices.get(prices.size() - 1);
    }
}
