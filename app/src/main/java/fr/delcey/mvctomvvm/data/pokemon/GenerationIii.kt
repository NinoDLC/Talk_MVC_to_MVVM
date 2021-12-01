package fr.delcey.mvctomvvm.data.pokemon

import com.google.gson.annotations.SerializedName

data class GenerationIii(

    @field:SerializedName("firered-leafgreen")
    val fireredLeafgreen: FireredLeafgreen? = null,

    @field:SerializedName("ruby-sapphire")
    val rubySapphire: RubySapphire? = null,

    @field:SerializedName("emerald")
    val emerald: Emerald? = null
)