plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.navigation)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    jacoco
}

android {
    namespace = "br.com.littlepig"
    compileSdk = 36

    defaultConfig {
        applicationId = "br.com.littlepig"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    viewBinding {
        enable = true
    }
    dataBinding {
        enable = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    jacoco {
        version = "0.8.11"
    }
    tasks.register<JacocoReport>("jacocoTestReport") {
        dependsOn("test")

        reports {
            xml.required.set(true)
            html.required.set(true)
        }

        val fileFilter = listOf(
            "**/R.class", "**/R$*.class",
            "**/BuildConfig.*", "**/Manifest*.*",
            "**/*Test*.*"
        )

        val debugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug") {
            exclude(fileFilter)
        }

        classDirectories.setFrom(files(debugTree))
        sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))
        executionData.setFrom(fileTree(buildDir).include("jacoco/testDebugUnitTest.exec"))
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.hilt)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.coroutines.core)
    kapt(libs.hilt.compiler)
    testImplementation(libs.junit)
    testImplementation("io.mockk:mockk:1.13.3")
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    hilt {
        enableAggregatingTask = false
    }
}