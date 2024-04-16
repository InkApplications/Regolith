plugins {
    id("multiplatform.tier1")
    id("multiplatform.android")
    id("app.cash.sqldelight")
    id("ink.publishing")
}

android {
    namespace = "com.inkapplications.regolith.data"
}


sqldelight {
    databases {
        create("Settings") {
            packageName.set("regolith.data")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
            generateAsync = true
        }
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
                implementation(libs.sqldelight.coroutines)
                api(libs.watermelon.data)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.sqldelight.jvm)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.sqldelight.android)
            }
        }

        val nativeMain by getting {
            dependencies {
                implementation(libs.sqldelight.native)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.sqldelight.web.worker.driver)
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
                implementation(npm("sql.js", "1.6.2"))
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
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
