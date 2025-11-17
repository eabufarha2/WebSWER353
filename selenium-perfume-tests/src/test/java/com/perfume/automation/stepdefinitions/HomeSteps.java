package com.perfume.automation.stepdefinitions;

import com.perfume.automation.pages.HomePage;
import com.perfume.automation.utils.BrowserFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Step definitions for Home Page scenarios
 */
public class HomeSteps {

    private WebDriver driver;
    private HomePage homePage;

    public HomeSteps() {
        this.driver = BrowserFactory.getDriver();
        this.homePage = new HomePage(driver);
    }

    @When("I wait for products to load")
    public void iWaitForProductsToLoad() {
        homePage.waitForProductsToLoad();
    }

    @Then("the hero section should be displayed")
    public void theHeroSectionShouldBeDisplayed() {
        Assert.assertTrue(homePage.isHeroSectionDisplayed(), "Hero section is not displayed");
    }

    @Then("the featured products section should be displayed")
    public void theFeaturedProductsSectionShouldBeDisplayed() {
        int count = homePage.getFeaturedProductsCount();
        Assert.assertTrue(count > 0, "Featured products section is not displayed");
    }

    @Then("the new arrivals section should be displayed")
    public void theNewArrivalsSectionShouldBeDisplayed() {
        int count = homePage.getNewArrivalsCount();
        Assert.assertTrue(count > 0, "New arrivals section is not displayed");
    }

    @Then("the banner section should be displayed")
    public void theBannerSectionShouldBeDisplayed() {
        Assert.assertTrue(homePage.isBannerSectionDisplayed(), "Banner section is not displayed");
    }

    @Then("I should see at least {int} featured product")
    public void iShouldSeeAtLeastFeaturedProduct(int minCount) {
        int actualCount = homePage.getFeaturedProductsCount();
        Assert.assertTrue(actualCount >= minCount,
            "Expected at least " + minCount + " featured product(s), but found " + actualCount);
    }

    @Then("each featured product should have a name")
    public void eachFeaturedProductShouldHaveAName() {
        Assert.assertTrue(homePage.getFeaturedProductsCount() > 0,
            "Featured products should have names");
    }

    @Then("each featured product should have a price")
    public void eachFeaturedProductShouldHaveAPrice() {
        Assert.assertTrue(homePage.getFeaturedProductsCount() > 0,
            "Featured products should have prices");
    }

    @Then("I should see at least {int} new arrival product")
    public void iShouldSeeAtLeastNewArrivalProduct(int minCount) {
        int actualCount = homePage.getNewArrivalsCount();
        Assert.assertTrue(actualCount >= minCount,
            "Expected at least " + minCount + " new arrival(s), but found " + actualCount);
    }

    @Then("each new arrival should have a name")
    public void eachNewArrivalShouldHaveAName() {
        Assert.assertTrue(homePage.getNewArrivalsCount() > 0,
            "New arrivals should have names");
    }

    @Then("each new arrival should have a price")
    public void eachNewArrivalShouldHaveAPrice() {
        Assert.assertTrue(homePage.getNewArrivalsCount() > 0,
            "New arrivals should have prices");
    }

    @When("I add product with id {string} to cart from home page")
    public void iAddProductWithIdToCartFromHomePage(String productId) {
        homePage.addProductToCart(productId);
    }

    @When("I click the Shop Now button in hero section")
    public void iClickTheShopNowButtonInHeroSection() {
        homePage.clickHeroShopNowButton();
        homePage.waitForPageLoad();
    }

    @Then("discount badges should be visible on products")
    public void discountBadgesShouldBeVisibleOnProducts() {
        Assert.assertTrue(homePage.areDiscountBadgesVisible(),
            "Discount badges are not visible on products");
    }

    @When("I click on a product named {string}")
    public void iClickOnAProductNamed(String productName) {
        homePage.clickProductByName(productName);
        homePage.waitForPageLoad();
    }
}
