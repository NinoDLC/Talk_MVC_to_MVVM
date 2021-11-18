package fr.delcey.mvctomvvm.data.pokemon

import org.junit.Assert.*

import org.junit.Test

class PokemonResponseTest {

    @Test
    fun getFormattedNumber() {
        // Given
        val subject = PokemonResponse(id = 48)

        // When
        val result = subject.getFormattedNumber()

        // Then
        assertEquals("#48", result)
    }

    @Test
    fun `given uncapitalized name, getCapitalizedName should return capitalized name`() {
        // Given
        val subject = PokemonResponse(name = "mimitoss")

        // When
        val result = subject.getCapitalizedName()

        // Then
        assertEquals("Mimitoss", result)
    }

    @Test
    fun `given capitalized name, getCapitalizedName should return capitalized name`() {
        // Given
        val subject = PokemonResponse(name = "Mimitoss")

        // When
        val result = subject.getCapitalizedName()

        // Then
        assertEquals("Mimitoss", result)
    }

    @Test
    fun `given empty name, getCapitalizedName should return empty`() {
        // Given
        val subject = PokemonResponse(name = "")

        // When
        val result = subject.getCapitalizedName()

        // Then
        assertEquals("", result)
    }

    @Test
    fun `given null name, getCapitalizedName should return null`() {
        // Given
        val subject = PokemonResponse(name = null)

        // When
        val result = subject.getCapitalizedName()

        // Then
        assertNull(result)
    }

    @Test
    fun getDetailedName() {
        // Given
        val subject = PokemonResponse(id = 48, name = "mimitoss")

        // When
        val result = subject.getDetailedName()

        // Then
        assertEquals("#48 Mimitoss", result)
    }
}