package com.happybank.app

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * Example unit test demonstrating JUnit 5 features for HappyBank app.
 */
@DisplayName("Example Unit Tests")
class ExampleUnitTest {

    private lateinit var calculator: Calculator

    @BeforeEach
    fun setup() {
        calculator = Calculator()
    }

    @Test
    @DisplayName("Addition should work correctly")
    fun addition_isCorrect() {
        val result = calculator.add(2, 2)
        assertEquals(4, result)
    }

    @Test
    @DisplayName("Division should throw exception for zero divisor")
    fun division_byZero_throwsException() {
        assertThrows(IllegalArgumentException::class.java) {
            calculator.divide(10, 0)
        }
    }

    @Nested
    @DisplayName("Parameterized Tests")
    inner class ParameterizedTests {

        @ParameterizedTest
        @DisplayName("Test multiplication with different values")
        @ValueSource(ints = [1, 2, 3, 4, 5])
        fun multiplication_withDifferentValues(value: Int) {
            val result = calculator.multiply(value, 2)
            assertEquals(value * 2, result)
            assertTrue(result > 0)
        }
    }

    @Nested
    @DisplayName("Account Number Validation")
    inner class AccountValidationTests {

        @ParameterizedTest
        @DisplayName("Valid account numbers should pass validation")
        @ValueSource(strings = ["12345678", "87654321", "00000000", "99999999"])
        fun validAccountNumbers_shouldReturnTrue(accountNumber: String) {
            val isValid = isValidAccountNumber(accountNumber)
            assertTrue(isValid, "Account number $accountNumber should be valid")
        }

        @ParameterizedTest
        @DisplayName("Invalid account numbers should fail validation")
        @ValueSource(strings = ["1234567", "123456789", "12345abc", "abc12345", "", " ", "12 34 56 78"])
        fun invalidAccountNumbers_shouldReturnFalse(accountNumber: String) {
            val isValid = isValidAccountNumber(accountNumber)
            assertFalse(isValid, "Account number '$accountNumber' should be invalid")
        }
    }

    private fun isValidAccountNumber(accountNumber: String): Boolean {
        return accountNumber.length == 8 && accountNumber.all { it.isDigit() }
    }
}

/**
 * Simple calculator class for testing purposes
 */
class Calculator {
    fun add(a: Int, b: Int): Int = a + b

    fun multiply(a: Int, b: Int): Int = a * b

    fun divide(a: Int, b: Int): Int {
        require(b != 0) { "Cannot divide by zero" }
        return a / b
    }
}
