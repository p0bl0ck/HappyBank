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
│   ├── build.gradle (app module)
│   └── proguard-rules.pro
├── gradle/wrapper/
├── build.gradle (project)
├── settings.gradle
└── gradlew / gradlew.bat
```

## Technology Stack

- **Language**: Kotlin 2.2.20 (latest stable)
- **Build System**: Gradle 8.13 with Android Gradle Plugin 8.13.0
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
- **Test Libraries**: JUnit 4.13.2, AndroidX Test 1.3.0, Espresso 3.7.0

## Common Commands

- **Build**: `./gradlew build`
- **Run tests**: `./gradlew test`
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

- **gradle.properties**: AndroidX enabled, optimized JVM settings
- **Icons**: Adaptive icons with HappyBank green branding
- **Proguard**: Release build optimization configured
- **Security**: Data extraction rules configured for banking app