import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.apkupdater"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.apkupdater"

        minSdk = 24
        targetSdk = 34

        versionCode = 51
        versionName = "3.0.2"

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    applicationVariants.configureEach {
        outputs.configureEach {
            val variant = (this as BaseVariantOutputImpl)
            variant.outputFileName = defaultConfig.applicationId + "-" + buildType.name + ".apk"
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.8" }
    kotlinOptions { jvmTarget = "11" }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packaging {
        resources {
            excludes.add("**/**.version")
            excludes.add("**/**.properties")
            excludes.add("**/DEPENDENCIES")
            excludes.add("**/DEPENDENCIES.txt")
            excludes.add("**/dependencies.txt")
            excludes.add("**/LICENSE")
            excludes.add("**/LICENSE.txt")
            excludes.add("**/license.txt")
            excludes.add("**/NOTICE")
            excludes.add("**/NOTICE.txt")
            excludes.add("**/notice.txt")
            excludes.add("**/AL2.0")
            excludes.add("**/LGPL2.1")
        }
    }

    lint {
        warning.addAll(arrayOf("ExtraTranslation", "MissingTranslation", "MissingQuantity"))
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    implementation("androidx.compose.material3:material3:1.2.0-alpha12")
    implementation("androidx.tv:tv-foundation:1.0.0-alpha10")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui:1.5.4")

    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("io.insert-koin:koin-android:3.5.3")
    implementation("io.insert-koin:koin-androidx-compose:3.5.3")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("com.github.rumboalla.KryptoPrefs:kryptoprefs:0.4.3")
    implementation("com.github.rumboalla.KryptoPrefs:kryptoprefs-gson:0.4.3")
    implementation("org.jsoup:jsoup:1.17.2")
    //implementation("com.github.topjohnwu.libsu:core:5.2.1")
    //implementation("io.github.g00fy2:versioncompare:1.5.0")

    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")
}