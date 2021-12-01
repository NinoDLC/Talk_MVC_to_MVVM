package fr.delcey.mvctomvvm.data.pokemon

import com.google.gson.annotations.SerializedName

data class Yellow(

    @field:SerializedName("front_gray")
    val frontGray: String? = null,

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("back_gray")
    val backGray: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null
)