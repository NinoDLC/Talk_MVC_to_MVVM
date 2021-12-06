package fr.delcey.mvctomvvm.data

import fr.delcey.mvctomvvm.data.pokemon.PokemonResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonDatasource {
    @GET("pokemon/{pokemonId}/")
    suspend fun getPokemonById(@Path("pokemonId") pokemonId : String) : PokemonResponse
}