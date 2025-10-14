# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

HappyBank is a fully modernized Android banking application built with Kotlin and cutting-edge Android development practices. The project uses Jetpack Compose with Material Design 3, Compose Navigation, and modern reactive architecture for a professional banking app experience.

## Repository Structure

```
HappyBank/
├── app/
│   ├── src/main/
│   │   ├── java/com/happybank/app/
│   │   │   ├── MainActivity.kt (@AndroidEntryPoint Compose Activity)
│   │   │   ├── HappyBankApp.kt (Navigation & App Structure)
│   │   │   ├── HappyBankApplication.kt (@HiltAndroidApp)
│   │   │   └── ui/
│   │   │       ├── screen/
│   │   │       │   ├── HomeScreen.kt (with HomeViewModel)
│   │   │       │   └── ServicesScreen.kt
│   │   │       ├── viewmodel/
│   │   │       │   └── HomeViewModel.kt (@HiltViewModel)
│   │   │       └── theme/
│   │   │           ├── Color.kt
│   │   │           ├── Theme.kt
│   │   │           └── Type.kt
│   │   ├── res/
│   │   │   ├── values/ (strings, colors, themes)
│   │   │   ├── mipmap/ (launcher icons)
│   │   │   └── xml/ (backup rules)
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts (Kotlin DSL with version catalog)
│   └── proguard-rules.pro
├── config/
│   └── detekt/
│       └── detekt.yml (Detekt configuration)
├── gradle/
│   ├── libs.versions.toml (Version catalog)
│   └── wrapper/
├── build.gradle.kts (Kotlin DSL with Detekt configuration)
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
- **Architecture**: MVVM with Hilt dependency injection
- **Dependency Injection**: Hilt 2.57.2 with kapt annotation processing
- **Code Quality**: Detekt 1.23.7 with detekt-formatting plugin
- **Compose**: Declarative UI with type-safe navigation
- **JVM Target**: Java 11 (for optimal compatibility)
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
- **Testing**: JUnit 6.0.0 (Jupiter) with comprehensive parameterized tests
- **Compose**: BOM 2025.10.00 with Material Icons Extended

## Common Commands

### Build & Test
- **Build**: `./gradlew build`
- **Run tests**: `./gradlew test` (JUnit 6 with detailed reporting)
- **Run unit tests only**: `./gradlew testDebugUnitTest`
- **Clean**: `./gradlew clean`
- **Install debug APK**: `./gradlew installDebug`
- **Generate APK**: `./gradlew assembleDebug`

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
- **MVVM Pattern**: Use ViewModels for business logic
- **Dependency Injection**: Use @HiltViewModel and @Inject for DI
- **State Management**: Use StateFlow for reactive UI state
- **Compose State**: Use remember, derivedStateOf for local state
- Follow existing package structure in `com.happybank.app`
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
- **Hilt** dependency injection fully integrated
- **HomeViewModel** with StateFlow for reactive state
- @HiltViewModel annotation for ViewModel injection
- @AndroidEntryPoint on MainActivity
- Dynamic user data display from ViewModel

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

✅ **Project fully modernized with Jetpack Compose, Hilt DI, and Detekt linting**
- **Build**: `./gradlew build` ✅ Successful
- **Tests**: `./gradlew test` ✅ All 18 unit tests pass
- **Detekt**: `./gradlew detekt` ✅ 0 code smells
- **Lint**: `./gradlew lint` ✅ No critical issues
- **Debug APK**: `./gradlew assembleDebug` (optimized APK)
- **Release APK**: `./gradlew assembleRelease`
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