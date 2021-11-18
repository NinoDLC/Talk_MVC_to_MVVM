package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfficialArtwork(

    @field:SerializedName("front_default")
    val frontDefault: String? = null
) : Parcelable