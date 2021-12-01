package fr.delcey.mvctomvvm.ui.detail

import androidx.annotation.ColorRes

data class PokemonDetailViewState(
    val number: String,
    val name: String,
    val imageUrl: String,
    val type1Name: String,
    @ColorRes
    val type1Color: Int,
    val type2Name: String?,
    @ColorRes
    val type2Color: Int?,
    val isType2Visible: Boolean
)