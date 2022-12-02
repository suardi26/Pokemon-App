package com.practice.pokemon_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "my_pokemon"
)
data class MyPokemon(
    @PrimaryKey
    var id: Int,
    val name: String?,
    var nickname: String?,
    val type: String?,
    val move: String?,
    val height: Int,
    val weight: Int,
    val url: String?
) : Serializable