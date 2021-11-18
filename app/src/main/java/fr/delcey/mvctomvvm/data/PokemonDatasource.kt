package fr.delcey.mvctomvvm.data

import fr.delcey.mvctomvvm.data.pokemon.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonDatasource {
    @GET("pokemon/{pokemonId}/")
    fun getPokemonById(@Path("pokemonId") pokemonId : String) : Call<PokemonResponse>
}