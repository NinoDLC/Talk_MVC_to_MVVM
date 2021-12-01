package fr.delcey.mvctomvvm.data.pokemon

import com.google.gson.annotations.SerializedName

data class GenerationIv(

    @field:SerializedName("platinum")
    val platinum: Platinum? = null,

    @field:SerializedName("diamond-pearl")
    val diamondPearl: DiamondPearl? = null,

    @field:SerializedName("heartgold-soulsilver")
    val heartgoldSoulsilver: HeartgoldSoulsilver? = null
)