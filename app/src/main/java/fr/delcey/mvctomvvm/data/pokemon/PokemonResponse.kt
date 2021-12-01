package fr.delcey.mvctomvvm.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

// TODO This class is HUGE and needs to be Parcelable !
//  Thanks to the Parcelize plugin, this reduces boilerplate but this class would be awful to make parcelable without it !
@Parcelize
data class PokemonResponse(

    @field:SerializedName("location_area_encounters")
    val locationAreaEncounters: String? = null,

    @field:SerializedName("types")
    val types: List<TypesItem?>? = null,

    @field:SerializedName("base_experience")
    val baseExperience: Int? = null,

    @field:SerializedName("held_items")
    val heldItems: List<HeldItemsItem?>? = null,

    @field:SerializedName("weight")
    val weight: Int? = null,

    @field:SerializedName("is_default")
    val isDefault: Boolean? = null,

    @field:SerializedName("sprites")
    val sprites: Sprites? = null,

    @field:SerializedName("abilities")
    val abilities: List<AbilitiesItem?>? = null,

    @field:SerializedName("game_indices")
    val gameIndices: List<GameIndicesItem?>? = null,

    @field:SerializedName("species")
    val species: Species? = null,

    @field:SerializedName("stats")
    val stats: List<StatsItem?>? = null,

    @field:SerializedName("moves")
    val moves: List<MovesItem?>? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("forms")
    val forms: List<FormsItem?>? = null,

    @field:SerializedName("height")
    val height: Int? = null,

    @field:SerializedName("order")
    val order: Int? = null
) : Parcelable {
}