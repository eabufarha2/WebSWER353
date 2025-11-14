Feature: Contact Form Functionality
  As a customer
  I want to submit a contact form
  So that I can communicate with the company

  Background:
    Given I am on the contact page

  Scenario: Verify contact form is displayed
    Then the contact form should be displayed
    And all form fields should be present

  Scenario: Submit valid contact form
    When I fill the contact form with:
      | name    | John Doe                    |
      | email   | john.doe@example.com        |
      | subject | Product Inquiry             |
      | message | I would like more info      |
    And I submit the contact form
    Then the form feedback should be displayed

  Scenario Outline: Validate email field with various inputs
    When I enter email "<email>"
    And I enter name "Test User"
    And I enter subject "Test"
    And I enter message "This is a test message"
    And I submit the contact form
    Then the email field validation should be "<validationResult>"

    Examples:
      | email                  | validationResult |
      | valid@example.com      | valid            |
      | test.email@domain.co   | valid            |
      | invalid-email          | invalid          |
      | @example.com           | invalid          |
      | user@                  | invalid          |

  Scenario: Verify required fields validation
    When I submit the contact form without filling any field
    Then the form should not be submitted
    And validation errors should be shown

  Scenario Outline: Validate name field with minimum length
    When I enter name "<name>"
    Then the name field should be "<validationResult>"

    Examples:
      | name       | validationResult |
      | A          | invalid          |
      | AB         | valid            |
      | John Doe   | valid            |

  Scenario Outline: Validate subject field with minimum length
    When I enter subject "<subject>"
    Then the subject field should be "<validationResult>"

    Examples:
      | subject        | validationResult |
      | AB             | invalid          |
      | ABC            | valid            |
      | Product Inquiry| valid            |

  Scenario Outline: Validate message field with minimum length
    When I enter message "<message>"
    Then the message field should be "<validationResult>"

    Examples:
      | message                  | validationResult |
      | Short                    | invalid          |
      | This is a valid message  | valid            |

  Scenario: Clear form after submission
    When I fill the contact form with:
      | name    | Jane Smith              |
      | email   | jane@example.com        |
      | subject | Feedback                |
      | message | Great products!         |
    And I clear the contact form
    Then all form fields should be empty

  Scenario: Verify submit button is enabled
    Then the submit button should be displayed
    And the submit button should be enabled

  Scenario: Verify form field attributes
    Then the name field should have required attribute
    And the email field should have required attribute
    And the subject field should have required attribute
    And the message field should have required attribute

  Scenario: Submit form with special characters in message
    When I fill the contact form with:
      | name    | Test User                                |
      | email   | test@example.com                         |
      | subject | Special Characters Test                  |
      | message | Testing: @#$%^&*()_+-=[]{}semicolon.,<>? |
    And I submit the contact form
    Then the form should be processed successfully
