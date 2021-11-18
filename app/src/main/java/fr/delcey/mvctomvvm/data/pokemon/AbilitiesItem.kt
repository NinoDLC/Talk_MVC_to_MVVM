package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AbilitiesItem(

    @field:SerializedName("is_hidden")
    val isHidden: Boolean? = null,

    @field:SerializedName("ability")
    val ability: Ability? = null,

    @field:SerializedName("slot")
    val slot: Int? = null
) : Parcelable