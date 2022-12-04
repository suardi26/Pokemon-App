package com.practice.pokemon_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.pokemon_app.data.remote.response.Result
import com.practice.pokemon_app.databinding.ListPokemonBinding
import com.practice.pokemon_app.util.Constants

class PokemonListAdapter :
    PagingDataAdapter<Result, PokemonListAdapter.PokemonViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class PokemonViewHolder(private val binding: ListPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            with(binding) {

                // https://pokeapi.co/api/v2/pokemon/1/  -> number = 1
                val pokeEntries: Int = result.url!!.dropLast(1)
                    .substringAfterLast("/")
                    .toInt()

                val baseUrlImage = Constants.baseUrlImage(pokeEntries)

                // binding data Result
                resultBindData = Result(name = result.name, url = baseUrlImage)

                root.setOnClickListener {
                    onItemClickListener?.let { it(result.name!!) }
                }
            }
        }

    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val result = getItem(position)
        result?.let { holder.bind(result) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListPokemonBinding.inflate(layoutInflater, parent, false)
        return PokemonViewHolder(binding)
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
}

