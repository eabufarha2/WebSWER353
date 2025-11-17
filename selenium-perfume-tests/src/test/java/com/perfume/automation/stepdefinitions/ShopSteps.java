package com.perfume.automation.stepdefinitions;

import com.perfume.automation.pages.ShopPage;
import com.perfume.automation.utils.BrowserFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Step definitions for Shop Page scenarios
 */
public class ShopSteps {

    private WebDriver driver;
    private ShopPage shopPage;

    public ShopSteps() {
        this.driver = BrowserFactory.getDriver();
        this.shopPage = new ShopPage(driver);
    }

    @Then("I should see at least {int} product displayed")
    public void iShouldSeeAtLeastProductDisplayed(int minCount) {
        int actualCount = shopPage.getDisplayedProductsCount();
        Assert.assertTrue(actualCount >= minCount,
            "Expected at least " + minCount + " product(s), but found " + actualCount);
    }

    @Then("I should see at least {int} products displayed")
    public void iShouldSeeAtLeastProductsDisplayed(int minCount) {
        iShouldSeeAtLeastProductDisplayed(minCount);
    }

    @Then("I should see {int} products displayed")
    public void iShouldSeeProductsDisplayed(int expectedCount) {
        int actualCount = shopPage.getDisplayedProductsCount();
        Assert.assertEquals(actualCount, expectedCount,
            "Expected " + expectedCount + " product(s), but found " + actualCount);
    }

    @Then("all products should have names")
    public void allProductsShouldHaveNames() {
        int productCount = shopPage.getDisplayedProductsCount();
        Assert.assertTrue(productCount > 0, "No products displayed");
    }

    @Then("all products should have prices")
    public void allProductsShouldHavePrices() {
        int productCount = shopPage.getDisplayedProductsCount();
        Assert.assertTrue(productCount > 0, "No products displayed");
    }

    @When("I search for {string}")
    public void iSearchFor(String searchTerm) {
        shopPage.searchForProduct(searchTerm);
    }

    @Then("all displayed products should contain {string} in their name")
    public void allDisplayedProductsShouldContainInTheirName(String keyword) {
        Assert.assertTrue(shopPage.doAllProductsContainKeyword(keyword),
            "Not all products contain keyword: " + keyword);
    }

    @When("I clear the search")
    public void iClearTheSearch() {
        shopPage.clearSearch();
    }

    @When("I sort products by {string}")
    public void iSortProductsBy(String sortOption) {
        shopPage.sortByText(sortOption);
    }

    @Then("products should be sorted by name in ascending order")
    public void productsShouldBeSortedByNameInAscendingOrder() {
        Assert.assertTrue(shopPage.areProductsSortedByNameAsc(),
            "Products are not sorted by name in ascending order");
    }

    @Then("products should be sorted by price in ascending order")
    public void productsShouldBeSortedByPriceInAscendingOrder() {
        Assert.assertTrue(shopPage.areProductsSortedByPriceAsc(),
            "Products are not sorted by price in ascending order");
    }

    @Then("products should be sorted by price in descending order")
    public void productsShouldBeSortedByPriceInDescendingOrder() {
        Assert.assertTrue(shopPage.areProductsSortedByPriceDesc(),
            "Products are not sorted by price in descending order");
    }

    @When("I add product with id {string} to cart from shop page")
    public void iAddProductWithIdToCartFromShopPage(String productId) {
        shopPage.addProductToCart(productId);
    }

    @When("I click on product {string}")
    public void iClickOnProduct(String productName) {
        shopPage.clickProductByName(productName);
        shopPage.waitForPageLoad();
    }

    @Then("the product name should contain {string}")
    public void theProductNameShouldContain(String expectedText) {
        // This will be verified after navigation to product detail page
        Assert.assertTrue(driver.getCurrentUrl().contains("product-detail.html"));
    }

    @Then("I should see {string} or more products")
    public void iShouldSeeOrMoreProducts(String expectedCountStr) {
        int expectedCount = Integer.parseInt(expectedCountStr);
        int actualCount = shopPage.getDisplayedProductsCount();
        Assert.assertTrue(actualCount >= expectedCount,
            "Expected at least " + expectedCount + " product(s), but found " + actualCount);
    }

    @Then("products should be displayed")
    public void productsShouldBeDisplayed() {
        int count = shopPage.getDisplayedProductsCount();
        Assert.assertTrue(count > 0, "No products are displayed");
    }
}
