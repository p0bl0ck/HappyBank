package com.happybank.app.feature.home.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel for the Home Screen
 * Demonstrates Hilt dependency injection
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    // Future dependencies will be injected here
) : ViewModel() {

    // UI State
    private val _userName = MutableStateFlow("Guest")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _accountBalance = MutableStateFlow("$0.00")
    val accountBalance: StateFlow<String> = _accountBalance.asStateFlow()

    init {
        // Initialize default values
        loadUserData()
    }

    private fun loadUserData() {
        // Mock data - will be replaced with real data from repository
        _userName.value = "John Doe"
        _accountBalance.value = "$12,345.67"
    }

    fun refreshData() {
        // Placeholder for refresh logic
        loadUserData()
    }
}
