package com.happybank.app.core.domain.model

/**
 * Currency value object
 * Represents monetary currency with symbol and formatting rules
 *
 * Immutable representation of currency supporting multiple international currencies
 */
data class Currency(
    val code: String,
    val symbol: String,
    val name: String,
    val decimalPlaces: Int = 2
) {
    init {
        require(code.isNotBlank()) { "Currency code cannot be blank" }
        require(symbol.isNotBlank()) { "Currency symbol cannot be blank" }
        require(decimalPlaces >= 0) { "Decimal places must be non-negative" }
    }

    companion object {
        /**
         * US Dollar
         */
        val USD = Currency(
            code = "USD",
            symbol = "$",
            name = "US Dollar",
            decimalPlaces = 2
        )

        /**
         * Euro
         */
        val EUR = Currency(
            code = "EUR",
            symbol = "€",
            name = "Euro",
            decimalPlaces = 2
        )

        /**
         * British Pound
         */
        val GBP = Currency(
            code = "GBP",
            symbol = "£",
            name = "British Pound",
            decimalPlaces = 2
        )

        /**
         * Japanese Yen
         */
        val JPY = Currency(
            code = "JPY",
            symbol = "¥",
            name = "Japanese Yen",
            decimalPlaces = 0
        )

        /**
         * Polish Złoty
         */
        val PLN = Currency(
            code = "PLN",
            symbol = "zł",
            name = "Polish Złoty",
            decimalPlaces = 2
        )

        /**
         * Swiss Franc
         */
        val CHF = Currency(
            code = "CHF",
            symbol = "CHF",
            name = "Swiss Franc",
            decimalPlaces = 2
        )

        /**
         * Canadian Dollar
         */
        val CAD = Currency(
            code = "CAD",
            symbol = "C$",
            name = "Canadian Dollar",
            decimalPlaces = 2
        )

        /**
         * Australian Dollar
         */
        val AUD = Currency(
            code = "AUD",
            symbol = "A$",
            name = "Australian Dollar",
            decimalPlaces = 2
        )

        /**
         * Get currency by code
         * Returns a predefined currency if available, otherwise creates a new one
         *
         * @param code ISO 4217 currency code (e.g., "USD", "EUR")
         * @return Currency instance
         */
        fun fromCode(code: String): Currency = when (code.uppercase()) {
            "USD" -> USD
            "EUR" -> EUR
            "GBP" -> GBP
            "JPY" -> JPY
            "PLN" -> PLN
            "CHF" -> CHF
            "CAD" -> CAD
            "AUD" -> AUD
            else -> Currency(
                code = code.uppercase(),
                symbol = code.uppercase(),
                name = code.uppercase(),
                decimalPlaces = 2
            )
        }

        /**
         * Get all supported currencies
         */
        fun supportedCurrencies(): List<Currency> = listOf(
            USD, EUR, GBP, JPY, PLN, CHF, CAD, AUD
        )
    }
}
