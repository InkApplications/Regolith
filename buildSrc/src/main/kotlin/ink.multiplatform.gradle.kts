plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
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
    repositories {
        val mavenUser = findProperty("mavenUser")?.toString()
        val mavenPassword = findProperty("mavenPassword")?.toString()
        if (mavenUser != null && mavenPassword != null) {
            maven {
                name = "MavenCentral"
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = mavenUser
                    password = mavenPassword
                }
            }
        }
    }
    publications {
        withType<MavenPublication> {
            pom {
                name.set("Regolith: ${project.name}")
                description.set("General purpose application interfaces")
                url.set("https://github.com/InkApplications/Regolith")
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
    signing {
        val signingKey = findProperty("signingKey")?.toString()
        val signingKeyId = findProperty("signingKeyId")?.toString()
        val signingPassword = findProperty("signingPassword")?.toString()
        val shouldSign = signingKeyId != null && signingKey != null && signingPassword != null

        if (shouldSign) {
            useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
            sign(publications)
        }
    }
}
