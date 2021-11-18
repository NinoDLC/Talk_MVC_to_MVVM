package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameIndicesItem(

    @field:SerializedName("game_index")
    val gameIndex: Int? = null,

    @field:SerializedName("version")
    val version: Version? = null
) : Parcelable