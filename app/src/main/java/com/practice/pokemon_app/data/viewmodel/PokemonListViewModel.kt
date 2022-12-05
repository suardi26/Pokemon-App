package com.practice.pokemon_app.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.practice.pokemon_app.data.local.MyPokemon
import com.practice.pokemon_app.data.remote.response.Move
import com.practice.pokemon_app.data.remote.response.Result
import com.practice.pokemon_app.data.remote.response.Type
import com.practice.pokemon_app.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository

) : ViewModel() {

    fun getPokemonList(): LiveData<PagingData<Result>> {
        return repository.getPokemonList().cachedIn(viewModelScope)
    }

    val pokemonDetail = SingleLiveEvent<MyPokemon>()

    fun getPokemon(name: String) {
        viewModelScope.launch {
            val response = repository.getPokemonDetail(name)
            if (response.isSuccessful) {
                val pokemon = response.body()
                pokemon?.let {
                    val myPokemon = MyPokemon(
                        id = it.id!!,
                        name = it.name,
                        type = getDataType(it.types!!),
                        move = getDataMove(it.moves!!),
                        height = it.height!!,
                        weight = it.weight!!,
                        url = it.sprites?.front_default,
                        nickname = null
                    )
                    pokemonDetail.postValue(myPokemon)
                }
            }

        }
    }


    fun getAllMyPokemon(): LiveData<List<MyPokemon>> {
        return repository.getAllMyPokemon()
    }

    fun addPokemon(pokemon: MyPokemon) {
        viewModelScope.launch {
            repository.addMyPokemon(pokemon)
        }
    }

    fun deleteMyPokemon(pokemon: MyPokemon) {
        viewModelScope.launch {
            repository.deletePokemon(pokemon)
        }
    }


    private val _poke = MutableLiveData<Long>()
    val poke: LiveData<Long> get() = _poke

    fun checkIdMyPokemon(id: Int) {
        viewModelScope.launch {
            _poke.postValue(repository.checkPokemon(id))
        }
    }

    private fun getDataMove(list: List<Move>): String {
        var stringValue = ""
        list.forEach { moves ->
            val value = moves.move!!.name!!
            if (list[0].move!!.name == value) stringValue = value
            else if (list[3].move!!.name == value) return stringValue
            else stringValue += ", $value"
        }
        return stringValue
    }

    private fun getDataType(list: List<Type>): String {
        var stringValue = ""
        list.forEach { types ->
            val type = types.type!!.name!!
            if (list[0].type!!.name == type) stringValue = type
            else stringValue += ", $type"
        }
        return stringValue
    }

}
