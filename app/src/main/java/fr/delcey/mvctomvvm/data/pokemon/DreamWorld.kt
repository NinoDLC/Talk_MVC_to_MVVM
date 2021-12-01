package fr.delcey.mvctomvvm.data.pokemon

import com.google.gson.annotations.SerializedName

data class DreamWorld(

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null
)