package com.practice.pokemon_app.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.practice.pokemon_app.R
import com.practice.pokemon_app.adapter.PokemonListAdapter
import com.practice.pokemon_app.data.viewmodel.PokemonListViewModel
import com.practice.pokemon_app.databinding.FragmentPokemonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : Fragment(R.layout.fragment_pokemon) {

    private lateinit var binding: FragmentPokemonBinding
    private val pokemonListAdapter = PokemonListAdapter()
    private val viewModel: PokemonListViewModel by activityViewModels()
    private var action: NavDirections? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPokemonBinding.bind(view)
        setupRecyclerView()

        viewModel.getPokemonList().observe(viewLifecycleOwner) {
            pokemonListAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.pokemonDetail.observe(viewLifecycleOwner) { myPoke ->
            myPoke?.let {
                action =
                    PokemonListFragmentDirections.actionPokemonListFragmentToDetailPokemonFragment(
                        myPokemon = myPoke
                    )
            }
            action?.let { findNavController().navigate(it) }

        }

        pokemonListAdapter.setOnItemClickListener { name ->
            viewModel.getPokemon(name)
        }

    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerViewPokemonList.setHasFixedSize(true)
            recyclerViewPokemonList.layoutManager = GridLayoutManager(activity, 2)
            recyclerViewPokemonList.adapter = pokemonListAdapter
        }
    }


}