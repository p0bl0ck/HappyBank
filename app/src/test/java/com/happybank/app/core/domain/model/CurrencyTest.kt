package com.happybank.app.core.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("Currency Value Object Tests")
class CurrencyTest {

    @Test
    @DisplayName("USD currency has correct properties")
    fun `usd currency has correct properties`() {
        val usd = Currency.USD

        assertEquals("USD", usd.code)
        assertEquals("$", usd.symbol)
        assertEquals("US Dollar", usd.name)
        assertEquals(2, usd.decimalPlaces)
    }

    @Test
    @DisplayName("EUR currency has correct properties")
    fun `eur currency has correct properties`() {
        val eur = Currency.EUR

        assertEquals("EUR", eur.code)
        assertEquals("€", eur.symbol)
        assertEquals("Euro", eur.name)
        assertEquals(2, eur.decimalPlaces)
    }

    @Test
    @DisplayName("GBP currency has correct properties")
    fun `gbp currency has correct properties`() {
        val gbp = Currency.GBP

        assertEquals("GBP", gbp.code)
        assertEquals("£", gbp.symbol)
        assertEquals("British Pound", gbp.name)
        assertEquals(2, gbp.decimalPlaces)
    }

    @Test
    @DisplayName("JPY currency has zero decimal places")
    fun `jpy currency has zero decimal places`() {
        val jpy = Currency.JPY

        assertEquals("JPY", jpy.code)
        assertEquals("¥", jpy.symbol)
        assertEquals("Japanese Yen", jpy.name)
        assertEquals(0, jpy.decimalPlaces)
    }

    @Test
    @DisplayName("PLN currency has correct properties")
    fun `pln currency has correct properties`() {
        val pln = Currency.PLN

        assertEquals("PLN", pln.code)
        assertEquals("zł", pln.symbol)
        assertEquals("Polish Złoty", pln.name)
        assertEquals(2, pln.decimalPlaces)
    }

    @Test
    @DisplayName("fromCode returns correct currency for known codes")
    fun `fromCode returns correct currency for known codes`() {
        assertEquals(Currency.USD, Currency.fromCode("USD"))
        assertEquals(Currency.EUR, Currency.fromCode("EUR"))
        assertEquals(Currency.GBP, Currency.fromCode("GBP"))
        assertEquals(Currency.JPY, Currency.fromCode("JPY"))
        assertEquals(Currency.PLN, Currency.fromCode("PLN"))
    }

    @Test
    @DisplayName("fromCode is case insensitive")
    fun `fromCode is case insensitive`() {
        assertEquals(Currency.USD, Currency.fromCode("usd"))
        assertEquals(Currency.EUR, Currency.fromCode("eur"))
        assertEquals(Currency.GBP, Currency.fromCode("gbp"))
        assertEquals(Currency.USD, Currency.fromCode("UsD"))
    }

    @Test
    @DisplayName("fromCode creates new currency for unknown codes")
    fun `fromCode creates new currency for unknown codes`() {
        val custom = Currency.fromCode("XXX")

        assertEquals("XXX", custom.code)
        assertEquals("XXX", custom.symbol)
        assertEquals("XXX", custom.name)
        assertEquals(2, custom.decimalPlaces)
    }

    @Test
    @DisplayName("fromCode normalizes unknown codes to uppercase")
    fun `fromCode normalizes unknown codes to uppercase`() {
        val custom = Currency.fromCode("xyz")

        assertEquals("XYZ", custom.code)
        assertEquals("XYZ", custom.symbol)
    }

    @Test
    @DisplayName("supportedCurrencies returns all major currencies")
    fun `supportedCurrencies returns all major currencies`() {
        val supported = Currency.supportedCurrencies()

        assertEquals(8, supported.size)
        assert(supported.contains(Currency.USD))
        assert(supported.contains(Currency.EUR))
        assert(supported.contains(Currency.GBP))
        assert(supported.contains(Currency.JPY))
        assert(supported.contains(Currency.PLN))
        assert(supported.contains(Currency.CHF))
        assert(supported.contains(Currency.CAD))
        assert(supported.contains(Currency.AUD))
    }

    @Test
    @DisplayName("Currency creation validates code is not blank")
    fun `currency creation validates code is not blank`() {
        val exception = assertThrows<IllegalArgumentException> {
            Currency(code = "", symbol = "$", name = "Test")
        }
        assertEquals("Currency code cannot be blank", exception.message)
    }

    @Test
    @DisplayName("Currency creation validates symbol is not blank")
    fun `currency creation validates symbol is not blank`() {
        val exception = assertThrows<IllegalArgumentException> {
            Currency(code = "TST", symbol = "", name = "Test")
        }
        assertEquals("Currency symbol cannot be blank", exception.message)
    }

    @Test
    @DisplayName("Currency creation validates decimal places is non-negative")
    fun `currency creation validates decimal places is non-negative`() {
        val exception = assertThrows<IllegalArgumentException> {
            Currency(code = "TST", symbol = "$", name = "Test", decimalPlaces = -1)
        }
        assertEquals("Decimal places must be non-negative", exception.message)
    }

    @Test
    @DisplayName("Currency equality works correctly")
    fun `currency equality works correctly`() {
        val usd1 = Currency.USD
        val usd2 = Currency.fromCode("USD")
        val eur = Currency.EUR

        assertEquals(usd1, usd2)
        assert(usd1 != eur)
    }

    @Test
    @DisplayName("Custom currency can be created with specific decimal places")
    fun `custom currency can be created with specific decimal places`() {
        val custom = Currency(
            code = "BTC",
            symbol = "₿",
            name = "Bitcoin",
            decimalPlaces = 8
        )

        assertEquals("BTC", custom.code)
        assertEquals("₿", custom.symbol)
        assertEquals(8, custom.decimalPlaces)
    }

    @Test
    @DisplayName("All predefined currencies are not null")
    fun `all predefined currencies are not null`() {
        assertNotNull(Currency.USD)
        assertNotNull(Currency.EUR)
        assertNotNull(Currency.GBP)
        assertNotNull(Currency.JPY)
        assertNotNull(Currency.PLN)
        assertNotNull(Currency.CHF)
        assertNotNull(Currency.CAD)
        assertNotNull(Currency.AUD)
    }
}
