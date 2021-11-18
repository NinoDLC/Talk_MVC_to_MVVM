package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Versions(

    @field:SerializedName("generation-iii")
    val generationIii: GenerationIii? = null,

    @field:SerializedName("generation-ii")
    val generationIi: GenerationIi? = null,

    @field:SerializedName("generation-v")
    val generationV: GenerationV? = null,

    @field:SerializedName("generation-iv")
    val generationIv: GenerationIv? = null,

    @field:SerializedName("generation-vii")
    val generationVii: GenerationVii? = null,

    @field:SerializedName("generation-i")
    val generationI: GenerationI? = null,

    @field:SerializedName("generation-viii")
    val generationViii: GenerationViii? = null,

    @field:SerializedName("generation-vi")
    val generationVi: GenerationVi? = null
) : Parcelable