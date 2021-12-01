package fr.delcey.mvctomvvm.ui.detail

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import fr.delcey.mvctomvvm.R

@AndroidEntryPoint
class PokemonDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_POKEMON_ID = "EXTRA_POKEMON_ID"

        fun navigate(context: Context, pokemonId: String) = Intent(context, PokemonDetailActivity::class.java).apply {
            putExtra(EXTRA_POKEMON_ID, pokemonId)
        }
    }

    private val viewModel by viewModels<PokemonDetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.detail_activity)

        val pokemonId = intent.getStringExtra(EXTRA_POKEMON_ID) ?: return

        val pokemonImageView = findViewById<ImageView>(R.id.pokemon_detail_iv)
        val pokemonNumberTextView = findViewById<TextView>(R.id.pokemon_detail_tv_number)
        val pokemonNameTextView = findViewById<TextView>(R.id.pokemon_detail_tv_name)
        val pokemonType1Chip = findViewById<Chip>(R.id.pokemon_detail_chip_type1)
        val pokemonType2Chip = findViewById<Chip>(R.id.pokemon_detail_chip_type2)

        viewModel.getPokemonDetailViewStateLiveData().observe(this) { pokemonDetailViewState ->
            Glide.with(pokemonImageView)
                .load(pokemonDetailViewState.imageUrl)
                .fitCenter()
                .into(pokemonImageView)

            pokemonNumberTextView.text = pokemonDetailViewState.number
            pokemonNameTextView.text = pokemonDetailViewState.name

            pokemonType1Chip.text = pokemonDetailViewState.type1Name
            pokemonType1Chip.chipBackgroundColor = pokemonDetailViewState.type1Color?.let {
                ColorStateList.valueOf(ContextCompat.getColor(this, it))
            }

            pokemonType2Chip.isVisible = pokemonDetailViewState.isType2Visible
            pokemonType2Chip.text = pokemonDetailViewState.type2Name
            pokemonType2Chip.chipBackgroundColor = pokemonDetailViewState.type2Color?.let {
                ColorStateList.valueOf(ContextCompat.getColor(this, it))
            }
        }

        viewModel.setPokemonId(pokemonId)
    }
}