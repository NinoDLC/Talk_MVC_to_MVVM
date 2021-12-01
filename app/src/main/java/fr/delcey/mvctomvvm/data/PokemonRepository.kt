package fr.delcey.mvctomvvm.data

import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.delcey.mvctomvvm.data.pokemon.PokemonResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.TreeSet
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor() {

    private val executor = Executors.newFixedThreadPool(4)
    private val mainThreadHandler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())

    private val pokemonDatasource: PokemonDatasource

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BASIC)
                        }
                    )
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        pokemonDatasource = retrofit.create(PokemonDatasource::class.java)
    }

    fun getPokemonsLiveData(): LiveData<List<PokemonResponse>> {
        val pokemonsMutableLiveData = MutableLiveData<List<PokemonResponse>>()
        val pokemonResponses = TreeSet<PokemonResponse> { o1, o2 ->
            compareValues(o1?.id, o2?.id)
        }

        for (pokemonId in 1..30) {
            queryPokemonById(pokemonId.toString()) { pokemonResponse ->
                pokemonResponses.add(pokemonResponse)
                pokemonsMutableLiveData.value = pokemonResponses.sortedBy { it.id }
            }
        }

        return pokemonsMutableLiveData
    }

    fun getPokemonByIdLiveData(pokemonId: String): LiveData<PokemonResponse> {
        val pokemonMutableLiveData = MutableLiveData<PokemonResponse>()

        queryPokemonById(pokemonId) { pokemonResponse ->
            pokemonMutableLiveData.value = pokemonResponse
        }

        return pokemonMutableLiveData
    }

    private fun queryPokemonById(
        pokemonId: String,
        onQueried: (PokemonResponse) -> Unit
    ) {
        executor.execute {
            pokemonDatasource.getPokemonById(pokemonId).execute().body()?.let {
                mainThreadHandler.post {
                    onQueried(it)
                }
            }
        }
    }
}
