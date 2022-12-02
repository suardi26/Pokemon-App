package com.practice.pokemon_app.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.practice.pokemon_app.data.local.MyPokemon
import com.practice.pokemon_app.data.remote.response.Pokemon
import com.practice.pokemon_app.data.remote.response.Result
import com.practice.pokemon_app.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository

) : ViewModel() {

    fun getPokemonList(): LiveData<PagingData<Result>> {
        return repository.getPokemonList().cachedIn(viewModelScope)
    }

    private val _pokemonDetail = MutableLiveData<Pokemon>()
    val pokemonDetail: LiveData<Pokemon> get() = _pokemonDetail

    fun getPokemon(name: String) {
        viewModelScope.launch {
            val response = repository.getPokemonDetail(name)
            if (response.isSuccessful) _pokemonDetail.postValue(response.body())
        }
    }

    fun getAllMyPokemon(): LiveData<List<MyPokemon>> {
        return repository.getAllMyPokemon()
    }

    fun addPokemon(pokemon: MyPokemon){
        viewModelScope.launch {
            repository.addMyPokemon(pokemon)
        }
    }

    fun deleteMyPokemon(pokemon: MyPokemon){
        viewModelScope.launch {
            repository.deletePokemon(pokemon)
        }
    }


    private val _poke = MutableLiveData<Long>()
    val poke: LiveData<Long>  get() = _poke

    fun checkIdMyPokemon(id: Int){
        viewModelScope.launch {
            _poke.postValue(repository.checkPokemon(id))
        }
    }


}