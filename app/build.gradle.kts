plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.detekt)
    kotlin("kapt")
}

android {
    namespace = "com.happybank.app"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.happybank.app"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "environment"
    productFlavors {
        create("mock") {
            dimension = "environment"
            applicationIdSuffix = ".mock"
            versionNameSuffix = "-mock"
            buildConfigField("String", "BASE_URL", "\"http://localhost:8080/\"")
        }
        create("prod") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://api.happybank.com/\"")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    hilt {
        enableAggregatingTask = false
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    // Compose BOM - manages all Compose library versions
    implementation(platform(libs.compose.bom))

    // AndroidX Core libraries
    implementation(libs.bundles.androidx.core)

    // Material Design
    implementation(libs.material)

    // Lifecycle libraries
    implementation(libs.bundles.androidx.lifecycle)


    // Jetpack Compose
    implementation(libs.bundles.compose)

    // Compose Navigation
    implementation(libs.bundles.compose.navigation.bundle)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Networking (Retrofit, OkHttp, Kotlin Serialization)
    implementation(libs.bundles.networking)

    // NanoHTTPD - only for mock flavor
    "mockImplementation"(libs.nanohttpd)

    // JUnit 6 Testing
    testImplementation(libs.bundles.junit6.testing)
    testRuntimeOnly(libs.junit6.engine)
    testRuntimeOnly(libs.junit6.platform.launcher)

    // Compose Testing
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.bundles.compose.testing)

    // Compose Debug Tools
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}