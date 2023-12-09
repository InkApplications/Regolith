plugins {
    id("android-library")
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
    google()
}

kotlin {
    androidTarget()
}

android {
    compileSdk = 34
}
