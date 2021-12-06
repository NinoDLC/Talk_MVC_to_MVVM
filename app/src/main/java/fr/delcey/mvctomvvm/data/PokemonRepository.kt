package fr.delcey.mvctomvvm.data

import fr.delcey.mvctomvvm.data.pokemon.PokemonResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.TreeSet
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class PokemonRepository @Inject constructor() {

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

    fun getPokemonsFlow(): Flow<List<PokemonResponse>> = channelFlow {
        val pokemonResponses = TreeSet<PokemonResponse> { o1, o2 ->
            compareValues(o1?.id, o2?.id)
        }

        for (pokemonId in 1..30) {
            // Start a new "sub-coroutine" to make parallel calls thanks to channelFlow
            launch {
                try {
                    val response = pokemonDatasource.getPokemonById(pokemonId.toString())

                    pokemonResponses.add(response)
                    send(pokemonResponses.toList())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun getPokemonByIdFlow(pokemonId: String): Flow<PokemonResponse?> = flow {
        emit(
            try {
                pokemonDatasource.getPokemonById(pokemonId)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        )
    }
}
