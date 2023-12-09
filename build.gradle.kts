import org.gradle.api.tasks.testing.logging.TestExceptionFormat

gradle.startParameter.excludedTaskNames.add("lint")

subprojects {
    repositories {
        mavenCentral()
    }
    tasks.withType(Test::class) {
        testLogging.exceptionFormat = TestExceptionFormat.FULL
    }
}

repositories {
    mavenCentral()
}
