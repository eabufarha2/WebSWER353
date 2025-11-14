Feature: Shop Page Functionality
  As a customer
  I want to search and sort products
  So that I can find the products I'm looking for

  Background:
    Given I am on the shop page
    And I wait for products to load

  Scenario: Verify shop page displays products
    Then I should see at least 1 product displayed
    And all products should have names
    And all products should have prices

  Scenario Outline: Search for products by keyword
    When I search for "<keyword>"
    Then all displayed products should contain "<keyword>" in their name

    Examples:
      | keyword |
      | Bloom   |
      | Essence |
      | Future  |

  Scenario: Clear search returns all products
    When I search for "Bloom"
    And I clear the search
    Then I should see at least 10 products displayed

  Scenario: Sort products by name ascending
    When I sort products by "Name (A-Z)"
    Then products should be sorted by name in ascending order

  Scenario: Sort products by price ascending
    When I sort products by "Price (Low-High)"
    Then products should be sorted by price in ascending order

  Scenario: Sort products by price descending
    When I sort products by "Price (High-Low)"
    Then products should be sorted by price in descending order

  Scenario: Add product to cart from shop page
    When I add product with id "P2" to cart from shop page
    Then the cart count should increase by 1

  Scenario: Navigate to product detail from shop page
    When I click on product "Future Essence Perfume"
    Then I should be on the product detail page
    And the product name should contain "Future Essence"

  Scenario Outline: Verify search with different keywords
    When I search for "<searchTerm>"
    Then I should see "<expectedCount>" or more products

    Examples:
      | searchTerm | expectedCount |
      | Eternal    | 1             |
      | Perfume    | 5             |

  Scenario: Verify no products found message
    When I search for "NonExistentProduct12345"
    Then I should see 0 products displayed
