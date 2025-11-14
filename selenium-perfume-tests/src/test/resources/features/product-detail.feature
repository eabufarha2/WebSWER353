Feature: Product Detail Page Functionality
  As a customer
  I want to view detailed product information
  So that I can make informed purchase decisions

  Background:
    Given I am on the product detail page for product "P1"
    And I wait for product to load

  Scenario: Verify product detail page displays all information
    Then the product name should be displayed
    And the product brand should be displayed
    And the product rating should be displayed
    And the product original price should be displayed
    And the product discounted price should be displayed
    And the product description should be displayed
    And the product image should be displayed

  Scenario: Verify product pricing information
    Then the original price should be greater than discounted price
    And the discount should be approximately 70 percent

  Scenario: Add product to cart from detail page
    When I click the add to cart button on product detail
    Then the cart count should increase by 1

  Scenario: Verify add to cart button attributes
    Then the add to cart button should have product id
    And the add to cart button should have product name
    And the add to cart button should have product price
    And button attributes should match displayed information

  Scenario: Verify product image is loaded
    Then the product image should be displayed
    And the product image source should not be empty

  Scenario Outline: Navigate to different product details
    Given I am on the product detail page for product "<productId>"
    And I wait for product to load
    Then the product detail page should load successfully
    And all product information should be displayed

    Examples:
      | productId |
      | P1        |
      | P2        |
      | P3        |

  Scenario: Verify discount calculation accuracy
    When I get the original price
    And I get the discounted price
    Then the discount calculation should be accurate

  Scenario: Add product multiple times increases cart count
    When I click the add to cart button on product detail
    And I click the add to cart button on product detail
    Then the cart count should increase by 2

  Scenario: Navigate back to shop from product detail
    When I navigate to shop page via header
    Then I should be on the shop page
    And products should be displayed

  Scenario: Verify product description is not empty
    Then the product description should not be empty
    And the product description should contain text

  Scenario: Verify rating stars are displayed
    Then the product rating should show at least 1 star
    And the product rating should show at most 5 stars

  Scenario: Verify product brand format
    Then the product brand should start with "Brand:"
    And the brand name should not be empty
