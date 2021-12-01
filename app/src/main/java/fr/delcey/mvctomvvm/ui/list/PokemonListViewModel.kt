package fr.delcey.mvctomvvm.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.delcey.mvctomvvm.data.PokemonRepository
import fr.delcey.mvctomvvm.data.pokemon.PokemonResponse
import fr.delcey.mvctomvvm.ui.PokemonUtils
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    pokemonRepository: PokemonRepository,
    private val pokemonUtils: PokemonUtils
) : ViewModel() {

    private val pokemonViewStatesMediatorLiveData = MediatorLiveData<List<PokemonViewState>>()

    private val userQueryMutableLiveData = MutableLiveData<String>()

    init {
        val pokemonResponsesLiveData = pokemonRepository.getPokemonsLiveData()

        pokemonViewStatesMediatorLiveData.addSource(pokemonResponsesLiveData) { responses: List<PokemonResponse> ->
            combine(responses, userQueryMutableLiveData.value)
        }

        pokemonViewStatesMediatorLiveData.addSource(userQueryMutableLiveData) { userQuery ->
            combine(pokemonResponsesLiveData.value, userQuery)
        }
    }

    fun onSearchChanged(searchQuery: String) {
        userQueryMutableLiveData.value = searchQuery
    }

    fun getPokemonListLiveData(): LiveData<List<PokemonViewState>> = pokemonViewStatesMediatorLiveData

    private fun combine(responses: List<PokemonResponse>?, searchQuery: String?) {
        if (responses == null) {
            return
        }

        val pokemonViewStateList: List<PokemonViewState> = responses.mapNotNull { response ->
            if (searchQuery == null || response.name?.contains(searchQuery, ignoreCase = true) == true) {
                val number = response.id?.toString() ?: return@mapNotNull null
                val name = response.name?.capitalize() ?: return@mapNotNull null

                val type1: String = pokemonUtils.getType(response.types, 1) ?: return@mapNotNull null
                val type1Color = pokemonUtils.getTypeColorRes(type1) ?: return@mapNotNull null
                val type2: String? = pokemonUtils.getType(response.types, 2)
                val type2Color = pokemonUtils.getTypeColorRes(type2)

                PokemonViewState(
                    id = number,
                    name = "#$number - $name",
                    imageUrl = response.sprites?.frontDefault ?: return@mapNotNull null,
                    type1Name = type1,
                    type1Color = type1Color,
                    type2Name = type2,
                    type2Color = type2Color,
                    isType2Visible = type2 != null && type2Color != null
                )
            } else {
                null
            }
        }

        pokemonViewStatesMediatorLiveData.value = pokemonViewStateList
    }
}