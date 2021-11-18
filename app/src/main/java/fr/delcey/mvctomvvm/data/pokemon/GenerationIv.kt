package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenerationIv(

    @field:SerializedName("platinum")
    val platinum: Platinum? = null,

    @field:SerializedName("diamond-pearl")
    val diamondPearl: DiamondPearl? = null,

    @field:SerializedName("heartgold-soulsilver")
    val heartgoldSoulsilver: HeartgoldSoulsilver? = null
) : Parcelable