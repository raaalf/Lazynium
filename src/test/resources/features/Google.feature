@LoggedIn
Feature: Logged in customer should have item on promo correctly accrued.

  Scenario: Add items from Offers page to basket
    Given a logged in customer
    When Offers page is displayed
    Then add items met promo conditions to trolley