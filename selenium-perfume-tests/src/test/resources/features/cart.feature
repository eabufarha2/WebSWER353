Feature: Shopping Cart Functionality
  As a customer
  I want to manage my shopping cart
  So that I can review and modify my order before checkout

  Background:
    Given I am on the cart page

  Scenario: Empty cart state and messaging
    When I reset the cart
    Then the cart should be empty
    And I should see the empty cart message
    And the checkout button should not be visible

  Scenario: Cart persistence across navigation
    When I seed the cart with test data
    And I get the cart count
    And I navigate to home page via header
    Then the cart count should remain the same
    When I navigate to shop page via header
    Then the cart count should remain the same
    When I navigate to cart page via header
    Then I should see at least 1 item in the cart

  Scenario: Cart totals calculation and shipping cost
    When I seed the cart with test data
    Then the subtotal should be calculated correctly
    And the shipping cost should be 5.00
    And the total should equal subtotal plus shipping
    When I click the checkout button
    Then the checkout message should be displayed
    And the checkout message should contain "Thank You"

  Scenario Outline: Cart quantity operations - increase, decrease, remove
    When I reset the cart
    And I add "<initialQuantity>" items of product "P1" to cart
    And I navigate to cart page via header
    Then the cart should have "<initialQuantity>" items total
    When I get the quantity of item at index 0
    And I click the plus button for item at index 0
    Then the quantity of item at index 0 should increase by 1
    When I get the quantity of item at index 0
    And I click the minus button for item at index 0
    Then the quantity of item at index 0 should decrease by 1
    When I get the initial cart items count
    And I click the remove button for item at index 0
    Then the cart items count should decrease by 1

    Examples:
      | initialQuantity |
      | 3               |
      | 5               |
