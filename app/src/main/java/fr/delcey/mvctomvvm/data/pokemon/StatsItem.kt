package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatsItem(

    @field:SerializedName("stat")
    val stat: Stat? = null,

    @field:SerializedName("base_stat")
    val baseStat: Int? = null,

    @field:SerializedName("effort")
    val effort: Int? = null
) : Parcelable