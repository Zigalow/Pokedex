Feature: Pokemon Retrieval
  Scenario: The user has clicked on Bulbasaur, and now the data has to be loaded
    Given the user wants to get the pokemon with id 1
    Then the data of the pokemon should be retrieved
    And the name of the pokemon should be 'Bulbasaur'

  Scenario: The user has clicked on Mew, and now the data has to be loaded
    Given the user wants to get the pokemon with id 151
    Then the data of the pokemon should be retrieved
    And the name of the pokemon should be 'Mew'

    Scenario: The user has opened the app and data has to be loaded to display on the frontpage
      Given the user wants to get the pokemons with ids '7,65,871'
      Then the data of the pokemons should be retrieved

      Scenario: The user is on the inspect page and clicked the Evolution tab, and now the data has to be loaded
        Given the user wants to inspect evolution chain of pokemons with ids '1,2,3'
        Then the data of the evolution pokemons should be retrieved