Change Log
==========

1.2.0
-----

### Added

 - New Sensors module for abstracting hardware access layers to sensor data.
 - Added Location Interface to sensors module with a simple Android
   implementation.

### Fixed

 - Fixed a publishing error with Android modules

1.1.1
-----

### Changed

 - `DaemonFailureHandler` broken out into its own interface.
    No changes to functionality for daemon interface.


1.1.0
-----

### Changed
 - All `DaemonCallbacks` methods are now optional.
 - `CoroutineCronDaemon` now handles failed crons without throwing exceptions.

### Added

 - `CoroutineCronDaemon` now provides an optional `CronJobCallbacks` interface
   for handling failure logging.
 - `Daemon` interface now includes an `onFailure` method to define
   failure handling and allow for retries.
 - `DaemonCallbacks` now includes an `onDaemonRestart` method invoked if
   the daemon is restarted.
 - `DaemonCallbacks` now includes a `onPanic` method invoked if a daemon
   fails and is set to shut down the runner (panic) on failure.
 - `resources` module with string resource management interfaces and file
    reading interfaces.
 - Android implementation of `StringResources` and `FileResources` interfaces
 - JVM implementation of `FileResources` interface

1.0.1
-----

### Changed

- Removed unecessary `suspend` modifier in the `TargetManager.postTarget`
  method signature.

1.0.0
-----

### Added

 - `init` module for startup services in an application
 - `processes` module for background services in an application such as
   daemons and crons.
