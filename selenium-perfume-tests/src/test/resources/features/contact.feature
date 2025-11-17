Feature: Contact Form Functionality
  As a customer
  I want to submit a contact form
  So that I can communicate with the company

  Background:
    Given I am on the contact page

  Scenario: Submit valid contact form with all fields
    Then the contact form should be displayed
    And all form fields should be present
    And the submit button should be displayed
    And the submit button should be enabled
    And the name field should have required attribute
    And the email field should have required attribute
    And the subject field should have required attribute
    And the message field should have required attribute
    When I fill the contact form with:
      | name    | John Doe                    |
      | email   | john.doe@example.com        |
      | subject | Product Inquiry             |
      | message | I would like more info      |
    And I submit the contact form
    Then the form feedback should be displayed
    When I fill the contact form with:
      | name    | Test User                                |
      | email   | test@example.com                         |
      | subject | Special Characters Test                  |
      | message | Testing: @#$%^&*()_+-=[]{}semicolon.,<>? |
    And I submit the contact form
    Then the form should be processed successfully

  Scenario: Form validation with valid data passes
    When I fill the contact form with:
      | name    | Valid User                |
      | email   | valid@example.com         |
      | subject | Valid Subject Line        |
      | message | This is a valid message with more than ten characters |
    And I submit the contact form
    Then the form feedback should be displayed

  Scenario: Verify required fields validation
    When I submit the contact form without filling any field
    Then the form should not be submitted
    And validation errors should be shown
