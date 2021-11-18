package fr.delcey.mvctomvvm.ui.detail

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import fr.delcey.mvctomvvm.R
import fr.delcey.mvctomvvm.data.pokemon.PokemonResponse

@AndroidEntryPoint
class PokemonDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_POKEMON_RESPONSE = "EXTRA_POKEMON_RESPONSE"

        // TODO What if the PokemonResponse is too heavy (larger than 1Mo / 500 Ko, the maximum for a Bundle / Intent) ?
        //  Spoiler alert : it is too heavy. It would crash the app if you try to put 5 PokemonResponses on a Bundle
        //  Try it on the Repository, change 1..3 to 1..5 and click on a Pokemon in the list !
        fun navigate(context: Context, pokemonResponse: PokemonResponse) = Intent(context, PokemonDetailActivity::class.java).apply {
            putExtra(EXTRA_POKEMON_RESPONSE, pokemonResponse)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.detail_activity)

        // TODO What if the PokemonResponse is dynamic (data can be updated because of a repository change) ?

        // TODO What if the PokemonResponse is mutable (data can be updated because of a user interaction on this screen),
        //  how to reflect this change on the rest of the application ?
        //  (yes, setResult() can be used but that's troublesome and what we call "Spaghetti code !")
        val pokemonResponse = intent.getParcelableExtra<PokemonResponse>(EXTRA_POKEMON_RESPONSE) ?: return

        val pokemonImageView = findViewById<ImageView>(R.id.pokemon_detail_iv)
        val pokemonNumberTextView = findViewById<TextView>(R.id.pokemon_detail_tv_number)
        val pokemonNameTextView = findViewById<TextView>(R.id.pokemon_detail_tv_name)
        val pokemonType1Chip = findViewById<Chip>(R.id.pokemon_detail_chip_type1)
        val pokemonType2Chip = findViewById<Chip>(R.id.pokemon_detail_chip_type2)

        Glide.with(pokemonImageView)
            .load(pokemonResponse.sprites?.frontDefault)
            .fitCenter()
            .into(pokemonImageView)

        pokemonNumberTextView.text = pokemonResponse.getFormattedNumber()
        pokemonNameTextView.text = pokemonResponse.getCapitalizedName()

        // TODO Intelligence on the Activity : can't be tested
        val firstType = pokemonResponse.types?.firstOrNull {
            it?.slot == 1
        }?.type
        pokemonType1Chip.isVisible = firstType != null
        if (firstType != null) {
            pokemonType1Chip.text = firstType.name
            pokemonType1Chip.chipBackgroundColor = ColorStateList.valueOf(firstType.getColorInt())
        }

        // TODO Should be refactored but it doesn't makes much sense, the refactored function would be, for example :
        //  updatePokemonChip(pokemonResponse, 2, pokemonType2Chip)
        val secondType = pokemonResponse.types?.firstOrNull {
            it?.slot == 2
        }?.type
        pokemonType2Chip.isVisible = secondType != null
        if (secondType != null) {
            pokemonType2Chip.text = secondType.name
            pokemonType2Chip.chipBackgroundColor = ColorStateList.valueOf(secondType.getColorInt())
        }
    }
}