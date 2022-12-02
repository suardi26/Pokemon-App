package com.practice.pokemon_app.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.practice.pokemon_app.R
import com.practice.pokemon_app.data.local.MyPokemon
import com.practice.pokemon_app.data.viewmodel.PokemonListViewModel
import com.practice.pokemon_app.databinding.FragmentPokemonDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPokemonFragment : Fragment(R.layout.fragment_pokemon_detail) {

    private lateinit var binding: FragmentPokemonDetailBinding
    private val viewModel: PokemonListViewModel by activityViewModels()
    private val args: DetailPokemonFragmentArgs by navArgs<DetailPokemonFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPokemonDetailBinding.bind(view)

        val myPokemon = args.myPokemon
        val nickname = if (myPokemon.nickname != null) "(${myPokemon.nickname})" else ""

        checkPokemon(myPokemon.id)

        binding.apply {

            myPokemonDataBinding = MyPokemon(
                id = myPokemon.id,
                name = "#${myPokemon.id} ${myPokemon.name}",
                type = myPokemon.type,
                move = myPokemon.move,
                height = myPokemon.height,
                weight = myPokemon.weight,
                url = myPokemon.url,
                nickname = nickname
            )
            heightBinding = "Height : ${myPokemon.height} M"
            weightBinding = "Weight : ${myPokemon.weight} Kg"

            buttonCatch.setOnClickListener {

                // Probability 50 % (Ganjil-Genap)
                val rand = (1..10).shuffled().last()
                Log.v("TestRand", rand.toString())

                if (rand % 2 == 1) {
                    cardViewCatch.visibility = View.GONE
                    cardViewAdd.visibility = View.VISIBLE
                    Toast.makeText(activity, "You have caught a Pokemon !!", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(activity, "Try Next Time !!", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_detailPokemonFragment_to_pokemonListFragment)
                }
            }

            buttonAdd.setOnClickListener {
                if (editTextAddNickname.text.toString().isNotBlank()) {
                    viewModel.addPokemon(
                        MyPokemon(
                            id = myPokemon.id,
                            name = myPokemon.name,
                            type = myPokemon.type,
                            move = myPokemon.move,
                            height = myPokemon.height,
                            weight = myPokemon.weight,
                            url = myPokemon.url,
                            nickname = editTextAddNickname.text.toString()
                        )
                    )
                    findNavController().navigate(R.id.action_detailPokemonFragment_to_pokemonListFragment)
                    Toast.makeText(activity, "Pokemon saved Successfully", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(activity, "Please Enter Nickname !!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }


    private fun checkPokemon(id: Int) {

        viewModel.checkIdMyPokemon(id)
        viewModel.poke.observe(viewLifecycleOwner) {
            val pokemonCount = it.toInt()

            Log.v("Test Data", pokemonCount.toString())

            if (pokemonCount < 1) {
                binding.apply {
                    cardViewCatch.visibility = View.VISIBLE
                    cardViewAdd.visibility = View.GONE
                    buttonCatch.visibility = View.VISIBLE
                    textViewInfo.visibility = View.VISIBLE
                    messageBinding = "Info : Probability 50 %"
                }
            } else {
                binding.apply {
                    cardViewCatch.visibility = View.VISIBLE
                    cardViewAdd.visibility = View.GONE
                    buttonCatch.visibility = View.GONE
                    textViewInfo.visibility = View.VISIBLE
                    messageBinding = "Collect"
                }

            }
        }
    }

}