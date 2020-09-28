plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}