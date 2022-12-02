package com.practice.pokemon_app.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.practice.pokemon_app.R
import com.practice.pokemon_app.adapter.PokemonListAdapter
import com.practice.pokemon_app.data.local.MyPokemon
import com.practice.pokemon_app.data.remote.response.Move
import com.practice.pokemon_app.data.remote.response.Type
import com.practice.pokemon_app.data.viewmodel.PokemonListViewModel
import com.practice.pokemon_app.databinding.FragmentPokemonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : Fragment(R.layout.fragment_pokemon) {

    private lateinit var binding: FragmentPokemonBinding
    private val pokemonListAdapter = PokemonListAdapter()
    private val viewModel: PokemonListViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPokemonBinding.bind(view)
        setupRecyclerView()

        viewModel.getPokemonList().observe(viewLifecycleOwner) {
            pokemonListAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        pokemonListAdapter.setOnItemClickListener { name ->

            viewModel.getPokemon(name)
            Log.v("TestMain0", name)

            viewModel.pokemonDetail.observe(viewLifecycleOwner) { poke ->
                Log.v("TestMain1", poke.id.toString())

                val action =
                    PokemonListFragmentDirections.actionPokemonListFragmentToDetailPokemonFragment(

                        myPokemon = MyPokemon(
                            id = poke.id!!,
                            name = poke.name,
                            type = getDataType(poke.types!!),
                            move = getDataMove(poke.moves!!),
                            height = poke.height!!,
                            weight = poke.weight!!,
                            url = poke.sprites?.front_default,
                            nickname = null
                        )
                    )

                findNavController().navigate(action)
            }

        }

    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerViewPokemonList.setHasFixedSize(true)
            recyclerViewPokemonList.layoutManager = GridLayoutManager(activity, 2)
            recyclerViewPokemonList.adapter = pokemonListAdapter
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