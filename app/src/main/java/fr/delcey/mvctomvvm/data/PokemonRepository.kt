package fr.delcey.mvctomvvm.data

import fr.delcey.mvctomvvm.data.pokemon.PokemonResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

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

    fun getPokemons(): List<PokemonResponse> {
        val pokemonResponses = mutableListOf<PokemonResponse>()

        for (i in 1..3) {
            // TODO That's sad, we have to wait for ALL the pokemons to be queried sequentially to display them
            //  We should display them as soon as we get them !
            //  Or even better, query them in parallel !
            //  And why not both ?!
            val response = pokemonDatasource.getPokemonById(i.toString()).execute().body()
            if (response != null) {
                pokemonResponses.add(response)
            }
        }

        return pokemonResponses
    }
}
