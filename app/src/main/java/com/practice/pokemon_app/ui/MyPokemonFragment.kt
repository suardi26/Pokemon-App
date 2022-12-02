package com.practice.pokemon_app.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.practice.pokemon_app.R
import com.practice.pokemon_app.adapter.MyPokemonListAdapter
import com.practice.pokemon_app.data.viewmodel.PokemonListViewModel
import com.practice.pokemon_app.databinding.FragmentMyPokemonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPokemonFragment : Fragment(R.layout.fragment_my_pokemon) {

    private lateinit var binding: FragmentMyPokemonBinding
    private val viewModel: PokemonListViewModel by activityViewModels()
    private val myPokemonListAdapter = MyPokemonListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPokemonBinding.bind(view)
        setupRecyclerView()

        myPokemonListAdapter.setOnItemClickListener {

            val action = MyPokemonFragmentDirections.actionMyPokemonFragmentToDetailPokemonFragment(
                myPokemon = it
            )
            findNavController().navigate(action)
        }

        viewModel.getAllMyPokemon().observe(viewLifecycleOwner) {
            myPokemonListAdapter.differ.submitList(it)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val myPokemon = myPokemonListAdapter.differ.currentList[position]
                viewModel.deleteMyPokemon(myPokemon)
                Snackbar.make(view, "Successful Release Of Pokemon", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        viewModel.addPokemon(myPokemon)
                    }
                    .show()

            }

        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerViewMyPokemonList)

    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerViewMyPokemonList.setHasFixedSize(true)
            recyclerViewMyPokemonList.layoutManager = GridLayoutManager(activity, 2)
            recyclerViewMyPokemonList.adapter = myPokemonListAdapter
        }
    }
}