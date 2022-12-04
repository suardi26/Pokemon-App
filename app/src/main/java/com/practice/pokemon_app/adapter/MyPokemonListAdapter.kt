package com.practice.pokemon_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.pokemon_app.data.local.MyPokemon
import com.practice.pokemon_app.data.remote.response.Result
import com.practice.pokemon_app.databinding.ListPokemonBinding

class MyPokemonListAdapter:
    RecyclerView.Adapter<MyPokemonListAdapter.MyPokemonListViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<MyPokemon>() {
        override fun areItemsTheSame(oldItem: MyPokemon, newItem: MyPokemon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyPokemon, newItem: MyPokemon): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    inner class MyPokemonListViewHolder(private val binding: ListPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myPokemon: MyPokemon) {
            binding.apply {
                resultBindData = Result(name = myPokemon.name, url = myPokemon.url)
                root.setOnClickListener {
                    onItemClickListener?.let { it(myPokemon) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPokemonListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListPokemonBinding.inflate(layoutInflater, parent, false)
        return MyPokemonListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPokemonListViewHolder, position: Int) {
        val myPokemon = differ.currentList[position]
        holder.bind(myPokemon)
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener: ((MyPokemon) -> Unit)? = null

    fun setOnItemClickListener(listener: (MyPokemon) -> Unit) {
        onItemClickListener = listener
    }

}