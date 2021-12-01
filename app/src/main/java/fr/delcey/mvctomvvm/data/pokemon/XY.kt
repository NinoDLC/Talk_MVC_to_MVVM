package fr.delcey.mvctomvvm.data.pokemon

import com.google.gson.annotations.SerializedName

data class XY(

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
)