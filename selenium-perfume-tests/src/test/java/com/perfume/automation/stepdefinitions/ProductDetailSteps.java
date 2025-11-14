package com.perfume.automation.stepdefinitions;

import com.perfume.automation.pages.ProductDetailPage;
import com.perfume.automation.utils.BrowserFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Step definitions for Product Detail Page scenarios
 */
public class ProductDetailSteps {

    private WebDriver driver;
    private ProductDetailPage productDetailPage;
    private double savedOriginalPrice;
    private double savedDiscountedPrice;

    public ProductDetailSteps() {
        this.driver = BrowserFactory.getDriver();
        this.productDetailPage = new ProductDetailPage(driver);
    }

    @When("I wait for product to load")
    public void iWaitForProductToLoad() {
        productDetailPage.waitForProductToLoad();
    }

    @Then("the product name should be displayed")
    public void theProductNameShouldBeDisplayed() {
        String productName = productDetailPage.getProductName();
        Assert.assertFalse(productName.isEmpty(),
            "Product name should be displayed");
    }

    @Then("the product brand should be displayed")
    public void theProductBrandShouldBeDisplayed() {
        String brand = productDetailPage.getProductBrand();
        Assert.assertFalse(brand.isEmpty(),
            "Product brand should be displayed");
    }

    @Then("the product rating should be displayed")
    public void theProductRatingShouldBeDisplayed() {
        int stars = productDetailPage.getProductRatingStarsCount();
        Assert.assertTrue(stars > 0,
            "Product rating should be displayed");
    }

    @Then("the product original price should be displayed")
    public void theProductOriginalPriceShouldBeDisplayed() {
        String price = productDetailPage.getOriginalPrice();
        Assert.assertFalse(price.isEmpty(),
            "Original price should be displayed");
    }

    @Then("the product discounted price should be displayed")
    public void theProductDiscountedPriceShouldBeDisplayed() {
        String price = productDetailPage.getDiscountedPrice();
        Assert.assertFalse(price.isEmpty(),
            "Discounted price should be displayed");
    }

    @Then("the product description should be displayed")
    public void theProductDescriptionShouldBeDisplayed() {
        String description = productDetailPage.getProductDescription();
        Assert.assertFalse(description.isEmpty(),
            "Product description should be displayed");
    }

    @Then("the product image should be displayed")
    public void theProductImageShouldBeDisplayed() {
        Assert.assertTrue(productDetailPage.isProductImageDisplayed(),
            "Product image should be displayed");
    }

    @Then("the original price should be greater than discounted price")
    public void theOriginalPriceShouldBeGreaterThanDiscountedPrice() {
        double originalPrice = productDetailPage.getOriginalPriceAsDouble();
        double discountedPrice = productDetailPage.getDiscountedPriceAsDouble();
        Assert.assertTrue(originalPrice > discountedPrice,
            "Original price should be greater than discounted price. Original: " + originalPrice + ", Discounted: " + discountedPrice);
    }

    @Then("the discount should be approximately {int} percent")
    public void theDiscountShouldBeApproximatelyPercent(int expectedPercentage) {
        double actualPercentage = productDetailPage.getDiscountPercentage();
        Assert.assertEquals(actualPercentage, expectedPercentage, 1.0,
            "Discount percentage mismatch. Expected: " + expectedPercentage + "%, Actual: " + actualPercentage + "%");
    }

    @When("I click the add to cart button on product detail")
    public void iClickTheAddToCartButtonOnProductDetail() {
        productDetailPage.clickAddToCartButton();
    }

    @Then("the add to cart button should have product id")
    public void theAddToCartButtonShouldHaveProductId() {
        String productId = productDetailPage.getProductIdFromButton();
        Assert.assertFalse(productId.isEmpty(),
            "Add to cart button should have product ID");
    }

    @Then("the add to cart button should have product name")
    public void theAddToCartButtonShouldHaveProductName() {
        String productName = productDetailPage.getProductNameFromButton();
        Assert.assertFalse(productName.isEmpty(),
            "Add to cart button should have product name");
    }

    @Then("the add to cart button should have product price")
    public void theAddToCartButtonShouldHaveProductPrice() {
        String productPrice = productDetailPage.getProductPriceFromButton();
        Assert.assertFalse(productPrice.isEmpty(),
            "Add to cart button should have product price");
    }

    @Then("button attributes should match displayed information")
    public void buttonAttributesShouldMatchDisplayedInformation() {
        Assert.assertTrue(productDetailPage.doButtonAttributesMatchDisplayedInfo(),
            "Button attributes do not match displayed information");
    }

    @Then("the product image source should not be empty")
    public void theProductImageSourceShouldNotBeEmpty() {
        String imageSrc = productDetailPage.getProductImageSrc();
        Assert.assertFalse(imageSrc.isEmpty(),
            "Product image source should not be empty");
    }

    @Then("the product detail page should load successfully")
    public void theProductDetailPageShouldLoadSuccessfully() {
        Assert.assertTrue(productDetailPage.isProductDetailSectionDisplayed(),
            "Product detail page did not load successfully");
    }

    @Then("all product information should be displayed")
    public void allProductInformationShouldBeDisplayed() {
        Assert.assertTrue(productDetailPage.isAllProductInfoDisplayed(),
            "Not all product information is displayed");
    }

    @When("I get the original price")
    public void iGetTheOriginalPrice() {
        savedOriginalPrice = productDetailPage.getOriginalPriceAsDouble();
    }

    @When("I get the discounted price")
    public void iGetTheDiscountedPrice() {
        savedDiscountedPrice = productDetailPage.getDiscountedPriceAsDouble();
    }

    @Then("the discount calculation should be accurate")
    public void theDiscountCalculationShouldBeAccurate() {
        Assert.assertTrue(productDetailPage.isDiscountAppliedCorrectly(),
            "Discount calculation is not accurate");
    }

    @Then("the product description should not be empty")
    public void theProductDescriptionShouldNotBeEmpty() {
        String description = productDetailPage.getProductDescription();
        Assert.assertFalse(description.isEmpty(),
            "Product description should not be empty");
    }

    @Then("the product description should contain text")
    public void theProductDescriptionShouldContainText() {
        String description = productDetailPage.getProductDescription();
        Assert.assertTrue(description.length() > 10,
            "Product description should contain meaningful text");
    }

    @Then("the product rating should show at least {int} star")
    public void theProductRatingShouldShowAtLeastStar(int minStars) {
        int actualStars = productDetailPage.getProductRatingStarsCount();
        Assert.assertTrue(actualStars >= minStars,
            "Product rating should show at least " + minStars + " star(s), but shows " + actualStars);
    }

    @Then("the product rating should show at most {int} stars")
    public void theProductRatingShouldShowAtMostStars(int maxStars) {
        int actualStars = productDetailPage.getProductRatingStarsCount();
        Assert.assertTrue(actualStars <= maxStars,
            "Product rating should show at most " + maxStars + " stars, but shows " + actualStars);
    }

    @Then("the product brand should start with {string}")
    public void theProductBrandShouldStartWith(String expectedPrefix) {
        String brandText = productDetailPage.getProductBrandFullText();
        Assert.assertTrue(brandText.startsWith(expectedPrefix),
            "Product brand should start with '" + expectedPrefix + "', but is: " + brandText);
    }

    @Then("the brand name should not be empty")
    public void theBrandNameShouldNotBeEmpty() {
        String brand = productDetailPage.getProductBrand();
        Assert.assertFalse(brand.isEmpty(),
            "Brand name should not be empty");
    }
}
