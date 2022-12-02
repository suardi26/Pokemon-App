package com.practice.pokemon_app.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.practice.pokemon_app.data.remote.api.PokeApi
import com.practice.pokemon_app.data.remote.response.PokemonList
import com.practice.pokemon_app.data.remote.response.Result
import com.practice.pokemon_app.util.Constants.PAGE_SIZE
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PokePagingSource @Inject constructor(
    private val pokeApi : PokeApi
): PagingSource<Int, Result>() {

    companion object{
        private const val STARTING_PAGE_INDEX = 0
    }
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {

            val position = params.key ?: STARTING_PAGE_INDEX

            val response: Response<PokemonList> =
                pokeApi.getPokemonList(
                    offset = position * PAGE_SIZE,
                    limit = PAGE_SIZE
                )

            val listOfPokemon = response.body()?.results ?: emptyList()
            Log.v("Paging", position.toString())

            Log.v("Data", listOfPokemon.toString())
            LoadResult.Page(
                data = listOfPokemon,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (listOfPokemon.isEmpty()) null else position + 1
            )
        }catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }
}