plugins {
    `kotlin-dsl`
}
repositories {
    gradlePluginPortal()
    mavenCentral()
}

kotlin {
    jvmToolchain(11)
}
dependencies {
    implementation(libs.kotlin.gradle)
}
