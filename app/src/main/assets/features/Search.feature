Feature: Search
  Users should be able to search for a pokemon based on either the name of the Pokemons or their Pokemon id's

  Scenario: Searching for Pokemons with "char" in their name
    Given the user is on the search page
    And all the Pokemons have been loaded
    When the User performs a search and enters the keywords "char" in the search bar
    Then the three Pokémons, "Charmander", "Charmeleon" and "Charizard" should appear.

  Scenario: Searching for Pokemons with the number '1' in their id
    Given the user is on the search page
    And all the Pokemons have been loaded
    When the User performs a search and enters the number '1' in the search bar
    Then the three Pokémons, with Pokemon id "0001", "0011" and "0021" should appear.
    
    

    