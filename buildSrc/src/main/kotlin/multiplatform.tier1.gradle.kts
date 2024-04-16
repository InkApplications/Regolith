plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvmToolchain(11)
    jvm()
    js(IR) {
        nodejs()
        browser {
            testTask {
                useKarma {
                    useFirefoxHeadless()
                }
            }
        }
    }
    macosX64()
    macosArm64()
    iosSimulatorArm64()
    iosX64()
}

