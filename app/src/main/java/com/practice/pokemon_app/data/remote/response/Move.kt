package com.practice.pokemon_app.data.remote.response

data class Move(
    val move: MoveX?,
    val version_group_details: List<VersionGroupDetail>?
)