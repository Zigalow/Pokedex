package dtu.group21.models.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemons")
    fun getAll(): List<PokemonData>

    @Query("SELECT * FROM pokemons WHERE id IN (:pokemonIds)")
    fun getPokemonById(vararg pokemonIds: Int): List<PokemonData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokemons: PokemonData)

    @Delete
    fun delete(pokemon: PokemonData)
}