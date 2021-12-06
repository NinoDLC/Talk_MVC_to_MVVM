package fr.delcey.mvctomvvm.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fr.delcey.mvctomvvm.R
import fr.delcey.mvctomvvm.data.PokemonRepository
import fr.delcey.mvctomvvm.data.pokemon.PokemonResponse
import fr.delcey.mvctomvvm.data.pokemon.Sprites
import fr.delcey.mvctomvvm.data.pokemon.Type
import fr.delcey.mvctomvvm.data.pokemon.TypesItem
import fr.delcey.mvctomvvm.ui.PokemonUtils
import fr.delcey.mvctomvvm.utils.TestCoroutineRule
import fr.delcey.mvctomvvm.utils.getValueForTesting
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonListViewModelTest {

    companion object {
        private const val DEFAULT_POKEMON_RESPONSE_NAME = "DEFAULT_POKEMON_RESPONSE_NAME"
        private const val DEFAULT_POKEMON_RESPONSE_SPRITE_FRONT_DEFAULT = "DEFAULT_POKEMON_RESPONSE_SPRITE_FRONT_DEFAULT"

        private const val EXPECTED_POKEMON_TYPE_1 = "normal"
        private const val EXPECTED_POKEMON_TYPE_2 = "ghost"
        private const val EXPECTED_POKEMON_TYPE_COLOR_1 = R.color.type_normal
        private const val EXPECTED_POKEMON_TYPE_COLOR_2 = R.color.type_ghost
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val pokemonRepository = mockk<PokemonRepository>()

    @Before
    fun setUp() {
        clearAllMocks()

        every { pokemonRepository.getPokemonsFlow() } returns flowOf(getDefaultPokemonResponses())
    }

    @After
    fun tearDown() {
        confirmVerified(pokemonRepository)
    }

    @Test
    fun `nominal case`() {
        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(),
            result
        )
    }

    @Test
    fun `initial case - 0 responses`() {
        // Given
        pokemonResponsesFlow.value = emptyList()

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertTrue(result.isEmpty())
    }

    // region USER SEARCH
    @Test
    fun `nominal case - user search is a perfect match for first item`() {
        // Given
        viewModel.onSearchChanged("DEFAULT_POKEMON_RESPONSE_NAME_0")

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            listOf(getDefaultPokemonViewState(0)),
            result
        )
    }

    @Test
    fun `error case - user search is a perfect match for first item but name is null`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            firstPokemonResponse = getDefaultPokemonResponse(0).copy(
                name = null
            )
        )
        viewModel.onSearchChanged("DEFAULT_POKEMON_RESPONSE_NAME_0")

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `error case - user search is a perfect match for first item but name is empty`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            firstPokemonResponse = getDefaultPokemonResponse(0).copy(
                name = ""
            )
        )
        viewModel.onSearchChanged("DEFAULT_POKEMON_RESPONSE_NAME_0")

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `nominal case - user search is a perfect match for second item`() {
        // Given
        viewModel.onSearchChanged("DEFAULT_POKEMON_RESPONSE_NAME_1")

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            listOf(getDefaultPokemonViewState(1)),
            result
        )
    }

    @Test
    fun `nominal case - user search is a perfect match for second item with ignore case`() {
        // Given
        viewModel.onSearchChanged("default_pokemon_response_name_1")

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            listOf(getDefaultPokemonViewState(1)),
            result
        )
    }

    @Test
    fun `nominal case - user search is a perfect match for third item`() {
        // Given
        viewModel.onSearchChanged("DEFAULT_POKEMON_RESPONSE_NAME_2")

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            listOf(getDefaultPokemonViewState(2)),
            result
        )
    }

    @Test
    fun `nominal case - user search is a partial match for all`() {
        // Given
        viewModel.onSearchChanged("POKEMON_RESPONSE_NAME")

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(),
            result
        )
    }

    @Test
    fun `nominal case - user search is a partial match for all with ignore case`() {
        // Given
        viewModel.onSearchChanged("pokemon_response_name")

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(),
            result
        )
    }

    @Test
    fun `nominal case - user search is a partial match for first item`() {
        // Given
        viewModel.onSearchChanged("0")

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            listOf(getDefaultPokemonViewState(0)),
            result
        )
    }

    @Test
    fun `nominal case - user search is a partial match for second item`() {
        // Given
        viewModel.onSearchChanged("1")

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            listOf(getDefaultPokemonViewState(1)),
            result
        )
    }

    @Test
    fun `nominal case - user search is a partial match for third item`() {
        // Given
        viewModel.onSearchChanged("2")

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            listOf(getDefaultPokemonViewState(2)),
            result
        )
    }

    @Test
    fun `edge case - user search has no match`() {
        // Given
        viewModel.onSearchChanged("unknown pokemon")

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertTrue(result.isEmpty())
    }
    // endregion USER SEARCH

    @Test
    fun `nominal case - pokemon name must have a # and be capitalized`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                name = "mimitoss"
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = getDefaultPokemonViewState(1).copy(
                    name = "#1 - Mimitoss"
                )
            ),
            result
        )
    }

    @Test
    fun `error case - don't display a pokemon without id`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                id = null
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = null
            ),
            result
        )
    }

    @Test
    fun `error case - don't display a pokemon without name`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                name = null
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = null
            ),
            result
        )
    }

    @Test
    fun `error case - don't display a pokemon without types`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                types = null
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = null
            ),
            result
        )
    }

    @Test
    fun `error case - don't display a pokemon with empty types`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                types = emptyList()
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = null
            ),
            result
        )
    }

    // region TYPE 1
    @Test
    fun `error case - don't display a pokemon with unknown slot for type 1`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                types = getDefaultTypesItems(
                    firstTypeItem = getDefaultTypesItem(1).copy(
                        slot = 666
                    )
                )
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = null
            ),
            result
        )
    }

    @Test
    fun `error case - don't display a pokemon with null slot for type 1`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                types = getDefaultTypesItems(
                    firstTypeItem = getDefaultTypesItem(1).copy(
                        slot = null
                    )
                )
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = null
            ),
            result
        )
    }

    @Test
    fun `error case - don't display a pokemon with null type 1`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                types = getDefaultTypesItems(
                    firstTypeItem = getDefaultTypesItem(1).copy(
                        type = null
                    )
                )
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = null
            ),
            result
        )
    }

    @Test
    fun `error case - don't display a pokemon with null name for type 1`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                types = getDefaultTypesItems(
                    firstTypeItem = getDefaultTypesItem(1).copy(
                        type = getDefaultType(1)?.copy(
                            name = null
                        )
                    )
                )
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = null
            ),
            result
        )
    }

    @Test
    fun `error case - don't display a pokemon without a type 1 color`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                types = getDefaultTypesItems(
                    firstTypeItem = getDefaultTypesItem(1).copy(
                        type = getDefaultType(1)?.copy(
                            name = "unknown type"
                        )
                    )
                )
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = null
            ),
            result
        )
    }
    // endregion TYPE1

    // region TYPE 2
    @Test
    fun `nominal case - a pokemon can have an null type 2`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                types = getDefaultTypesItems(
                    secondTypeItem = null
                )
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = getDefaultPokemonViewState(1).copy(
                    type2Name = null,
                    type2Color = null,
                    isType2Visible = false
                )
            ),
            result
        )
    }

    @Test
    fun `nominal case - a pokemon can have an unknown slot for type 2`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                types = getDefaultTypesItems(
                    secondTypeItem = getDefaultTypesItem(2).copy(
                        slot = 666
                    )
                )
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = getDefaultPokemonViewState(1).copy(
                    type2Name = null,
                    type2Color = null,
                    isType2Visible = false
                )
            ),
            result
        )
    }

    @Test
    fun `nominal case - a pokemon can have a null slot for type 2`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                types = getDefaultTypesItems(
                    secondTypeItem = getDefaultTypesItem(2).copy(
                        slot = null
                    )
                )
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = getDefaultPokemonViewState(1).copy(
                    type2Name = null,
                    type2Color = null,
                    isType2Visible = false
                )
            ),
            result
        )
    }

    @Test
    fun `nominal case - a pokemon can have a null type 2`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                types = getDefaultTypesItems(
                    secondTypeItem = getDefaultTypesItem(2).copy(
                        type = null
                    )
                )
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = getDefaultPokemonViewState(1).copy(
                    type2Name = null,
                    type2Color = null,
                    isType2Visible = false
                )
            ),
            result
        )
    }

    @Test
    fun `nominal case - a pokemon can have a null name for type 2`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                types = getDefaultTypesItems(
                    secondTypeItem = getDefaultTypesItem(2).copy(
                        type = getDefaultType(2)?.copy(
                            name = null
                        )
                    )
                )
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = getDefaultPokemonViewState(1).copy(
                    type2Name = null,
                    type2Color = null,
                    isType2Visible = false
                )
            ),
            result
        )
    }

    @Test
    fun `nominal case - a pokemon can have an unknown type 2 color`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                types = getDefaultTypesItems(
                    secondTypeItem = getDefaultTypesItem(2).copy(
                        type = getDefaultType(2)?.copy(
                            name = "unknown type"
                        )
                    )
                )
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = getDefaultPokemonViewState(1).copy(
                    type2Name = "unknown type",
                    type2Color = null,
                    isType2Visible = false
                )
            ),
            result
        )
    }
    // endregion TYPE 2

    @Test
    fun `error case - don't display a pokemon without an image url (sprites node is null)`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                sprites = null
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = null
            ),
            result
        )
    }

    @Test
    fun `error case - don't display a pokemon without an image url (front default is null)`() {
        // Given
        pokemonResponsesFlow.value = getDefaultPokemonResponses(
            secondPokemonResponse = getDefaultPokemonResponse(1).copy(
                sprites = getDefaultSprites(0).copy(
                    frontDefault = null
                )
            )
        )

        // When
        val result: List<PokemonViewState> = getViewModel().getPokemonListLiveData().getValueForTesting()

        // Then
        assertEquals(
            getDefaultPokemonViewStates(
                secondPokemonViewState = null
            ),
            result
        )
    }

    // region IN
    private fun getDefaultPokemonResponses(
        firstPokemonResponse: PokemonResponse? = getDefaultPokemonResponse(0),
        secondPokemonResponse: PokemonResponse? = getDefaultPokemonResponse(1),
        thirdPokemonResponse: PokemonResponse? = getDefaultPokemonResponse(2),
    ): List<PokemonResponse> = listOfNotNull(
        firstPokemonResponse,
        secondPokemonResponse,
        thirdPokemonResponse
    )

    private fun getDefaultPokemonResponse(index: Int) = PokemonResponse(
        id = index,
        name = "${DEFAULT_POKEMON_RESPONSE_NAME}_$index",
        sprites = getDefaultSprites(index),
        types = getDefaultTypesItems()
    )

    private fun getDefaultSprites(index: Int) = Sprites(
        frontDefault = "${DEFAULT_POKEMON_RESPONSE_SPRITE_FRONT_DEFAULT}_$index"
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

    private fun getViewModel(): PokemonListViewModel {
        return PokemonListViewModel(
            pokemonRepository = pokemonRepository,
            pokemonUtils = PokemonUtils(),
            ioDispatcher = testCoroutineRule.testCoroutineDispatcher
        ).also {
            verify(exactly = 1) {
                pokemonRepository.getPokemonsFlow()
            }
        }
    }

    // region OUT
    private fun getDefaultPokemonViewStates(
        firstPokemonViewState: PokemonViewState? = getDefaultPokemonViewState(0),
        secondPokemonViewState: PokemonViewState? = getDefaultPokemonViewState(1),
        thirdPokemonViewState: PokemonViewState? = getDefaultPokemonViewState(2),
    ): List<PokemonViewState> = listOfNotNull(
        firstPokemonViewState,
        secondPokemonViewState,
        thirdPokemonViewState
    )

    private fun getDefaultPokemonViewState(index: Int) = PokemonViewState(
        index.toString(),
        "#$index - ${DEFAULT_POKEMON_RESPONSE_NAME}_$index",
        "${DEFAULT_POKEMON_RESPONSE_SPRITE_FRONT_DEFAULT}_$index",
        EXPECTED_POKEMON_TYPE_1,
        EXPECTED_POKEMON_TYPE_COLOR_1,
        EXPECTED_POKEMON_TYPE_2,
        EXPECTED_POKEMON_TYPE_COLOR_2,
        true
    )
    // endregion OUT
}