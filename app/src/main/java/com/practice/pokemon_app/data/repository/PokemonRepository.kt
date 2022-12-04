package com.practice.pokemon_app.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.practice.pokemon_app.data.local.MyPokemon
import com.practice.pokemon_app.data.local.PokemonDao
import com.practice.pokemon_app.data.remote.api.PokeApi
import com.practice.pokemon_app.data.remote.response.Pokemon
import com.practice.pokemon_app.data.remote.response.Result
import com.practice.pokemon_app.util.Constants.PAGE_SIZE
import retrofit2.Call
import retrofit2.Response

class PokemonRepository(
    private val api: PokeApi,
    private val pokemonDao: PokemonDao
) {

    fun getPokemonList(): LiveData<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokePagingSource(api) }
        ).liveData
    }

    suspend fun getPokemonDetail(name: String): Response<Pokemon> {
        return api.getPokemonInfo(name)
    }

    fun getAllMyPokemon(): LiveData<List<MyPokemon>> {
        return pokemonDao.getAllPokemon()
    }

    suspend fun checkPokemon(id: Int): Long {
        return pokemonDao.checkPokemon(id)
    }

    suspend fun addMyPokemon(pokemon: MyPokemon): Long {
        return pokemonDao.insertPokemon(pokemon)
    }

    suspend fun deletePokemon(pokemon: MyPokemon) {
        pokemonDao.deletePokemon(pokemon)
    }


}