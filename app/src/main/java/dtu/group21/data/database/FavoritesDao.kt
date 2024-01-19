package dtu.group21.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAll(): List<FavoriteData>

    @Query("SELECT * FROM favorites WHERE id IN (:pokemonIds)")
    fun getPokemonById(vararg pokemonIds: Int): List<FavoriteData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokemons: FavoriteData)

    @Delete
    fun delete(pokemon: FavoriteData)
}