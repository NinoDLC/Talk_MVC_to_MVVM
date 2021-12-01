package fr.delcey.mvctomvvm.data.pokemon

import com.google.gson.annotations.SerializedName

data class FormsItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)