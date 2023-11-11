plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(11)
    jvm()

    js(IR) {
        nodejs()
        browser()
        browser {
            testTask {
                useKarma {
                    useFirefoxHeadless()
                }
            }
        }
    }

    // KMP Tier 1:
    macosX64()
    macosArm64()
    iosSimulatorArm64()
    iosX64()

    // KMP Tier 2:
    linuxX64()
    linuxArm64()
    watchosSimulatorArm64()
    watchosX64()
    watchosArm32()
    watchosArm64()
    tvosSimulatorArm64()
    tvosX64()
    tvosArm64()
    iosArm64()

    // KMP Tier 3:
    androidNativeArm32()
    androidNativeArm64()
    androidNativeX86()
    androidNativeX64()
    mingwX64()
    watchosDeviceArm64()
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                name.set("Regolith: ${project.name}")
                description.set("Bootstrapping and Configuration Toolkit")
                url.set("https://inkapplications.com")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://choosealicense.com/licenses/mit/")
                    }
                }
                developers {
                    developer {
                        id.set("reneevandervelde")
                        name.set("Renee Vandervelde")
                        email.set("Renee@InkApplications.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/InkApplications/regolith.git")
                    developerConnection.set("scm:git:ssh://git@github.com:InkApplications/regolith.git")
                    url.set("https://github.com/InkApplications/regolith")
                }
            }
        }
    }
}
