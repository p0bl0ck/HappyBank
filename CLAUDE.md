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
│   │   │   ├── MainActivity.kt (Compose Activity)
│   │   │   ├── HappyBankApp.kt (Navigation & App Structure)
│   │   │   ├── HappyBankApplication.kt
│   │   │   └── ui/
│   │   │       ├── screen/
│   │   │       │   ├── HomeScreen.kt
│   │   │       │   └── ServicesScreen.kt
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
├── gradle/
│   ├── libs.versions.toml (Version catalog)
│   └── wrapper/
├── build.gradle.kts (Kotlin DSL with version catalog)
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
- **Architecture**: Compose Navigation with MVVM patterns
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
- **Testing**: JUnit 6.0.0 (Jupiter) with comprehensive parameterized tests
- **Compose**: BOM 2024.12.01 with Material Icons Extended

## Common Commands

- **Build**: `./gradlew build`
- **Run tests**: `./gradlew test` (JUnit 6 with detailed reporting)
- **Run unit tests only**: `./gradlew testDebugUnitTest`
- **Clean**: `./gradlew clean`
- **Install debug APK**: `./gradlew installDebug`
- **Lint check**: `./gradlew lint`
- **Generate APK**: `./gradlew assembleDebug`
- **Preview Compose**: Use Android Studio's @Preview annotations

## Development Guidelines

- Follow Material Design 3 principles with Compose
- Use Composables for all UI components
- Implement Compose Navigation for screen transitions
- Maintain security best practices for banking app
- Use the established HappyBank color scheme (green primary, blue accent)
- Follow existing package structure in `com.happybank.app`
- **Kotlin DSL**: All build files use Kotlin DSL for type safety and better IDE support
- **Version Catalogs**: Use `libs.versions.toml` for centralized dependency management
- **Compose**: Use @Preview annotations for UI development and testing
- **State Management**: Use remember, derivedStateOf for local state
- Leverage Gradle configuration cache and build cache for faster builds
- When adding new dependencies, update the version catalog first for consistency

## Current Features

- Modern Compose-based welcome screen with banking dashboard
- Interactive banking services screen with Material 3 cards
- Comprehensive Material Design 3 theming with HappyBank branding
- Type-safe Compose Navigation between screens
- Quick action cards (Transfer, Balance, Investment)
- Floating Action Button with Material 3 styling
- Dark mode support with dynamic theming
- Secure backup and data extraction rules
- Adaptive launcher icons with banking theme
- Full build system with debug and release variants
- Gradle wrapper configured for cross-platform development

## Build Status

✅ **Project fully modernized with Jetpack Compose and builds successfully**
- Debug APK: `./gradlew assembleDebug` (7MB optimized APK)
- Release APK: `./gradlew assembleRelease`
- All tests pass: `./gradlew test` (18 unit tests including account validation)
- Lint checks pass with no critical issues
- Compose previews available in Android Studio

## Project Configuration

- **gradle.properties**: AndroidX enabled, optimized JVM settings, Kotlin DSL caching
- **Build Scripts**: Full Kotlin DSL migration with type safety and modern syntax
- **Version Catalog**: Centralized dependency management in `libs.versions.toml`
- **Dependency Bundles**: Organized dependency groups for easier management
- **Compose**: BOM-managed dependencies with Material Icons Extended
- **Icons**: Adaptive icons with HappyBank green branding
- **Proguard**: Release build optimization configured
- **Security**: Data extraction rules configured for banking app
- **Performance**: Configuration cache and build cache enabled for faster builds
- **Architecture**: Clean separation with Compose screens and navigation

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
- **HomeScreen**: Banking dashboard with quick actions and welcome card
- **ServicesScreen**: Interactive list of banking services
- **Navigation**: Seamless transitions with back stack management
- **Theming**: Consistent Material 3 design across all screens