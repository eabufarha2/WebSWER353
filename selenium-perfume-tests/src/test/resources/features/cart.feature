Feature: Shopping Cart Functionality
  As a customer
  I want to manage my shopping cart
  So that I can review and modify my order before checkout

  Background:
    Given I am on the cart page

  Scenario: Verify empty cart message
    When I reset the cart
    Then the cart should be empty
    And I should see the empty cart message

  Scenario: Seed cart with test data
    When I seed the cart with test data
    Then I should see at least 1 item in the cart
    And the cart count should be greater than 0

  Scenario: Verify cart totals calculation
    When I seed the cart with test data
    Then the subtotal should be calculated correctly
    And the total should equal subtotal plus shipping

  Scenario: Increase product quantity
    When I seed the cart with test data
    And I get the quantity of item at index 0
    And I click the plus button for item at index 0
    Then the quantity of item at index 0 should increase by 1

  Scenario: Decrease product quantity
    When I seed the cart with test data
    And I get the quantity of item at index 0
    And I click the minus button for item at index 0
    Then the quantity of item at index 0 should decrease by 1

  Scenario: Remove product from cart
    When I seed the cart with test data
    And I get the initial cart items count
    And I click the remove button for item at index 0
    Then the cart items count should decrease by 1

  Scenario: Checkout with items in cart
    When I seed the cart with test data
    And I click the checkout button
    Then the checkout message should be displayed
    And the checkout message should contain "Thank You"

  Scenario: Verify cart persistence across pages
    When I seed the cart with test data
    And I get the cart count
    And I navigate to home page via header
    Then the cart count should remain the same

  Scenario: Add multiple products and verify total
    When I reset the cart
    And I navigate to shop page via header
    And I add product with id "P1" to cart from shop page
    And I add product with id "P2" to cart from shop page
    And I navigate to cart page via header
    Then I should see 2 items in the cart

  Scenario: Verify shipping cost is constant
    When I seed the cart with test data
    Then the shipping cost should be 5.00

  Scenario: Clear cart via dev tools
    When I seed the cart with test data
    And I reset the cart
    Then the cart should be empty
    And the checkout button should not be visible

  Scenario Outline: Verify cart calculations with different quantities
    When I reset the cart
    And I add "<quantity>" items of product "P1" to cart
    And I navigate to cart page via header
    Then the cart should have "<quantity>" items total

    Examples:
      | quantity |
      | 1        |
      | 3        |
      | 5        |
