package fr.delcey.mvctomvvm.data.pokemon

import android.graphics.Color
import android.os.Parcelable
import androidx.annotation.ColorInt
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Type(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable {

    // TODO Deep inside our POJOS lies the business rules of our project... Not easily manageable : would you find it without the TODO ?
    //  (the "business rule" here is how to map the name of the type to the corresponding color)

    // TODO Meaningful testing : testing this function is easy (no its not) but it doesn't really check that we use this color in every screen

    // TODO Testing against Android : testing this function is impossible because it uses the Android Color class
    //  (and unit tests don't know anything about Android)

    // TODO Decoupling : this is about data parsing wtf why do I use the Android Color class ?
    @ColorInt
    fun getColorInt(): Int = when (name) {
        "normal" -> Color.parseColor("#A8A77A")
        "fire" -> Color.parseColor("#EE8130")
        "water" -> Color.parseColor("#6390F0")
        "electric" -> Color.parseColor("#F7D02C")
        "grass" -> Color.parseColor("#7AC74C")
        "ice" -> Color.parseColor("#96D9D6")
        "fighting" -> Color.parseColor("#C22E28")
        "poison" -> Color.parseColor("#A33EA1")
        "ground" -> Color.parseColor("#E2BF65")
        "flying" -> Color.parseColor("#A98FF3")
        "psychic" -> Color.parseColor("#F95587")
        "bug" -> Color.parseColor("#A6B91A")
        "rock" -> Color.parseColor("#B6A136")
        "ghost" -> Color.parseColor("#735797")
        "dragon" -> Color.parseColor("#6F35FC")
        "dark" -> Color.parseColor("#705746")
        "steel" -> Color.parseColor("#B7B7CE")
        "fairy" -> Color.parseColor("#D685AD")
        else -> Color.parseColor("#777777")
    }
}