package com.perfume.automation.stepdefinitions;

import com.perfume.automation.pages.*;
import com.perfume.automation.utils.BrowserFactory;
import com.perfume.automation.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Common step definitions shared across multiple feature files
 * Manages WebDriver instance and page objects
 */
public class CommonSteps {

    private WebDriver driver;
    private String baseUrl;

    // Page Objects
    private HomePage homePage;
    private ShopPage shopPage;
    private CartPage cartPage;
    private ContactPage contactPage;
    private ProductDetailPage productDetailPage;

    // Shared state
    private int initialCartCount;
    private int initialItemCount;

    public CommonSteps() {
        this.driver = BrowserFactory.getDriver();
        this.baseUrl = ConfigReader.getInstance().getBaseUrl();
    }

    // Getters for page objects (to be used by other step definition classes)
    public WebDriver getDriver() {
        return driver;
    }

    public HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage(driver);
        }
        return homePage;
    }

    public ShopPage getShopPage() {
        if (shopPage == null) {
            shopPage = new ShopPage(driver);
        }
        return shopPage;
    }

    public CartPage getCartPage() {
        if (cartPage == null) {
            cartPage = new CartPage(driver);
        }
        return cartPage;
    }

    public ContactPage getContactPage() {
        if (contactPage == null) {
            contactPage = new ContactPage(driver);
        }
        return contactPage;
    }

    public ProductDetailPage getProductDetailPage() {
        if (productDetailPage == null) {
            productDetailPage = new ProductDetailPage(driver);
        }
        return productDetailPage;
    }

    // State management
    public int getInitialCartCount() {
        return initialCartCount;
    }

    public void setInitialCartCount(int count) {
        this.initialCartCount = count;
    }

    public int getInitialItemCount() {
        return initialItemCount;
    }

    public void setInitialItemCount(int count) {
        this.initialItemCount = count;
    }

    // Common Given steps
    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        getHomePage().navigateToHomePage(baseUrl);
    }

    @Given("I am on the shop page")
    public void iAmOnTheShopPage() {
        getShopPage().navigateToShopPage(baseUrl);
    }

    @Given("I am on the cart page")
    public void iAmOnTheCartPage() {
        getCartPage().navigateToCartPage(baseUrl);
    }

    @Given("I am on the contact page")
    public void iAmOnTheContactPage() {
        getContactPage().navigateToContactPage(baseUrl);
    }

    @Given("I am on the product detail page for product {string}")
    public void iAmOnTheProductDetailPageForProduct(String productId) {
        getProductDetailPage().navigateToProductDetail(baseUrl, productId);
    }

    // Common navigation steps
    @Given("I navigate to home page via header")
    public void iNavigateToHomePageViaHeader() {
        getHomePage().navigateToHome();
        getHomePage().waitForPageLoad();
    }

    @Given("I navigate to shop page via header")
    public void iNavigateToShopPageViaHeader() {
        getShopPage().navigateToShop();
        getShopPage().waitForPageLoad();
    }

    @Given("I navigate to cart page via header")
    public void iNavigateToCartPageViaHeader() {
        getCartPage().navigateToCart();
        getCartPage().waitForPageLoad();
    }

    // Common Then steps
    @Then("I should be on the home page")
    public void iShouldBeOnTheHomePage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("index.html"));
    }

    @Then("I should be on the shop page")
    public void iShouldBeOnTheShopPage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("shop.html"));
    }

    @Then("I should be on the cart page")
    public void iShouldBeOnTheCartPage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"));
    }

    @Then("I should be on the contact page")
    public void iShouldBeOnTheContactPage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("contact.html"));
    }

    @Then("I should be on the product detail page")
    public void iShouldBeOnTheProductDetailPage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("product-detail.html"));
    }

    // Cart count steps
    @Then("the cart count should be {int}")
    public void theCartCountShouldBe(int expectedCount) {
        int actualCount = getHomePage().getCartCount();
        Assert.assertEquals(actualCount, expectedCount, "Cart count mismatch");
    }

    @Then("the cart count should increase to {int}")
    public void theCartCountShouldIncreaseTo(int expectedCount) {
        int actualCount = getHomePage().getCartCount();
        Assert.assertEquals(actualCount, expectedCount, "Cart count did not increase as expected");
    }

    @Then("the cart count should increase by {int}")
    public void theCartCountShouldIncreaseBy(int increment) {
        int actualCount = getHomePage().getCartCount();
        int expectedCount = initialCartCount + increment;
        Assert.assertEquals(actualCount, expectedCount, "Cart count increment mismatch");
    }

    @Then("the cart count should be greater than {int}")
    public void theCartCountShouldBeGreaterThan(int minCount) {
        int actualCount = getHomePage().getCartCount();
        Assert.assertTrue(actualCount > minCount, "Cart count is not greater than " + minCount);
    }

    @Then("the cart count should remain the same")
    public void theCartCountShouldRemainTheSame() {
        int currentCount = getHomePage().getCartCount();
        Assert.assertEquals(currentCount, initialCartCount, "Cart count changed unexpectedly");
    }

    @Given("I get the cart count")
    public void iGetTheCartCount() {
        initialCartCount = getHomePage().getCartCount();
    }

    @Given("I clear the cart via localStorage")
    public void iClearTheCartViaLocalStorage() {
        getCartPage().clearCartViaLocalStorage();
    }

    @Given("I refresh the page")
    public void iRefreshThePage() {
        driver.navigate().refresh();
        getHomePage().waitForPageLoad();
    }
}
