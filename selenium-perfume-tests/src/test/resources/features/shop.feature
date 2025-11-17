Feature: Shop Page Functionality
  As a customer
  I want to search and sort products
  So that I can find the products I'm looking for

  Background:
    Given I am on the shop page
    And I wait for products to load

  Scenario Outline: Search for products by keyword
    When I search for "<keyword>"
    Then all displayed products should contain "<keyword>" in their name
    And I should see "<expectedCount>" or more products
    When I clear the search
    Then I should see at least 10 products displayed

    Examples:
      | keyword | expectedCount |
      | Bloom   | 1             |
      | Perfume | 5             |

  Scenario Outline: Sort products by price and name
    When I sort products by "<sortOption>"
    Then products should be sorted by <sortType> in <sortOrder> order

    Examples:
      | sortOption        | sortType | sortOrder  |
      | Name (A-Z)        | name     | ascending  |
      | Price (Low-High)  | price    | ascending  |
      | Price (High-Low)  | price    | descending |

  Scenario: Verify no products found message
    When I search for "NonExistentProduct12345"
    Then I should see 0 products displayed
