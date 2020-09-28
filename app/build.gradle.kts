plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Versions.Sdk.COMPILE_VERSION)

    defaultConfig {
        minSdkVersion(Versions.Sdk.MIN_VERSION)
        targetSdkVersion(Versions.Sdk.TARGET_VERSION)

        applicationId = Constants.APPLICATION_ID
        versionCode = Versions.VERSION_CODE
        versionName = Versions.VERSION_NAME

        testInstrumentationRunner = Constants.TEST_RUNNER

        javaCompileOptions {
            annotationProcessorOptions {
                includeCompileClasspath = true
                arguments = mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        getByName(Constants.BuildTypes.RELEASE) {
            isMinifyEnabled = false
            consumerProguardFile("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    dependencies {
        implementation(Dependencies.ANDROID_CONSTRAINT_LAYOUT)
        implementation(Dependencies.ANDROID_RECYCLERVIEW)
        implementation(Dependencies.ANDROID_MATERIAL)

        implementation(Dependencies.DAGGER)
        kapt(Dependencies.DAGGER_COMPILER)

        implementation(Dependencies.ROOM)
        implementation(Dependencies.ROOM_RX_JAVA)
        kapt(Dependencies.ROOM_COMPILER)

        implementation(Dependencies.RX_ANDROID)
        implementation(Dependencies.RX_KOTLIN)

        implementation(Dependencies.KOTLIN_STD)
        implementation(Dependencies.KOTLIN_CORE)

        implementation(Dependencies.JUNIT)
        implementation(Dependencies.RUNNER)
        implementation(Dependencies.ESPRESSO)
        debugImplementation(Dependencies.DEBUG_DB)
    }
}
