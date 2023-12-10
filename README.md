Regolith
========

A collection of generalized application system components that can be used
in mobile, desktop and web applications to create reusable functionality
across platforms.

Modules
=======

Regolith is broken up into distinct modules that can be used independently.
Each module provides different abstractions on system components common
in applications.

Init Module
-----------

Initializers are the start of your application. They can be used to configure
libraries, or start services.

### Maven Coordinates

    com.inkapplications.regolith:init

### Create an Initializer

The interface for an Initializer has a single-method that is called when
the application starts:

```kotlin
object DatabaseInitializer: Initializer {
    override fun initialize(targetManager: TargetManager) {
        Database.init()
    }
}
```

Initializers are then added to an `InitRunner` to be started immediately
when `initialize()` is called:

```kotlin
fun main() {
    val myInitializers = listOf(DatabaseInitializer)
    RegolithInitRunner(
        initializers = myInitializers
    ).initialize()
}
```

### Initialization Targets

All initializers are started *immediately* on application start. However,
Targets may be used to wait for initializers or to pass data between
initializers.

To create a target, extend the empty `InitTarget` interface:

```kotlin
object DatabaseInitTarget: InitTarget
```

If your initializer provides data that may be needed by other initializers,
it can be passed through the Target as data in the Target object:


```kotlin
data class DatabaseInitTarget(
    val session: DatabaseSession
)
```

Targets can be emitted by any initializer through
`targetManager.postTarget()` provided in the `initialize` call:

```kotlin
object DatabaseInitializer: Initializer {
    override fun initialize(targetManager: TargetManager) {
        val session = Database.init()
        targetManager.postTarget(DatabaseInitTarget(session))
    }
}
```

You can wait for a Target with the `targetManager.awaitTarget()` method:

```kotlin
class TokenRefreshInitializer(
    val api: MyApi
): Initializer {
    override fun initialize(targetManager: TargetManager) {
        // Wait for DatabaseInitTarget and use its provided session data:
        val session = targetManager.awaitTarget(DatabaseInitTarget)
        session.updateToken(api.fetchToken())
    }
}
```

Targets can also be posted to the manager via the `RegolithInitRunner`.
For example, an Android application may use this mechanism to provide
the application instance as a target:

```kotlin
data class ApplicationTarget(
    val application: Application
)

class MyApplication: Application() {
    private val initializers = listOf(DatabaseInitializer)
    private val initRunner = RegolithInitRunner(
        initializers = myInitializers
    )
    override fun onCreate() {
        initRunner.initialize()
        initRunner.postTarget(ApplicationTarget(this))
    }
}
```

Processes
---------

The processes module provices abstractions on long-running components of
your application

### Maven coordinates

    com.inkapplications.regolith:processes

### Daemons

Daemons are similar to initializers, except that they are intended to
never stop running. While initializers may not suspend and must return
for initialization to complete, Daemons *cannot* be completed.

To create a Daemon, implement the `Daemon` interface:

```
class WebServerDaemon(
    private val server: WebServer
): Daemon {
    suspend fun startDaemon(): Nothing {
        server.start()
        throw IllegalStateException("Server exited unexpectedly")
    }
}
```

Daemons can be started with the `DaemonInitializer` which can be run
as a part of the `init` component:

```kotlin
fun main() {
    val daemonInitializer = DaemonInitializer(
        daemons = listOf(webServerDaemon)
    )
    val myInitializers = listOf(daemonInitializer)

    RegolithInitRunner(
        initializers = myInitializers
    ).initialize()
}
```

### Crons

Crons are similar to Daemons, except that they are expected to run and
complete on a predetermined time schedule.

Crons can be created by implementing the `CronJob` interface:

```kotlin
class DatabaseFlushCron(
    private val database: Database
): CronJob {
    override val schedule = Schedule().withMinutes { it % 10 } // Every 10 minutes
    suspend fun runCron(time: LocalDateTime, zone: TimeZone) {
        database.flush()
    }
}
```

Crons can be run automatically by using the `CoroutineCronDaemon`:

```kotlin
fun main() {
    val cronDaemon = CoroutineCronDaemon(
        jobs = DatabaseFlushCron(myDatabase),
    )
    val daemonInitializer = DaemonInitializer(
        daemons = listOf(webServerDaemon, cronDaemon)
    )
    val myInitializers = listOf(daemonInitializer)

    RegolithInitRunner(
        initializers = myInitializers
    ).initialize()
}
```

Resources
---------

The resources module provides abstractions around application resources like
files and strings.

### Maven coordinates

    com.inkapplications.regolith:resources

### String Resources

The `StringResources` interface provides a way to load localized strings
in an application.

Strings can be Identified either by string or integer, and supports parameter
formatting.

Android has a default implementation to its internal string localization
loader via the `AndroidStringResources` class.

### File Resources

The `FileResources` interface provides a way to load files from the
filesystem or the local application resources.

Application packaged resources can be loaded with by name or ID with the
`FileResources.Local` id type. External files can be loaded by path or URI.

Android has a default implementation via the `AndroidFileResources` class.

JVM has a default implementation via the `JvmFileResources` class.

### Testing

Testing implementations are provided in the `doubles` package, including:
`DummyResources`, `StubResources`, and `ParrotResources`.
