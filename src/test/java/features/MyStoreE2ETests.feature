Feature: My Store End-to-End Tests Feature

  Scenario: User should be able to search products, add them to cart and complete checkout process (as an existing user)
    Given User is in landing page of MyStore website
    When User searches and adds single product from each criteria to shopping card
    |Search Criteria|
    |blouse         |
    |dress          |
    |t-shirt        |
    Then User is able to view added 3 products by hovering over the shopping cart
    And User is able to see added 3 products with their prices upon navigating to shopping cart
    And User is able to increase quantity of item 1 by 1 in the cart and adjust the shopping cart accordingly
    And User is able to decrease quantity of item 1 by 1 in the cart and adjust the shopping cart accordingly
    And User is able to remove item 2 from the cart and adjust the shopping cart accordingly
    When User searches and adds single product from each criteria to shopping card
      |Search Criteria|
      |dress         |
    And User is able to see added 3 products with their prices upon navigating to shopping cart
    And User is able to sign in and complete checkout process
    |Test Data File|
    |test001.json  |
    And User is able to navigate to order history and details page and see order with reference number is shown as first record
    And User is able to logout from application
