package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VersionDetailsItem(

    @field:SerializedName("version")
    val version: Version? = null,

    @field:SerializedName("rarity")
    val rarity: Int? = null
) : Parcelable