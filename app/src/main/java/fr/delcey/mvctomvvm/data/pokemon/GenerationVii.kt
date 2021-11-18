package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenerationVii(

    @field:SerializedName("icons")
    val icons: Icons? = null,

    @field:SerializedName("ultra-sun-ultra-moon")
    val ultraSunUltraMoon: UltraSunUltraMoon? = null
) : Parcelable