package com.happybank.app.feature.accounts.presentation

import com.happybank.app.feature.accounts.domain.model.Account

/**
 * UI state for account-related screens
 *
 * This sealed class represents all possible states of the account UI:
 *
 * State transitions:
 * Loading → InitialSuccess (first load succeeds)
 * Loading → Error (first load fails)
 * InitialSuccess → RefreshableSuccess (user triggers refresh)
 * RefreshableSuccess → RefreshableSuccess (refresh completes, data updates)
 * Error → Loading (user retries)
 *
 * Anti-flickering design:
 * - Loading: Only for initial load when no data exists
 * - InitialSuccess: Data loaded for the first time
 * - RefreshableSuccess: Data exists and is being refreshed (shows refresh indicator)
 * - Error: Only when no cached data exists
 */
sealed class AccountUiState {

    /**
     * Initial loading state
     * Only shown when no account data exists yet
     * Use a full-screen loading indicator
     */
    data object Loading : AccountUiState()

    /**
     * Initial success state after first load
     * Data is displayed without any refresh indicator
     *
     * @param account The account data to display
     */
    data class InitialSuccess(
        val account: Account
    ) : AccountUiState()

    /**
     * Refreshable success state
     * Data exists and is being refreshed in the background
     * Shows a subtle refresh indicator (e.g., pull-to-refresh spinner)
     *
     * @param account The current account data (may be stale during refresh)
     * @param isRefreshing True when actively refreshing, false when refresh completes
     */
    data class RefreshableSuccess(
        val account: Account,
        val isRefreshing: Boolean = true
    ) : AccountUiState()

    /**
     * Error state
     * Only shown when initial load fails and no cached data exists
     *
     * @param message User-friendly error message
     */
    data class Error(
        val message: String
    ) : AccountUiState()
}

/**
 * Example ViewModel usage:
 *
 * ```kotlin
 * @HiltViewModel
 * class AccountViewModel @Inject constructor(
 *     private val getAccountUseCase: GetAccountUseCase
 * ) : ViewModel() {
 *
 *     private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
 *     val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()
 *
 *     init {
 *         loadAccount()
 *     }
 *
 *     private fun loadAccount() {
 *         viewModelScope.launch {
 *             getAccountUseCase().collect { result ->
 *                 _uiState.value = result.fold(
 *                     onSuccess = { account ->
 *                         // Determine which success state to use
 *                         when (val current = _uiState.value) {
 *                             is AccountUiState.Loading, is AccountUiState.Error -> {
 *                                 // First time loading data
 *                                 AccountUiState.InitialSuccess(account)
 *                             }
 *                             is AccountUiState.RefreshableSuccess -> {
 *                                 // Refresh completed
 *                                 AccountUiState.RefreshableSuccess(account, isRefreshing = false)
 *                             }
 *                             is AccountUiState.InitialSuccess -> {
 *                                 // Stay in initial success
 *                                 current
 *                             }
 *                         }
 *                     },
 *                     onFailure = { error ->
 *                         // Only show error if we don't have data
 *                         when (val current = _uiState.value) {
 *                             is AccountUiState.InitialSuccess,
 *                             is AccountUiState.RefreshableSuccess -> {
 *                                 // Keep showing stale data, stop refreshing
 *                                 AccountUiState.RefreshableSuccess(
 *                                     account = (current as? AccountUiState.InitialSuccess)?.account
 *                                         ?: (current as AccountUiState.RefreshableSuccess).account,
 *                                     isRefreshing = false
 *                                 )
 *                             }
 *                             else -> {
 *                                 // No data yet, show error
 *                                 AccountUiState.Error(error.message ?: "Unknown error")
 *                             }
 *                         }
 *                     }
 *                 )
 *             }
 *         }
 *     }
 *
 *     fun refresh() {
 *         viewModelScope.launch {
 *             val currentState = _uiState.value
 *             when (currentState) {
 *                 is AccountUiState.InitialSuccess -> {
 *                     // Transition to refreshable state
 *                     _uiState.value = AccountUiState.RefreshableSuccess(
 *                         account = currentState.account,
 *                         isRefreshing = true
 *                     )
 *                 }
 *                 is AccountUiState.RefreshableSuccess -> {
 *                     // Already refreshing or keep current data
 *                     _uiState.value = currentState.copy(isRefreshing = true)
 *                 }
 *                 is AccountUiState.Loading, is AccountUiState.Error -> {
 *                     // No data yet, show loading
 *                     _uiState.value = AccountUiState.Loading
 *                 }
 *             }
 *
 *             // Force refresh from network
 *             getAccountUseCase.refresh()
 *         }
 *     }
 * }
 * ```
 *
 * Example Composable usage:
 *
 * ```kotlin
 * @Composable
 * fun AccountScreen(viewModel: AccountViewModel = hiltViewModel()) {
 *     val uiState by viewModel.uiState.collectAsState()
 *
 *     when (val state = uiState) {
 *         is AccountUiState.Loading -> {
 *             // Full-screen loading (only on initial load)
 *             Box(Modifier.fillMaxSize()) {
 *                 CircularProgressIndicator(Modifier.align(Alignment.Center))
 *             }
 *         }
 *         is AccountUiState.InitialSuccess -> {
 *             // Show account data with pull-to-refresh capability
 *             SwipeRefresh(
 *                 state = rememberSwipeRefreshState(isRefreshing = false),
 *                 onRefresh = { viewModel.refresh() }
 *             ) {
 *                 AccountContent(account = state.account)
 *             }
 *         }
 *         is AccountUiState.RefreshableSuccess -> {
 *             // Show account data with active refresh indicator
 *             SwipeRefresh(
 *                 state = rememberSwipeRefreshState(state.isRefreshing),
 *                 onRefresh = { viewModel.refresh() }
 *             ) {
 *                 AccountContent(account = state.account)
 *             }
 *         }
 *         is AccountUiState.Error -> {
 *             // Error screen with retry button
 *             ErrorScreen(
 *                 message = state.message,
 *                 onRetry = { viewModel.refresh() }
 *             )
 *         }
 *     }
 * }
 * ```
 */
