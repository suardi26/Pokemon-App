package com.practice.pokemon_app.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface PokemonDao {
    @Insert
    suspend fun insertPokemon(myPokemon: MyPokemon): Long

    @Query("SELECT * FROM my_pokemon")
    fun getAllPokemon(): LiveData<List<MyPokemon>>

    @Query("SELECT COUNT(id) FROM my_pokemon WHERE id =:id ")
    suspend fun checkPokemon(id: Int): Long

    @Delete
    suspend fun deletePokemon(pokemon: MyPokemon)

}