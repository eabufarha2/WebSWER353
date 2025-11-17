package com.perfume.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object Model for Cart Page (cart.html)
 * Contains cart items, quantity management, and checkout functionality
 */
public class CartPage extends BasePage {

    // Locators using data-testid
    private By cartItemsSection = By.cssSelector("[data-testid='cart-items-section']");
    private By cartItemRows = By.cssSelector("[data-testid='cart-item-row']");
    private By cartItemNames = By.cssSelector("[data-testid='cart-item-link']");
    private By cartItemPrices = By.cssSelector("[data-testid='cart-item-price']");
    private By cartItemQuantities = By.cssSelector("[data-testid='cart-item-qty']");
    private By qtyMinusButtons = By.cssSelector("[data-testid='qty-minus-btn']");
    private By qtyPlusButtons = By.cssSelector("[data-testid='qty-plus-btn']");
    private By removeButtons = By.cssSelector("[data-testid='cart-remove-btn']");
    private By cartSubtotal = By.cssSelector("[data-testid='cart-subtotal']");
    private By cartShipping = By.cssSelector("[data-testid='cart-shipping']");
    private By cartTotal = By.cssSelector("[data-testid='cart-total']");
    private By checkoutBtn = By.cssSelector("[data-testid='checkout-btn']");
    private By checkoutMessage = By.cssSelector("[data-testid='checkout-message']");
    private By resetCartBtn = By.cssSelector("[data-testid='reset-cart-btn']");
    private By seedCartBtn = By.cssSelector("[data-testid='seed-cart-btn']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to cart page
     */
    public CartPage navigateToCartPage(String baseUrl) {
        String cartUrl = baseUrl.replace("index.html", "cart.html");
        navigateTo(cartUrl);
        waitForPageLoad();
        waitHelper.waitForHeaderLoaded();
        return this;
    }

    /**
     * Wait for cart to update
     */
    public CartPage waitForCartUpdate() {
        waitHelper.waitForCartUpdated();
        return this;
    }

    /**
     * Get number of items in cart
     */
    public int getCartItemsCount() {
        try {
            List<WebElement> rows = driver.findElements(cartItemRows);
            return rows.size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Check if cart is empty
     */
    public boolean isCartEmpty() {
        String pageText = getText(cartItemsSection);
        return pageText.contains("Your Cart is empty");
    }

    /**
     * Get cart subtotal
     */
    public String getSubtotal() {
        return getText(cartSubtotal);
    }

    /**
     * Get cart subtotal as double
     */
    public double getSubtotalAsDouble() {
        String subtotalText = getSubtotal();
        return parsePrice(subtotalText);
    }

    /**
     * Get shipping cost
     */
    public String getShipping() {
        return getText(cartShipping);
    }

    /**
     * Get shipping cost as double
     */
    public double getShippingAsDouble() {
        String shippingText = getShipping();
        return parsePrice(shippingText);
    }

    /**
     * Get cart total
     */
    public String getTotal() {
        return getText(cartTotal);
    }

    /**
     * Get cart total as double
     */
    public double getTotalAsDouble() {
        String totalText = getTotal();
        return parsePrice(totalText);
    }

    /**
     * Parse price string to double
     */
    private double parsePrice(String priceText) {
        return Double.parseDouble(priceText.replace("₪", "").trim());
    }

    /**
     * Get quantity of specific cart item by index
     */
    public int getItemQuantity(int itemIndex) {
        List<WebElement> quantities = driver.findElements(cartItemQuantities);
        if (itemIndex < quantities.size()) {
            String qtyText = quantities.get(itemIndex).getText().trim();
            return Integer.parseInt(qtyText);
        }
        return 0;
    }

    /**
     * Click plus button for specific item
     */
    public CartPage clickPlusButton(int itemIndex) {
        List<WebElement> plusButtons = driver.findElements(qtyPlusButtons);
        if (itemIndex < plusButtons.size()) {
            plusButtons.get(itemIndex).click();
            waitHelper.waitForUIUpdate();
        }
        return this;
    }

    /**
     * Click minus button for specific item
     */
    public CartPage clickMinusButton(int itemIndex) {
        List<WebElement> minusButtons = driver.findElements(qtyMinusButtons);
        if (itemIndex < minusButtons.size()) {
            minusButtons.get(itemIndex).click();
            waitHelper.waitForUIUpdate();
        }
        return this;
    }

    /**
     * Click remove button for specific item
     */
    public CartPage clickRemoveButton(int itemIndex) {
        List<WebElement> removeBtn = driver.findElements(removeButtons);
        if (itemIndex < removeBtn.size()) {
            removeBtn.get(itemIndex).click();
            waitHelper.waitForUIUpdate();
        }
        return this;
    }

    /**
     * Get product name by index
     */
    public String getProductName(int itemIndex) {
        List<WebElement> names = driver.findElements(cartItemNames);
        if (itemIndex < names.size()) {
            return names.get(itemIndex).getText();
        }
        return "";
    }

    /**
     * Get product price by index
     */
    public String getProductPrice(int itemIndex) {
        List<WebElement> prices = driver.findElements(cartItemPrices);
        if (itemIndex < prices.size()) {
            return prices.get(itemIndex).getText();
        }
        return "";
    }

    /**
     * Check if product exists in cart by name
     */
    public boolean isProductInCart(String productName) {
        List<WebElement> names = driver.findElements(cartItemNames);
        return names.stream()
                .anyMatch(name -> name.getText().equalsIgnoreCase(productName));
    }

    /**
     * Click checkout button
     */
    public CartPage clickCheckoutButton() {
        click(checkoutBtn);
        waitHelper.waitForElementVisible(checkoutMessage);
        return this;
    }

    /**
     * Get checkout message
     */
    public String getCheckoutMessage() {
        return getText(checkoutMessage);
    }

    /**
     * Check if checkout message is displayed
     */
    public boolean isCheckoutMessageDisplayed() {
        return isElementDisplayed(checkoutMessage);
    }

    /**
     * Check if checkout button is visible
     */
    public boolean isCheckoutButtonVisible() {
        return isElementDisplayed(checkoutBtn);
    }

    /**
     * Reset cart using dev tool button
     */
    public CartPage resetCart() {
        click(resetCartBtn);
        waitHelper.waitForUIUpdate();
        refreshPage();
        return this;
    }

    /**
     * Seed cart with test data using dev tool button
     */
    public CartPage seedCart() {
        click(seedCartBtn);
        waitHelper.waitForCartUpdated();
        return this;
    }

    /**
     * Clear cart via localStorage
     */
    public CartPage clearCartViaLocalStorage() {
        executeScript("localStorage.removeItem('cart');");
        refreshPage();
        return this;
    }

    /**
     * Verify cart totals calculation
     * Subtotal + Shipping = Total
     */
    public boolean verifyTotalsCalculation() {
        double subtotal = getSubtotalAsDouble();
        double shipping = getShippingAsDouble();
        double total = getTotalAsDouble();
        double expectedTotal = subtotal + shipping;

        // Allow small floating point difference
        return Math.abs(total - expectedTotal) < 0.01;
    }

    /**
     * Get all product names in cart
     */
    public List<String> getAllProductNames() {
        List<WebElement> names = driver.findElements(cartItemNames);
        return names.stream()
                .map(WebElement::getText)
                .collect(java.util.stream.Collectors.toList());
    }
}
