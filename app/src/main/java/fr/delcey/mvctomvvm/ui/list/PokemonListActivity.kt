package fr.delcey.mvctomvvm.ui.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import fr.delcey.mvctomvvm.R
import fr.delcey.mvctomvvm.ui.detail.PokemonDetailActivity

@AndroidEntryPoint
class PokemonListActivity : AppCompatActivity() {

    private val viewModel by viewModels<PokemonListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.pokemon_activity)

        setSupportActionBar(findViewById(R.id.pokemons_toolbar))

        val adapter = PokemonAdapter { id -> startActivity(PokemonDetailActivity.navigate(this, id)) }
        val recyclerView = findViewById<RecyclerView>(R.id.pokemons_recyclerview)
        recyclerView.adapter = adapter

        val searchTextInputEditText = findViewById<TextInputEditText>(R.id.pokemons_search_text_input_edit_text)
        searchTextInputEditText.doAfterTextChanged { viewModel.onSearchChanged(it.toString()) }

        viewModel.getPokemonListLiveData().observe(this) {
            adapter.submitList(it)
        }
    }
}