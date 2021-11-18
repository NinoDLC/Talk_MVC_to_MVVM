package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenerationViii(

    @field:SerializedName("icons")
    val icons: Icons? = null
) : Parcelable