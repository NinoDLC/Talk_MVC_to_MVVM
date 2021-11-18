package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenerationV(

    @field:SerializedName("black-white")
    val blackWhite: BlackWhite? = null
) : Parcelable