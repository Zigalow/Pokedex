package dtu.group21.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dtu.group21.data.pokemon.DetailedPokemon

@Database(entities = [FavoriteData::class], version = 1)
@TypeConverters(PokemonConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}