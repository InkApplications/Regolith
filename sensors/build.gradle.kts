plugins {
    id("multiplatform.tier3")
    id("multiplatform.android")
    id("ink.publishing")
}

android {
    namespace = "com.inkapplications.regolith.sensors"

    defaultConfig {
        minSdk = 21
    }
}

kotlin {
    androidTarget {
        publishAllLibraryVariants()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.coroutines.core)
                api(libs.spondee.units)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test.core)
                implementation(libs.coroutines.test)
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.androidx.appcompat)
            }
        }
    }
}
