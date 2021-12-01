package fr.delcey.mvctomvvm.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import fr.delcey.mvctomvvm.R
import fr.delcey.mvctomvvm.data.PokemonRepository
import fr.delcey.mvctomvvm.data.pokemon.PokemonResponse
import fr.delcey.mvctomvvm.data.pokemon.Sprites
import fr.delcey.mvctomvvm.data.pokemon.Type
import fr.delcey.mvctomvvm.data.pokemon.TypesItem
import fr.delcey.mvctomvvm.ui.PokemonUtils
import fr.delcey.mvctomvvm.utils.getValueForTesting
import fr.delcey.mvctomvvm.utils.observeForTesting
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PokemonDetailViewModelTest {

    companion object {
        private const val DEFAULT_POKEMON_ID = 48

        private const val DEFAULT_POKEMON_RESPONSE_NAME = "DEFAULT_POKEMON_RESPONSE_NAME"
        private const val DEFAULT_POKEMON_RESPONSE_SPRITE_FRONT_DEFAULT = "DEFAULT_POKEMON_RESPONSE_SPRITE_FRONT_DEFAULT"

        private const val EXPECTED_POKEMON_TYPE_1 = "normal"
        private const val EXPECTED_POKEMON_TYPE_2 = "ghost"
        private const val EXPECTED_POKEMON_TYPE_COLOR_1 = R.color.type_normal
        private const val EXPECTED_POKEMON_TYPE_COLOR_2 = R.color.type_ghost
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val pokemonResponseMutableLiveData = MutableLiveData<PokemonResponse>()

    private val pokemonRepository = mockk<PokemonRepository>()

    private lateinit var viewModel: PokemonDetailViewModel

    @Before
    fun setUp() {
        clearAllMocks()

        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse()
        every { pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString()) } returns pokemonResponseMutableLiveData

        viewModel = PokemonDetailViewModel(pokemonRepository, PokemonUtils())
        viewModel.setPokemonId(DEFAULT_POKEMON_ID.toString())
    }

    @After
    fun tearDown() {
        confirmVerified(pokemonRepository)
    }

    @Test
    fun `nominal case`() {
        // When
        val result: PokemonDetailViewState = viewModel.getPokemonDetailViewStateLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonDetailViewState(),
            result
        )
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    @Test
    fun `error case - id is null`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            id = null
        )

        // When
        viewModel.getPokemonDetailViewStateLiveData().observeForTesting()

        // Then
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    @Test
    fun `error case - name is null`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            name = null
        )

        // When
        viewModel.getPokemonDetailViewStateLiveData().observeForTesting()

        // Then
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    @Test
    fun `error case - sprites node is null`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            sprites = null
        )

        // When
        viewModel.getPokemonDetailViewStateLiveData().observeForTesting()

        // Then
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    @Test
    fun `error case - sprites frontDefault is null`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            sprites = getDefaultSprites().copy(
                frontDefault = null
            )
        )

        // When
        viewModel.getPokemonDetailViewStateLiveData().observeForTesting()

        // Then
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    @Test
    fun `error case - types node is null`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            types = null
        )

        // When
        viewModel.getPokemonDetailViewStateLiveData().observeForTesting()

        // Then
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    // region TYPE 1
    @Test
    fun `error case - first typeItem is null`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            types = getDefaultTypesItems(
                firstTypeItem = null
            )
        )

        // When
        viewModel.getPokemonDetailViewStateLiveData().observeForTesting()

        // Then
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    @Test
    fun `error case - first typeItem has null slot`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            types = getDefaultTypesItems(
                firstTypeItem = getDefaultTypesItem(1).copy(
                    slot = null
                )
            )
        )

        // When
        viewModel.getPokemonDetailViewStateLiveData().observeForTesting()

        // Then
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    @Test
    fun `error case - first typeItem has unknown slot`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            types = getDefaultTypesItems(
                firstTypeItem = getDefaultTypesItem(1).copy(
                    slot = 666
                )
            )
        )

        // When
        viewModel.getPokemonDetailViewStateLiveData().observeForTesting()

        // Then
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    @Test
    fun `error case - first typeItem has null type`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            types = getDefaultTypesItems(
                firstTypeItem = getDefaultTypesItem(1).copy(
                    type = null
                )
            )
        )

        // When
        viewModel.getPokemonDetailViewStateLiveData().observeForTesting()

        // Then
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    @Test
    fun `error case - first typeItem has null name`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            types = getDefaultTypesItems(
                firstTypeItem = getDefaultTypesItem(1).copy(
                    type = getDefaultType(1)?.copy(
                        name = null
                    )
                )
            )
        )

        // When
        viewModel.getPokemonDetailViewStateLiveData().observeForTesting()

        // Then
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }
    // endregion TYPE 1

    // region TYPE 2
    @Test
    fun `nominal case - second typeItem is null`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            types = getDefaultTypesItems(
                secondTypeItem = null
            )
        )

        // When
        val result = viewModel.getPokemonDetailViewStateLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonDetailViewState().copy(
                type2Name = null,
                type2Color = null,
                isType2Visible = false
            ),
            result
        )
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    @Test
    fun `edge case - second typeItem has null slot`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            types = getDefaultTypesItems(
                secondTypeItem = getDefaultTypesItem(2).copy(
                    slot = null
                )
            )
        )

        // When
        val result = viewModel.getPokemonDetailViewStateLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonDetailViewState().copy(
                type2Name = null,
                type2Color = null,
                isType2Visible = false
            ),
            result
        )
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    @Test
    fun `edge case - second typeItem has unknown slot`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            types = getDefaultTypesItems(
                secondTypeItem = getDefaultTypesItem(2).copy(
                    slot = 666
                )
            )
        )

        // When
        val result = viewModel.getPokemonDetailViewStateLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonDetailViewState().copy(
                type2Name = null,
                type2Color = null,
                isType2Visible = false
            ),
            result
        )
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    @Test
    fun `edge case - second typeItem has null type`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            types = getDefaultTypesItems(
                secondTypeItem = getDefaultTypesItem(2).copy(
                    type = null
                )
            )
        )

        // When
        val result = viewModel.getPokemonDetailViewStateLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonDetailViewState().copy(
                type2Name = null,
                type2Color = null,
                isType2Visible = false
            ),
            result
        )
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }

    @Test
    fun `edge case - second typeItem has null name`() {
        // Given
        pokemonResponseMutableLiveData.value = getDefaultPokemonResponse().copy(
            types = getDefaultTypesItems(
                secondTypeItem = getDefaultTypesItem(2).copy(
                    type = getDefaultType(2)?.copy(
                        name = null
                    )
                )
            )
        )

        // When
        val result = viewModel.getPokemonDetailViewStateLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonDetailViewState().copy(
                type2Name = null,
                type2Color = null,
                isType2Visible = false
            ),
            result
        )
        verify(exactly = 1) {
            pokemonRepository.getPokemonByIdLiveData(DEFAULT_POKEMON_ID.toString())
        }
    }
    // endregion TYPE 2

    // region IN
    private fun getDefaultPokemonResponse() = PokemonResponse(
        id = DEFAULT_POKEMON_ID,
        name = DEFAULT_POKEMON_RESPONSE_NAME,
        sprites = getDefaultSprites(),
        types = getDefaultTypesItems()
    )

    private fun getDefaultSprites() = Sprites(
        frontDefault = DEFAULT_POKEMON_RESPONSE_SPRITE_FRONT_DEFAULT
    )

    private fun getDefaultTypesItems(
        firstTypeItem: TypesItem? = getDefaultTypesItem(1),
        secondTypeItem: TypesItem? = getDefaultTypesItem(2),
    ) = listOfNotNull(
        firstTypeItem,
        secondTypeItem,
    )

    private fun getDefaultTypesItem(slot: Int) = TypesItem(
        slot = slot,
        type = getDefaultType(slot)
    )

    private fun getDefaultType(slot: Int) = when (slot) {
        1 -> Type(name = EXPECTED_POKEMON_TYPE_1)
        2 -> Type(name = EXPECTED_POKEMON_TYPE_2)
        else -> null
    }
    // endregion IN

    // region OUT
    private fun getDefaultPokemonDetailViewState() = PokemonDetailViewState(
        number = "#48",
        name = DEFAULT_POKEMON_RESPONSE_NAME,
        imageUrl = DEFAULT_POKEMON_RESPONSE_SPRITE_FRONT_DEFAULT,
        type1Name = EXPECTED_POKEMON_TYPE_1,
        type1Color = EXPECTED_POKEMON_TYPE_COLOR_1,
        type2Name = EXPECTED_POKEMON_TYPE_2,
        type2Color = EXPECTED_POKEMON_TYPE_COLOR_2,
        isType2Visible = true,
    )
    // endregion OUT
}