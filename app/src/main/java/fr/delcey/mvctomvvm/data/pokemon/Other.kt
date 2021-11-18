package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Other(

    @field:SerializedName("dream_world")
    val dreamWorld: DreamWorld? = null,

    @field:SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork? = null,

    @field:SerializedName("home")
    val home: Home? = null
) : Parcelable