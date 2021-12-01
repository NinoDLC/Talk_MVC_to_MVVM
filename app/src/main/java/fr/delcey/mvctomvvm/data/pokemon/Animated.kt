package fr.delcey.mvctomvvm.data.pokemon

import com.google.gson.annotations.SerializedName

data class Animated(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: String? = null,

    @field:SerializedName("back_female")
    val backFemale: String? = null,

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
)