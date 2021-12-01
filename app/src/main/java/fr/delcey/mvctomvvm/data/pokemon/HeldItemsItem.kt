package fr.delcey.mvctomvvm.data.pokemon

import com.google.gson.annotations.SerializedName

data class HeldItemsItem(

    @field:SerializedName("item")
    val item: Item? = null,

    @field:SerializedName("version_details")
    val versionDetails: List<VersionDetailsItem?>? = null
)