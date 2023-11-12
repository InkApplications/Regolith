plugins {
    id("multiplatform.tier2")
    id("ink.publishing")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.coroutines.core)
                api(libs.kotlinx.datetime)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test.core)
                implementation(libs.kotlin.test.junit)
                implementation(libs.coroutines.test)
            }
        }
    }
}
