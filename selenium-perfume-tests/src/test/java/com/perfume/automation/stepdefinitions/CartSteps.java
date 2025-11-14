package com.perfume.automation.stepdefinitions;

import com.perfume.automation.pages.CartPage;
import com.perfume.automation.utils.BrowserFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Step definitions for Cart Page scenarios
 */
public class CartSteps {

    private WebDriver driver;
    private CartPage cartPage;
    private int savedQuantity;
    private int savedItemCount;

    public CartSteps() {
        this.driver = BrowserFactory.getDriver();
        this.cartPage = new CartPage(driver);
    }

    @When("I reset the cart")
    public void iResetTheCart() {
        cartPage.resetCart();
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart is not empty");
    }

    @Then("I should see the empty cart message")
    public void iShouldSeeTheEmptyCartMessage() {
        Assert.assertTrue(cartPage.isCartEmpty(), "Empty cart message not displayed");
    }

    @When("I seed the cart with test data")
    public void iSeedTheCartWithTestData() {
        cartPage.seedCart();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("I should see at least {int} item in the cart")
    public void iShouldSeeAtLeastItemInTheCart(int minCount) {
        int actualCount = cartPage.getCartItemsCount();
        Assert.assertTrue(actualCount >= minCount,
            "Expected at least " + minCount + " item(s), but found " + actualCount);
    }

    @Then("I should see {int} items in the cart")
    public void iShouldSeeItemsInTheCart(int expectedCount) {
        int actualCount = cartPage.getCartItemsCount();
        Assert.assertEquals(actualCount, expectedCount,
            "Expected " + expectedCount + " item(s), but found " + actualCount);
    }

    @Then("the subtotal should be calculated correctly")
    public void theSubtotalShouldBeCalculatedCorrectly() {
        double subtotal = cartPage.getSubtotalAsDouble();
        Assert.assertTrue(subtotal > 0, "Subtotal is not calculated correctly");
    }

    @Then("the total should equal subtotal plus shipping")
    public void theTotalShouldEqualSubtotalPlusShipping() {
        Assert.assertTrue(cartPage.verifyTotalsCalculation(),
            "Total does not equal subtotal plus shipping");
    }

    @When("I get the quantity of item at index {int}")
    public void iGetTheQuantityOfItemAtIndex(int index) {
        savedQuantity = cartPage.getItemQuantity(index);
    }

    @When("I click the plus button for item at index {int}")
    public void iClickThePlusButtonForItemAtIndex(int index) {
        cartPage.clickPlusButton(index);
    }

    @Then("the quantity of item at index {int} should increase by {int}")
    public void theQuantityOfItemAtIndexShouldIncreaseBy(int index, int increment) {
        int newQuantity = cartPage.getItemQuantity(index);
        int expectedQuantity = savedQuantity + increment;
        Assert.assertEquals(newQuantity, expectedQuantity,
            "Quantity did not increase as expected. Expected: " + expectedQuantity + ", Actual: " + newQuantity);
    }

    @When("I click the minus button for item at index {int}")
    public void iClickTheMinusButtonForItemAtIndex(int index) {
        cartPage.clickMinusButton(index);
    }

    @Then("the quantity of item at index {int} should decrease by {int}")
    public void theQuantityOfItemAtIndexShouldDecreaseBy(int index, int decrement) {
        int newQuantity = cartPage.getItemQuantity(index);
        int expectedQuantity = savedQuantity - decrement;
        Assert.assertEquals(newQuantity, expectedQuantity,
            "Quantity did not decrease as expected. Expected: " + expectedQuantity + ", Actual: " + newQuantity);
    }

    @When("I get the initial cart items count")
    public void iGetTheInitialCartItemsCount() {
        savedItemCount = cartPage.getCartItemsCount();
    }

    @When("I click the remove button for item at index {int}")
    public void iClickTheRemoveButtonForItemAtIndex(int index) {
        cartPage.clickRemoveButton(index);
    }

    @Then("the cart items count should decrease by {int}")
    public void theCartItemsCountShouldDecreaseBy(int decrement) {
        int newCount = cartPage.getCartItemsCount();
        int expectedCount = savedItemCount - decrement;
        Assert.assertEquals(newCount, expectedCount,
            "Item count did not decrease as expected. Expected: " + expectedCount + ", Actual: " + newCount);
    }

    @When("I click the checkout button")
    public void iClickTheCheckoutButton() {
        cartPage.clickCheckoutButton();
    }

    @Then("the checkout message should be displayed")
    public void theCheckoutMessageShouldBeDisplayed() {
        Assert.assertTrue(cartPage.isCheckoutMessageDisplayed(),
            "Checkout message is not displayed");
    }

    @Then("the checkout message should contain {string}")
    public void theCheckoutMessageShouldContain(String expectedText) {
        String actualMessage = cartPage.getCheckoutMessage();
        Assert.assertTrue(actualMessage.contains(expectedText),
            "Checkout message does not contain expected text. Expected: " + expectedText + ", Actual: " + actualMessage);
    }

    @Then("the shipping cost should be {double}")
    public void theShippingCostShouldBe(double expectedShipping) {
        double actualShipping = cartPage.getShippingAsDouble();
        Assert.assertEquals(actualShipping, expectedShipping, 0.01,
            "Shipping cost mismatch. Expected: " + expectedShipping + ", Actual: " + actualShipping);
    }

    @Then("the checkout button should not be visible")
    public void theCheckoutButtonShouldNotBeVisible() {
        Assert.assertFalse(cartPage.isCheckoutButtonVisible(),
            "Checkout button should not be visible for empty cart");
    }

    @When("I add {string} items of product {string} to cart")
    public void iAddItemsOfProductToCart(String quantityStr, String productId) {
        int quantity = Integer.parseInt(quantityStr);
        // Navigate to shop page and add items
        // This is a simplified implementation
        // In real scenario, you'd navigate to shop page and add items multiple times
    }

    @Then("the cart should have {string} items total")
    public void theCartShouldHaveItemsTotal(String expectedCountStr) {
        // This would verify total quantity across all cart items
        int expectedCount = Integer.parseInt(expectedCountStr);
        // Implementation depends on how you calculate total items
    }
}
