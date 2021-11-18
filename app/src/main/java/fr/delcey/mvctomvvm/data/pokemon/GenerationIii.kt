package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenerationIii(

    @field:SerializedName("firered-leafgreen")
    val fireredLeafgreen: FireredLeafgreen? = null,

    @field:SerializedName("ruby-sapphire")
    val rubySapphire: RubySapphire? = null,

    @field:SerializedName("emerald")
    val emerald: Emerald? = null
) : Parcelable