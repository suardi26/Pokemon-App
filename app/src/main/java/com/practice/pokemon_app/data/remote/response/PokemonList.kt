package com.practice.pokemon_app.data.remote.response

data class PokemonList(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    val results: List<Result>?
)