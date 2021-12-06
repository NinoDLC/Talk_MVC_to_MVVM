package fr.delcey.mvctomvvm.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.delcey.mvctomvvm.data.PokemonRepository
import fr.delcey.mvctomvvm.ui.PokemonUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    pokemonRepository: PokemonRepository,
    pokemonUtils: PokemonUtils,
    ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val pokemonIdMutableLiveData = MutableLiveData<String>()
    private val pokemonResponseLiveData = Transformations.switchMap(pokemonIdMutableLiveData) { id ->
        pokemonRepository.getPokemonByIdFlow(id).asLiveData(ioDispatcher)
    }

    private val pokemonDetailViewStateMediatorLiveData = MediatorLiveData<PokemonDetailViewState>()

    init {
        pokemonDetailViewStateMediatorLiveData.addSource(pokemonResponseLiveData) { pokemonResponse ->
            val number = pokemonResponse?.id?.toString() ?: return@addSource

            val type1: String = pokemonUtils.getType(pokemonResponse.types, 1)?: return@addSource
            val type2: String? = pokemonUtils.getType(pokemonResponse.types, 2)
            val type2Color: Int? = pokemonUtils.getTypeColorRes(type2)

            pokemonDetailViewStateMediatorLiveData.value = PokemonDetailViewState(
                number = "#$number",
                name = pokemonResponse.name?.capitalize() ?: return@addSource,
                imageUrl = pokemonResponse.sprites?.frontDefault ?: return@addSource,
                type1Name = type1,
                type1Color = pokemonUtils.getTypeColorRes(type1) ?: return@addSource,
                type2Name = type2,
                type2Color = type2Color,
                isType2Visible = type2 != null && type2Color != null
            )
        }
    }

    fun getPokemonDetailViewStateLiveData(): LiveData<PokemonDetailViewState> = pokemonDetailViewStateMediatorLiveData

    fun setPokemonId(pokemonId: String) {
        pokemonIdMutableLiveData.value = pokemonId
    }
}