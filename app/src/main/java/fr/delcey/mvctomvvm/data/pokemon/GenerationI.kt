package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenerationI(

    @field:SerializedName("yellow")
    val yellow: Yellow? = null,

    @field:SerializedName("red-blue")
    val redBlue: RedBlue? = null
) : Parcelable