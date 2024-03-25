plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.20" // Les 7 51' Fix this error: Unable to create converter for java.util.List<com.example.biljart.network.ApiRank>
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.biljart"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.biljart"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // changed from 1.8 to 17 Les 9 31'
        targetCompatibility = JavaVersion.VERSION_17 // changed from 1.8 to 17 Les 9 31'
    }
    kotlinOptions {
        jvmTarget = "17" // changed from 1.8 to 17 Les 9 31'
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val nav_version = "2.7.7"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-runtime-ktx:$nav_version")
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Retrofit with Scalar Converter
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // Unit testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Android testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // For Material 3 icons
    implementation("androidx.compose.material:material-icons-extended")

    // Database Room
    val room_version = "2.6.1" // Les 9 32'30: gebruik 2.5.0 werkt wel, hogere versies niet altijd
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version") // see https://kotlinlang.org/docs/ksp-quickstart.html  -> in de andere gradle file: id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
    // ksp: see plugins id("com.google.devtools.ksp")
    testImplementation("androidx.room:room-testing:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
}
