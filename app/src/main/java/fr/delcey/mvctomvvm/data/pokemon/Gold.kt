package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gold(

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable