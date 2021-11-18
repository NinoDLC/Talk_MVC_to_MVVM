package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovesItem(

    @field:SerializedName("version_group_details")
    val versionGroupDetails: List<VersionGroupDetailsItem?>? = null,

    @field:SerializedName("move")
    val move: Move? = null
) : Parcelable