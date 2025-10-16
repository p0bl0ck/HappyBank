# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

HappyBank is a fully modernized Android banking application built with Kotlin and cutting-edge Android development practices. The project uses Jetpack Compose with Material Design 3, Compose Navigation, Clean Architecture, and modern reactive architecture for a professional banking app experience.

## Repository Structure

```
HappyBank/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/happybank/app/
│   │   │   │   ├── MainActivity.kt (@AndroidEntryPoint)
│   │   │   │   ├── HappyBankApp.kt (Navigation)
│   │   │   │   ├── HappyBankApplication.kt (@HiltAndroidApp)
│   │   │   │   ├── core/                    # Shared modules
│   │   │   │   │   ├── ui/theme/            # App-wide theming
│   │   │   │   │   │   ├── Color.kt
│   │   │   │   │   │   ├── Theme.kt
│   │   │   │   │   │   └── Type.kt
│   │   │   │   │   ├── domain/              # Shared domain logic
│   │   │   │   │   │   └── model/           # Core value objects
│   │   │   │   │   │       ├── Currency.kt  # Currency value object
│   │   │   │   │   │       └── Money.kt     # Money value object
│   │   │   │   │   ├── data/                # Shared data layer
│   │   │   │   │   │   ├── NetworkModule.kt # Retrofit/OkHttp DI
│   │   │   │   │   │   └── api/             # Shared API models
│   │   │   │   │   │       └── ApiResponse.kt
│   │   │   │   │   └── util/                # Common utilities
│   │   │   │   └── feature/                 # Feature modules
│   │   │   │       ├── home/
│   │   │   │       │   ├── presentation/    # UI Layer
│   │   │   │       │   │   ├── HomeScreen.kt
│   │   │   │       │   │   └── HomeViewModel.kt
│   │   │   │       │   ├── domain/          # Business logic
│   │   │   │       │   │   ├── model/       # Domain models
│   │   │   │       │   │   └── usecase/     # Use cases
│   │   │   │       │   └── data/            # Data layer
│   │   │   │       │       ├── api/         # API interfaces
│   │   │   │       │       │   └── AccountApi.kt
│   │   │   │       │       ├── model/       # DTOs
│   │   │   │       │       │   └── AccountDto.kt
│   │   │   │       │       └── repository/  # Repositories
│   │   │   │       └── services/
│   │   │   │           ├── presentation/
│   │   │   │           │   └── ServicesScreen.kt
│   │   │   │           ├── domain/
│   │   │   │           └── data/
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   ├── mock/                            # Mock build flavor
│   │   │   ├── assets/mock/                 # JSON mock responses
│   │   │   │   ├── account.json
│   │   │   │   ├── balance.json
│   │   │   │   └── transactions.json
│   │   │   └── java/com/happybank/app/
│   │   │       ├── ApplicationInitializer.kt # Starts MockServer
│   │   │       └── mock/
│   │   │           ├── MockServer.kt        # NanoHTTPD server
│   │   │           └── MockServerModule.kt  # Hilt DI
│   │   └── prod/                            # Production build flavor
│   │       └── java/com/happybank/app/
│   │           └── ApplicationInitializer.kt # Empty (no mock)
│   ├── build.gradle.kts (Flavors: mock, prod)
│   └── proguard-rules.pro
├── config/
│   └── detekt/
│       └── detekt.yml (Detekt configuration)
├── gradle/
│   ├── libs.versions.toml (Version catalog)
│   └── wrapper/
├── build.gradle.kts (Kotlin DSL with Detekt)
├── settings.gradle.kts (Kotlin DSL settings)
├── gradle.properties (enhanced for Kotlin DSL)
└── gradlew / gradlew.bat
```

## Technology Stack

- **Language**: Kotlin 2.2.20 (latest stable)
- **Build System**: Gradle 8.13 with Android Gradle Plugin 8.13.0
- **Build Configuration**: Kotlin DSL (.kts files) for type safety and IDE support
- **Dependency Management**: Gradle Version Catalogs (TOML) for centralized version control
- **UI Framework**: Jetpack Compose with Material Design 3
- **Architecture**: Clean Architecture with MVVM and Hilt dependency injection
- **Dependency Injection**: Hilt 2.57.2 with kapt annotation processing
- **Code Quality**: Detekt 1.23.7 with detekt-formatting plugin
- **Compose**: Declarative UI with type-safe navigation
- **JVM Target**: Java 17 (modern features, required for JUnit 6)
- **Target SDK**: 36 (Android 15)
- **Compile SDK**: 36 (Android 15 API Level 36)
- **Minimum SDK**: 24 (Android 7.0)
- **AndroidX**: Enabled with gradle.properties configuration

## Key Dependencies (All Latest 2024-2025 Versions)

- **AndroidX Core KTX**: 1.17.0 (latest with Android 15 support)
- **AppCompat**: 1.7.1 (latest compatibility library)
- **Material Design**: 1.13.0 (Material 3 with latest components)
- **ConstraintLayout**: 2.2.1 (latest layout system)
- **Navigation Component**: 2.9.5 (latest navigation framework)
- **Lifecycle Components**: 2.9.4 (ViewModel, LiveData latest)
- **Hilt**: 2.57.2 (Dependency Injection with Compose integration)
- **Detekt**: 1.23.7 (Static code analysis with formatting plugin)
- **Retrofit**: 2.11.0 (Type-safe REST API client)
- **OkHttp**: 4.12.0 (HTTP client with logging interceptor)
- **Kotlin Serialization**: 1.8.0 (Type-safe JSON serialization)
- **NanoHTTPD**: 2.3.1 (Mock server for development - mock flavor only)
- **Testing**: JUnit 6.0.0 (Jupiter) with comprehensive parameterized tests
- **Compose**: BOM 2025.10.00 with Material Icons Extended

## Common Commands

### Build & Test
- **Build all flavors**: `./gradlew build`
- **Build mock flavor**: `./gradlew assembleMockDebug`
- **Build prod flavor**: `./gradlew assembleProdDebug`
- **Run tests**: `./gradlew test` (JUnit 6 with detailed reporting)
- **Run unit tests only**: `./gradlew testDebugUnitTest`
- **Clean**: `./gradlew clean`
- **Install mock APK**: `./gradlew installMockDebug`
- **Install prod APK**: `./gradlew installProdDebug`

### Code Quality
- **Run Detekt**: `./gradlew detekt` (static code analysis)
- **Auto-format code**: `./gradlew detektFormat` (fix style issues)
- **Detekt reports**: `app/build/reports/detekt/detekt.html`
- **Lint check**: `./gradlew lint`

### Development
- **Preview Compose**: Use Android Studio's @Preview annotations
- **Sync Gradle**: Update dependencies from version catalog

## Development Guidelines

### UI & Design
- Follow Material Design 3 principles with Compose
- Use Composables for all UI components
- Implement Compose Navigation for screen transitions
- Use @Preview annotations for UI development and testing
- Use the established HappyBank color scheme (green primary, blue accent)

### Architecture & Code
- **Clean Architecture**: Follow layer separation (presentation, domain, data)
- **Feature Modules**: Organize code by feature in `feature/` directory
- **MVVM Pattern**: Use ViewModels for presentation logic
- **Dependency Injection**: Use @HiltViewModel and @Inject for DI
- **State Management**: Use StateFlow for reactive UI state
- **Compose State**: Use remember, derivedStateOf for local state
- **Core Module**: Use `core/` for shared utilities, theme, and common code
- **Package Structure**: `feature/{feature-name}/{presentation,domain,data}`
- Maintain security best practices for banking app

### Build & Dependencies
- **Kotlin DSL**: All build files use Kotlin DSL for type safety
- **Version Catalogs**: Use `libs.versions.toml` for centralized dependency management
- When adding dependencies, update the version catalog first
- Leverage Gradle configuration cache and build cache for faster builds

### Code Quality
- **Run Detekt** before committing code (`./gradlew detekt`)
- Fix all code style issues with `./gradlew detektFormat`
- Follow Kotlin coding conventions (4 spaces, no tabs)
- Keep functions under 80 lines (Composables exempt)
- Avoid magic numbers, use named constants
- Write meaningful test cases with JUnit 6

## Current Features

### UI & Navigation
- Modern Compose-based welcome screen with banking dashboard
- Interactive banking services screen with Material 3 cards
- Comprehensive Material Design 3 theming with HappyBank branding
- Type-safe Compose Navigation between screens
- Quick action cards (Transfer, Balance, Investment)
- Floating Action Button with Material 3 styling
- Dark mode support with dynamic theming

### Architecture & DI
- **Clean Architecture** with feature-based folder structure
- **Feature modules**: `home` and `services` with layer separation
- **Core module**: Shared theme, domain, data, and utilities
- **Hilt** dependency injection fully integrated
- **HomeViewModel** with StateFlow for reactive state
- @HiltViewModel annotation for ViewModel injection
- @AndroidEntryPoint on MainActivity
- Dynamic user data display from ViewModel
- Prepared for future Gradle modularization

### Code Quality
- **Detekt** static code analysis configured
- detekt-formatting plugin with ktlint rules
- 0 code smells achieved
- Comprehensive rule set (complexity, naming, style, potential bugs)
- Auto-formatting capabilities

### Infrastructure
- Secure backup and data extraction rules
- Adaptive launcher icons with banking theme
- Full build system with debug and release variants
- Gradle wrapper configured for cross-platform development

## Build Status

✅ **Project fully modernized with Jetpack Compose, Clean Architecture, Hilt DI, and domain value objects**
- **Build**: `./gradlew build` ✅ Successful (JVM 17)
- **Tests**: `./gradlew testMockDebugUnitTest` ✅ All 83 unit tests pass
- **Detekt**: `./gradlew detekt` ✅ 0 code smells (production code)
- **Lint**: `./gradlew lint` ✅ No critical issues
- **All 4 variants**: mockDebug, mockRelease, prodDebug, prodRelease ✅
- **Compose previews**: Available in Android Studio

## Project Configuration

### Build System
- **gradle.properties**: AndroidX enabled, optimized JVM settings, Kotlin DSL caching
- **Build Scripts**: Full Kotlin DSL migration with type safety and modern syntax
- **Version Catalog**: Centralized dependency management in `libs.versions.toml`
- **Dependency Bundles**: Organized groups (core, lifecycle, compose, junit6-testing)
- **Performance**: Configuration cache and build cache enabled

### Dependency Injection (Hilt)
- **Application**: @HiltAndroidApp on HappyBankApplication
- **Activity**: @AndroidEntryPoint on MainActivity
- **ViewModels**: @HiltViewModel with @Inject constructor
- **Navigation**: hilt-navigation-compose integration
- **Config**: enableAggregatingTask = false for compatibility

### Code Quality (Detekt)
- **Config**: `config/detekt/detekt.yml` with custom rules
- **Formatting**: detekt-formatting plugin with ktlint
- **Rules**: Complexity, naming, style, potential bugs, coroutines
- **Compose**: Special rules for @Composable functions
- **Reports**: HTML, XML, SARIF, Markdown output

### UI & Resources
- **Compose**: BOM 2025.10.00 with Material Icons Extended
- **Icons**: Adaptive icons with HappyBank green branding
- **Proguard**: Release build optimization configured
- **Security**: Data extraction rules configured for banking app
- **Architecture**: MVVM with Hilt DI and reactive state management

## Version Catalog Structure

The `gradle/libs.versions.toml` file contains:
- **[versions]**: All dependency versions in one place
- **[libraries]**: Individual library definitions
- **[bundles]**: Grouped related dependencies (core, lifecycle, compose, junit6-testing)
- **[plugins]**: Build plugins with version management

## Testing Framework

**JUnit 6 (Jupiter) Features:**
- Modern testing framework with better assertions and annotations
- `@DisplayName` for readable test descriptions
- `@Nested` classes for organized test structure
- `@ParameterizedTest` with `@ValueSource` for data-driven tests
- JUnit Platform integration with Gradle
- Comprehensive account validation tests (11 test cases)
- Sample test class demonstrating banking validation patterns

## Jetpack Compose Architecture

**Modern UI Framework:**
- **Material Design 3**: Full theming with HappyBank brand colors
- **Compose Navigation**: Type-safe navigation between screens
- **State Management**: Reactive UI with remember and state handling
- **Components**: Reusable banking UI components (cards, buttons, etc.)
- **Previews**: @Preview annotations for rapid UI development
- **Dark Mode**: Dynamic theming with system theme switching
- **Accessibility**: Built-in accessibility support
- **Performance**: Optimized recomposition and state management

**Screen Structure:**
- **HomeScreen**: Banking dashboard with quick actions, welcome card, and ViewModel integration
  - Uses HomeViewModel for state management
  - Displays dynamic user name and account balance
  - StateFlow for reactive UI updates
- **ServicesScreen**: Interactive list of banking services
- **Navigation**: Seamless transitions with back stack management
- **Theming**: Consistent Material 3 design across all screens

## Hilt Dependency Injection

**Setup:**
```kotlin
// Application
@HiltAndroidApp
class HappyBankApplication : Application()

// Activity
@AndroidEntryPoint
class MainActivity : ComponentActivity()

// ViewModel
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _userName = MutableStateFlow("Guest")
    val userName: StateFlow<String> = _userName.asStateFlow()
}

// Composable
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val userName by viewModel.userName.collectAsState()
}
```

## Detekt Code Quality

**Running Detekt:**
```bash
# Check code quality
./gradlew detekt

# Auto-fix formatting issues
./gradlew detektFormat

# View reports
open app/build/reports/detekt/detekt.html
```

**Key Rules Enforced:**
- **Complexity**: Max method length 80 lines, cyclomatic complexity 15
- **Naming**: Kotlin conventions (camelCase, ignore @Composable/@Test)
- **Style**: 120 char line length, no magic numbers, final newlines
- **Formatting**: 4 spaces indentation, no trailing spaces, proper spacing
- **Potential Bugs**: Unnecessary operators, unreachable code, equals/hashcode
- **Coroutines**: Global usage checks, suspend function validations

## Clean Architecture

**Layer Structure:**
The project follows Clean Architecture principles with clear separation of concerns:

```
feature/
└── {feature-name}/
    ├── presentation/    # UI Layer
    │   ├── Screen.kt    # Composables
    │   └── ViewModel.kt # UI state & logic
    ├── domain/          # Business Logic Layer
    │   ├── model/       # Domain entities
    │   └── usecase/     # Business rules
    └── data/            # Data Layer
        ├── model/       # DTOs, API models
        └── repository/  # Data sources
```

**Dependency Rules:**
- **Presentation** depends on **Domain**
- **Domain** is independent (pure Kotlin)
- **Data** depends on **Domain**
- Dependencies point inward (toward domain)

**Example Feature Implementation:**
```kotlin
// Domain Layer (pure business logic)
package com.happybank.app.feature.home.domain.model
data class UserAccount(val name: String, val balance: Double)

package com.happybank.app.feature.home.domain.usecase
class GetUserAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(): Result<UserAccount>
}

// Data Layer (data sources)
package com.happybank.app.feature.home.data.repository
class AccountRepositoryImpl @Inject constructor(
    private val api: BankingApi,
    private val cache: AccountCache
) : AccountRepository {
    override suspend fun getUserAccount(): Result<UserAccount>
}

// Presentation Layer (UI)
package com.happybank.app.feature.home.presentation
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserAccount: GetUserAccountUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
}
```

**Core Module:**
Shared code lives in `core/`:
- `core/ui/theme/` - App-wide theming
- `core/domain/` - Shared domain models/interfaces
- `core/data/` - Shared data utilities
- `core/util/` - Common utilities

**Benefits:**
- **Testability**: Each layer can be tested independently
- **Maintainability**: Clear responsibilities and boundaries
- **Scalability**: Easy to add new features
- **Modularity**: Prepared for Gradle multi-module setup

## Networking Infrastructure

**Stack:**
The project uses modern networking with Retrofit, OkHttp, and Kotlin Serialization.

### NetworkModule (Hilt DI)
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // Flavor-specific
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}
```

### API Interfaces (Retrofit)
```kotlin
interface AccountApi {
    @GET("api/v1/account")
    suspend fun getAccount(
        @Header("Authorization") authorization: String
    ): ApiResponse<AccountDto>
}
```

### DTOs (Kotlin Serialization)
```kotlin
@Serializable
data class AccountDto(
    @SerialName("account_id") val accountId: String,
    @SerialName("balance") val balance: Double,
    @SerialName("user_name") val userName: String
)
```

### Generic Response Wrappers
```kotlin
@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
)

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()
}
```

**Configuration:**
- **JSON**: Ignore unknown keys, coerce null values, lenient parsing
- **OkHttp**: 30s timeouts, logging interceptor (debug), ready for auth
- **Retrofit**: Suspend functions for coroutines, type-safe API calls

## Build Flavors & Mock Server

**The project uses build flavors to separate mock and production environments.**

### Flavors Configuration
```kotlin
flavorDimensions += "environment"
productFlavors {
    create("mock") {
        dimension = "environment"
        applicationIdSuffix = ".mock"
        buildConfigField("String", "BASE_URL", "\"http://localhost:8080/\"")
    }
    create("prod") {
        dimension = "environment"
        buildConfigField("String", "BASE_URL", "\"https://api.happybank.com/\"")
    }
}
```

### Mock Server (NanoHTTPD)

**Purpose**: On-device HTTP server serving JSON from assets for development/testing.

**MockServer.kt** (mock flavor only):
```kotlin
class MockServer @Inject constructor(
    private val context: Context
) : NanoHTTPD(8080) {

    override fun serve(session: IHTTPSession): Response {
        return when {
            session.uri.startsWith("/api/v1/account") ->
                serveJsonFromAssets("mock/account.json")
            session.uri.startsWith("/api/v1/transactions") ->
                serveJsonFromAssets("mock/transactions.json")
            else -> notFound()
        }
    }

    private fun serveJsonFromAssets(path: String): Response {
        val json = context.assets.open(path).bufferedReader().use { it.readText() }
        return newFixedLengthResponse(Response.Status.OK, "application/json", json)
    }
}
```

### Mock JSON Files

Located in `app/src/mock/assets/mock/`:

**account.json**:
```json
{
  "success": true,
  "data": {
    "account_id": "ACC-001",
    "account_number": "1234567890",
    "account_type": "Checking",
    "balance": 12345.67,
    "currency": "USD",
    "user_name": "John Doe"
  }
}
```

**transactions.json**: List of transaction records
**balance.json**: Simple balance response

### Flavor-Specific Source Sets

```
app/src/
├── main/           # Shared code for all flavors
├── mock/           # Mock flavor only
│   ├── assets/mock/         # JSON responses
│   └── java/.../
│       ├── ApplicationInitializer.kt  # Starts MockServer
│       └── mock/
│           ├── MockServer.kt          # NanoHTTPD
│           └── MockServerModule.kt    # Hilt DI
└── prod/           # Production flavor only
    └── java/.../
        └── ApplicationInitializer.kt  # Empty (no mock server)
```

### Usage

**Development with mock server**:
```bash
./gradlew installMockDebug
# App starts with NanoHTTPD on localhost:8080
# All API calls go to mock server
# Modify JSON files in assets/mock/ to change responses
```

**Production build**:
```bash
./gradlew assembleProdRelease
# Zero mock code included
# API calls go to real backend
# Smaller APK size
```

**Benefits:**
- ✅ Develop without backend dependency
- ✅ Fast iteration (edit JSON, restart app)
- ✅ Consistent test data
- ✅ Zero mock code in production
- ✅ Real HTTP calls (not interceptors)
- ✅ Easy to add new mock endpoints

## Domain Value Objects

**Core financial value objects with type safety.**

### Currency & Money
- **Currency.kt** (`core/domain/model/`) - Type-safe currency representation
  - 8 predefined currencies (USD, EUR, GBP, JPY, PLN, CHF, CAD, AUD)
  - Custom currency support via `Currency.fromCode()`

- **Money.kt** (`core/domain/model/`) - Immutable monetary amounts
  - Type-safe arithmetic: `money1 + money2`, `money * 2`
  - Currency validation: prevents mixing different currencies
  - Formatted display: `$1,234.56`, `€1.234,56`
  - Extension functions: `100.usd()`, `50.eur()`

**Usage:**
```kotlin
val price = 100.usd()
val discount = price * 0.20
val final = price - discount  // $80.00
```

**Documentation:** See `VALUE_OBJECTS.md` for detailed examples and patterns.

**Testing:** 83 unit tests (21 Currency + 62 Money) - all passing