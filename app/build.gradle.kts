plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.vishalgupta.learntdd"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.vishalgupta.learntdd"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(kotlin("test"))

    testImplementation(libs.unittest.mockito.inline)
    testImplementation(libs.unittest.mockito)
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)

    implementation (libs.kotlin.stdlib)


    implementation (libs.androidx.appcompat)

    implementation (libs.androidx.constraintlayout)

    implementation (libs.androidx.lifecycle.livedata.ktx)

    implementation (libs.androidx.lifecycle.viewmodel.ktx)

    implementation (libs.retrofit)
    implementation(libs.logging.interceptor)

    implementation (libs.converter.gson)
    implementation (libs.androidx.legacy.support.v4)
    implementation (libs.androidx.recyclerview)
    implementation (libs.okhttp3.idling.resource)


    testImplementation (libs.androidx.core.testing)

    androidTestImplementation (libs.okhttp3.idling.resource)
    androidTestImplementation (libs.androidx.runner)
    androidTestImplementation("com.adevinta.android:barista:4.2.0"){
        exclude(group = "org.jetbrains.kotlin")
    }


    //HILT
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

}