# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

HappyBank is a fully initialized Android banking application built with Kotlin and modern Android development practices. The project uses Material Design 3, Navigation Component, and ViewBinding for a professional banking app experience.

## Repository Structure

```
HappyBank/
├── app/
│   ├── src/main/
│   │   ├── java/com/happybank/app/
│   │   │   ├── MainActivity.kt
│   │   │   ├── FirstFragment.kt
│   │   │   └── SecondFragment.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── values/ (strings, colors, themes)
│   │   │   ├── navigation/
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
- **UI Framework**: Material Design 3 with AndroidX libraries
- **Architecture**: Navigation Component with Fragments
- **View Binding**: Enabled for type-safe view access
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
- **Testing**: JUnit 6.0.0 (Jupiter), AndroidX Test 1.3.0, Espresso 3.7.0

## Common Commands

- **Build**: `./gradlew build`
- **Run tests**: `./gradlew test` (JUnit 6 with detailed reporting)
- **Run unit tests only**: `./gradlew testDebugUnitTest`
- **Clean**: `./gradlew clean`
- **Install debug APK**: `./gradlew installDebug`
- **Lint check**: `./gradlew lint`
- **Generate APK**: `./gradlew assembleDebug`

## Development Guidelines

- Follow Material Design principles
- Use ViewBinding for view references
- Implement proper navigation patterns
- Maintain security best practices for banking app
- Use the established color scheme (green primary, blue accent)
- Follow existing package structure in `com.happybank.app`
- **Kotlin DSL**: All build files use Kotlin DSL for type safety and better IDE support
- **Version Catalogs**: Use `libs.versions.toml` for centralized dependency management
- Leverage Gradle configuration cache and build cache for faster builds
- When adding new dependencies, update the version catalog first for consistency

## Current Features

- Welcome screen with navigation
- Banking services screen
- Material Design 3 theming with HappyBank branding
- Secure backup and data extraction rules
- Navigation between fragments with Navigation Component
- Floating Action Button (ready for feature implementation)
- Adaptive launcher icons with banking theme
- Full build system with debug and release variants
- Gradle wrapper configured for cross-platform development

## Build Status

✅ **Project fully initialized and builds successfully**
- Debug APK: `./gradlew assembleDebug`
- Release APK: `./gradlew assembleRelease`
- All tests pass: `./gradlew test`
- Lint checks pass with no critical issues

## Project Configuration

- **gradle.properties**: AndroidX enabled, optimized JVM settings, Kotlin DSL caching
- **Build Scripts**: Full Kotlin DSL migration with type safety and modern syntax
- **Version Catalog**: Centralized dependency management in `libs.versions.toml`
- **Dependency Bundles**: Organized dependency groups for easier management
- **Icons**: Adaptive icons with HappyBank green branding
- **Proguard**: Release build optimization configured
- **Security**: Data extraction rules configured for banking app
- **Performance**: Configuration cache and build cache enabled for faster builds

## Version Catalog Structure

The `gradle/libs.versions.toml` file contains:
- **[versions]**: All dependency versions in one place
- **[libraries]**: Individual library definitions
- **[bundles]**: Grouped related dependencies (core, lifecycle, navigation, junit6-testing, android-testing)
- **[plugins]**: Build plugins with version management

## Testing Framework

**JUnit 6 (Jupiter) Features:**
- Modern testing framework with better assertions and annotations
- `@DisplayName` for readable test descriptions
- `@Nested` classes for organized test structure
- `@ParameterizedTest` with `@ValueSource` for data-driven tests
- JUnit Platform integration with Gradle
- Sample test class demonstrating banking validation patterns