package com.practice.pokemon_app.data.remote.response

import com.google.gson.annotations.SerializedName

data class GenerationVi(
    @SerializedName("omegaruby-alphasapphire")
    val omegarubyAlphasapphire: OmegarubyAlphasapphire?,
    @SerializedName("x-y")
    val xY: XY?
)