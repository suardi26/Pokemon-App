package com.practice.pokemon_app.util

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val PAGE_SIZE = 20

    fun baseUrlImage(number: Int): String{
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png"
    }
}