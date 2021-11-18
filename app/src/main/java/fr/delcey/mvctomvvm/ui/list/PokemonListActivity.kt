package fr.delcey.mvctomvvm.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import fr.delcey.mvctomvvm.R
import fr.delcey.mvctomvvm.data.PokemonRepository
import fr.delcey.mvctomvvm.data.pokemon.PokemonResponse
import fr.delcey.mvctomvvm.ui.detail.PokemonDetailActivity
import java.util.concurrent.Executors
import java.util.concurrent.Future
import javax.inject.Inject

@AndroidEntryPoint
class PokemonListActivity : AppCompatActivity() {

    companion object {
        // TODO Boilerplate & error prone
        private const val BUNDLE_POKEMON_LIST = "BUNDLE_POKEMON_LIST"
        private const val BUNDLE_SEARCH_QUERY = "BUNDLE_SEARCH_QUERY"
    }

    @Inject
    lateinit var pokemonRepository: PokemonRepository

    // TODO Is the Activity the View or the Controller ?
    //  Seems like both : it handles the threads and querying (controller) and display the information (view)
    private val executor = Executors.newFixedThreadPool(4)

    // TODO Error prone : we have to keep track of the current requests to cancel them if the Activity get destroyed
    private var currentRequest: Future<*>? = null
    // TODO Error prone : the Activity is stateful, we have to manage the savedInstanceState to avoid re-query the server on every rotation
    private var pokemons: List<PokemonResponse>? = null
    // TODO Complexity to the max : if searchQuery or pokemons, we have to update the view. Imagine with 4 or 5 factors ? Exponentially complex
    private var searchQuery: String? = null

    // TODO Namespace pollution : the adapter need to be a field (instead of a simple local val inside the onCreate() function)
    //  because we can update the adapter from many "places" !
    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.pokemon_activity)

        setSupportActionBar(findViewById(R.id.pokemons_toolbar))

        adapter = PokemonAdapter { startActivity(PokemonDetailActivity.navigate(this, it)) }
        val recyclerView = findViewById<RecyclerView>(R.id.pokemons_recyclerview)
        recyclerView.adapter = adapter

        val searchTextInputEditText = findViewById<TextInputEditText>(R.id.pokemons_search_text_input_edit_text)
        // TODO Every single line of the remaining onCreate() function is critical but can't be tested !! :
        //  remove one line and the application will most likely compile, but will have a (hidden) bug
        //  Testing every behavior is very hard because the Activity is too coupled with the Android framework
        searchTextInputEditText.doAfterTextChanged {
            searchQuery = it.toString()

            filterAndDisplayPokemons()
        }

        if (savedInstanceState == null) {
            queryPokemons()
        } else {
            pokemons = savedInstanceState.getParcelableArrayList(BUNDLE_POKEMON_LIST)
            searchQuery = savedInstanceState.getString(BUNDLE_SEARCH_QUERY)

            filterAndDisplayPokemons()

            if (pokemons.isNullOrEmpty()) {
                queryPokemons()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // TODO Stateful : don't miss a single dynamic information, otherwise it's lost !
        pokemons?.let { outState.putParcelableArrayList(BUNDLE_POKEMON_LIST, ArrayList(it)) }
        outState.putString(BUNDLE_SEARCH_QUERY, searchQuery)
    }

    override fun onDestroy() {
        super.onDestroy()

        // TODO Possible leak : forget these lines and your Activity can be leaking
        currentRequest?.cancel(true)
        currentRequest = null
    }

    private fun queryPokemons() {
        currentRequest = executor.submit {
            pokemons = pokemonRepository.getPokemons()

            // TODO Thread management : forget this line and your app can crash or have unexpected behavior
            runOnUiThread {
                filterAndDisplayPokemons()
            }
        }
    }

    private fun filterAndDisplayPokemons() {
        // TODO Stateful : we have to capture this value because it can change during the execution of this function
        val searchQuery = this.searchQuery

        val pokemonsToDisplay = if (searchQuery == null) {
            pokemons
        } else {
            pokemons?.filter {
                it.name?.contains(other = searchQuery, ignoreCase = true) == true
            }
        }

        adapter.submitList(pokemonsToDisplay)
    }
}