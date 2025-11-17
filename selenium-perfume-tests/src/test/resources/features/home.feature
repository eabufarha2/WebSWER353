Feature: Home Page Functionality
  As a customer
  I want to browse the home page
  So that I can see featured products and new arrivals

  Background:
    Given I am on the home page

  Scenario: Product discovery flow from home page
    When I wait for products to load
    Then the hero section should be displayed
    And the featured products section should be displayed
    And the new arrivals section should be displayed
    And the banner section should be displayed
    And I should see at least 1 featured product
    And I should see at least 1 new arrival product
    And each featured product should have a name
    And each featured product should have a price
    And each new arrival should have a name
    And each new arrival should have a price
    And discount badges should be visible on products
    When I click on a product named "Eternal Bloom Perfume"
    Then I should be on the product detail page
    And the product name should be displayed

  Scenario: Add product to cart from home page
    When I wait for products to load
    And I add product with id "F1" to cart from home page
    Then the cart count should increase to 1
