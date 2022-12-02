package com.practice.pokemon_app.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.practice.pokemon_app.R

@BindingAdapter("image")
fun loadImage(view: ImageView, url: String){
    Glide.with(view)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(R.drawable.ic_error)
        .into(view)
}