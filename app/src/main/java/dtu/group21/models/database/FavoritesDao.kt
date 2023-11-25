package dtu.group21.models.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dtu.group21.models.pokemon.ComplexPokemon

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAll() : List<ComplexPokemon>

    @Query("SELECT * FROM favorites WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<ComplexPokemon>

    @Insert
    fun insertAll(vararg pokemons: ComplexPokemon)

    @Delete
    fun delete(pokemon: ComplexPokemon)

}