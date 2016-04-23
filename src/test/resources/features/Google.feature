@search
Feature: As a user I'm able to search in Google.

  Scenario: Search for phrase in Google
    Given I open google main page
    When I search for "Facebook"
    Then verify that page "Facebook" is in results
    When I research for "Twitter"
    Then verify that page "Twitter" is in results