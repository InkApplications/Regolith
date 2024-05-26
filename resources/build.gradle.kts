plugins {
    id("multiplatform.tier2")
    id("multiplatform.android")
    id("ink.publishing")
}

android {
    namespace = "com.inkapplications.regolith.resources"
}

kotlin {
    androidTarget {
        publishAllLibraryVariants()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.coroutines.core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test.core)
                implementation(libs.coroutines.test)
            }
        }
    }
}
