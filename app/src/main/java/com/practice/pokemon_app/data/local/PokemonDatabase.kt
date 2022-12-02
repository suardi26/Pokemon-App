package com.practice.pokemon_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MyPokemon::class],
    version = 1
)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao
}