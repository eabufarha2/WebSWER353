Feature: Home Page Functionality
  As a customer
  I want to browse the home page
  So that I can see featured products and new arrivals

  Background:
    Given I am on the home page

  Scenario: Verify home page loads successfully
    Then the hero section should be displayed
    And the featured products section should be displayed
    And the new arrivals section should be displayed
    And the banner section should be displayed

  Scenario: Verify featured products are displayed
    When I wait for products to load
    Then I should see at least 1 featured product
    And each featured product should have a name
    And each featured product should have a price

  Scenario: Verify new arrivals are displayed
    When I wait for products to load
    Then I should see at least 1 new arrival product
    And each new arrival should have a name
    And each new arrival should have a price

  Scenario: Add product to cart from home page
    When I wait for products to load
    And I add product with id "F1" to cart from home page
    Then the cart count should increase to 1

  Scenario: Navigate to shop from hero section
    When I click the Shop Now button in hero section
    Then I should be on the shop page

  Scenario: Verify discount badges are visible on products
    When I wait for products to load
    Then discount badges should be visible on products

  Scenario: Navigate to product detail from home page
    When I wait for products to load
    And I click on a product named "Eternal Bloom Perfume"
    Then I should be on the product detail page
    And the product name should be displayed

  Scenario: Verify cart count starts at zero on first visit
    When I clear the cart via localStorage
    And I refresh the page
    Then the cart count should be 0
