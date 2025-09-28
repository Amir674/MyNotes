plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "ru.atmaratovich.mynotes"
    compileSdk = 36

    viewBinding{
        enable = true
    }

    defaultConfig {
        applicationId = "ru.atmaratovich.mynotes"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // ViewModel урок №31
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

    // Для Kotlin Coroutines
    implementation ("androidx.room:room-ktx:2.5.0")

    // RecyclerView
    implementation ("androidx.recyclerview:recyclerview:1.3.0")

    //NavigationComponent
    implementation ("androidx.navigation:navigation-fragment-ktx:2.9.0")
    implementation ("androidx.navigation:navigation-ui-ktx:2.9.0")

    // ConstraintLayout 2.x — включает MotionLayout
    implementation ("androidx.constraintlayout:constraintlayout:2.2.1")

    // Material Components
    implementation ("com.google.android.material:material:1.12.0")
    implementation ("androidx.core:core-ktx:1.16.0")

    //Coil
    implementation("io.coil-kt:coil:2.5.0")

    // Lifecycle и LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")


    // Room урок №36
    implementation ("androidx.room:room-runtime:2.5.0")
    kapt ("androidx.room:room-compiler:2.5.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}