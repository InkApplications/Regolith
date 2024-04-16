plugins {
    `kotlin-dsl`
}
repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

kotlin {
    jvmToolchain(17)
}
dependencies {
    implementation(libs.kotlin.gradle)
    implementation(libs.android.gradle)
    implementation(libs.dokka)
    implementation(libs.kotlinx.binary.compatibility)
    implementation(libs.sqldelight.gradle)
}
