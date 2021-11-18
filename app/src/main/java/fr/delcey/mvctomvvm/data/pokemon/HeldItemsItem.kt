package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HeldItemsItem(

    @field:SerializedName("item")
    val item: Item? = null,

    @field:SerializedName("version_details")
    val versionDetails: List<VersionDetailsItem?>? = null
) : Parcelable