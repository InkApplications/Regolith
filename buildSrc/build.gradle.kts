plugins {
    `kotlin-dsl`
}
repositories {
    gradlePluginPortal()
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}
dependencies {
    implementation(libs.kotlin.gradle)
    implementation(libs.ink.publishing)
}
