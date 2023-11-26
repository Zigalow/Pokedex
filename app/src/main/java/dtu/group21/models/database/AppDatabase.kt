package dtu.group21.models.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PokemonData::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    //abstract fun favoritesDao(): FavoritesDao
    abstract fun pokemonDao(): PokemonDao
}