package dtu.group21.models.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dtu.group21.models.pokemon.ComplexPokemon

@Database(entities = [ComplexPokemon::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}