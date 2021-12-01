package fr.delcey.mvctomvvm.data.pokemon

import com.google.gson.annotations.SerializedName

data class GameIndicesItem(

    @field:SerializedName("game_index")
    val gameIndex: Int? = null,

    @field:SerializedName("version")
    val version: Version? = null
)