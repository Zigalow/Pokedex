Feature: Pokemon Retrieval
  Scenario: The user has clicked on Bulbasaur, and now the data has to be loaded
    Given the user wants to get the pokemon with id 1
    Then the data of the pokemon should be retrieved
    And the name of the pokemon should be 'Bulbasaur'

  Scenario: The user has clicked on Mew, and now the data has to be loaded
    Given the user wants to get the pokemon with id 151
    Then the data of the pokemon should be retrieved
    And the name of the pokemon should be 'Mew'
