package com.perfume.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Model for Product Detail Page (product-detail.html)
 * Contains product information, pricing, and add to cart functionality
 */
public class ProductDetailPage extends BasePage {

    // Locators using data-testid
    private By productDetailSection = By.cssSelector("[data-testid='product-detail-section']");
    private By productName = By.cssSelector("[data-testid='product-detail-name']");
    private By productBrand = By.cssSelector("[data-testid='product-detail-brand']");
    private By productRating = By.cssSelector("[data-testid='product-detail-rating']");
    private By productOriginalPrice = By.cssSelector("[data-testid='product-detail-original-price']");
    private By productDiscountedPrice = By.cssSelector("[data-testid='product-detail-discounted-price']");
    private By productDescription = By.cssSelector("[data-testid='product-detail-description']");
    private By addToCartBtn = By.cssSelector("[data-testid='product-detail-add-to-cart-btn']");

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to product detail page by product ID
     */
    public ProductDetailPage navigateToProductDetail(String baseUrl, String productId) {
        String productDetailUrl = baseUrl.replace("index.html", "product-detail.html?id=" + productId);
        navigateTo(productDetailUrl);
        waitForPageLoad();
        waitHelper.waitForHeaderLoaded();
        return this;
    }

    /**
     * Wait for product details to load
     */
    public ProductDetailPage waitForProductToLoad() {
        waitHelper.waitForProductLoaded();
        return this;
    }

    /**
     * Check if product detail section is displayed
     */
    public boolean isProductDetailSectionDisplayed() {
        return isElementDisplayed(productDetailSection);
    }

    /**
     * Get product name
     */
    public String getProductName() {
        return getText(productName);
    }

    /**
     * Get product brand
     */
    public String getProductBrand() {
        String brandText = getText(productBrand);
        // Extract brand name from "Brand: XYZ" format
        return brandText.replace("Brand:", "").trim();
    }

    /**
     * Get product brand text (full format)
     */
    public String getProductBrandFullText() {
        return getText(productBrand);
    }

    /**
     * Get product rating stars count
     */
    public int getProductRatingStarsCount() {
        String ratingHtml = driver.findElement(productRating).getAttribute("innerHTML");
        // Count filled stars (fa-star class)
        int starCount = 0;
        for (String star : ratingHtml.split("<i")) {
            if (star.contains("fa-star") && !star.contains("fa-star-o")) {
                starCount++;
            }
        }
        return starCount;
    }

    /**
     * Get original price
     */
    public String getOriginalPrice() {
        return getText(productOriginalPrice);
    }

    /**
     * Get original price as double
     */
    public double getOriginalPriceAsDouble() {
        String priceText = getOriginalPrice();
        return parsePrice(priceText);
    }

    /**
     * Get discounted price
     */
    public String getDiscountedPrice() {
        return getText(productDiscountedPrice);
    }

    /**
     * Get discounted price as double
     */
    public double getDiscountedPriceAsDouble() {
        String priceText = getDiscountedPrice();
        return parsePrice(priceText);
    }

    /**
     * Parse price string to double
     */
    private double parsePrice(String priceText) {
        return Double.parseDouble(priceText.replace("₪", "").trim());
    }

    /**
     * Get product description
     */
    public String getProductDescription() {
        return getText(productDescription);
    }

    /**
     * Check if product description contains text
     */
    public boolean doesDescriptionContain(String text) {
        String description = getProductDescription();
        return description.toLowerCase().contains(text.toLowerCase());
    }

    /**
     * Check if add to cart button is displayed
     */
    public boolean isAddToCartButtonDisplayed() {
        return isElementDisplayed(addToCartBtn);
    }

    /**
     * Click add to cart button
     */
    public ProductDetailPage clickAddToCartButton() {
        click(addToCartBtn);
        // Wait for cart count update
        waitHelper.waitForCartUpdated();
        return this;
    }

    /**
     * Add product to cart and verify cart count increases
     */
    public ProductDetailPage addProductToCartAndVerify() {
        int initialCartCount = header.getCartCount();
        clickAddToCartButton();
        waitHelper.waitForCartUpdated();

        // Verify cart count increased
        int newCartCount = header.getCartCount();
        if (newCartCount != initialCartCount + 1) {
            throw new AssertionError("Cart count did not increase after adding product. " +
                    "Expected: " + (initialCartCount + 1) + ", Actual: " + newCartCount);
        }
        return this;
    }

    /**
     * Verify discount is applied correctly (70% discount = pay 30% of original)
     */
    public boolean isDiscountAppliedCorrectly() {
        double originalPrice = getOriginalPriceAsDouble();
        double discountedPrice = getDiscountedPriceAsDouble();

        // Expected discount is 70% OFF (pay 30% of original price = 0.3 multiplier)
        double expectedDiscountedPrice = originalPrice * 0.3;

        // Allow small floating point difference
        return Math.abs(discountedPrice - expectedDiscountedPrice) < 0.01;
    }

    /**
     * Get discount percentage
     */
    public double getDiscountPercentage() {
        double originalPrice = getOriginalPriceAsDouble();
        double discountedPrice = getDiscountedPriceAsDouble();

        if (originalPrice == 0) {
            return 0;
        }

        double discount = originalPrice - discountedPrice;
        return (discount / originalPrice) * 100;
    }

    /**
     * Check if product image is displayed
     */
    public boolean isProductImageDisplayed() {
        try {
            return driver.findElement(By.cssSelector("#product-image img.main-image")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get product image source URL
     */
    public String getProductImageSrc() {
        try {
            return driver.findElement(By.cssSelector("#product-image img.main-image")).getAttribute("src");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Verify all product information is displayed
     */
    public boolean isAllProductInfoDisplayed() {
        return isProductDetailSectionDisplayed() &&
               isProductImageDisplayed() &&
               !getProductName().isEmpty() &&
               !getProductBrand().isEmpty() &&
               !getOriginalPrice().isEmpty() &&
               !getDiscountedPrice().isEmpty() &&
               isAddToCartButtonDisplayed();
    }

    /**
     * Get add to cart button text
     */
    public String getAddToCartButtonText() {
        return getText(addToCartBtn);
    }

    /**
     * Get product data-id from add to cart button
     */
    public String getProductIdFromButton() {
        return driver.findElement(addToCartBtn).getAttribute("data-id");
    }

    /**
     * Get product data-name from add to cart button
     */
    public String getProductNameFromButton() {
        return driver.findElement(addToCartBtn).getAttribute("data-name");
    }

    /**
     * Get product data-price from add to cart button
     */
    public String getProductPriceFromButton() {
        return driver.findElement(addToCartBtn).getAttribute("data-price");
    }

    /**
     * Verify button data attributes match displayed information
     */
    public boolean doButtonAttributesMatchDisplayedInfo() {
        String buttonName = getProductNameFromButton();
        String displayedName = getProductName();

        String buttonPrice = getProductPriceFromButton();
        double displayedPrice = getDiscountedPriceAsDouble();

        boolean nameMatches = buttonName.equals(displayedName);
        boolean priceMatches = Math.abs(Double.parseDouble(buttonPrice) - displayedPrice) < 0.01;

        return nameMatches && priceMatches;
    }
}
