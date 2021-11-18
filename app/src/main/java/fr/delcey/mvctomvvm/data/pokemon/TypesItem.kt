package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TypesItem(

    @field:SerializedName("slot")
    val slot: Int? = null,

    @field:SerializedName("type")
    val type: Type? = null
) : Parcelable