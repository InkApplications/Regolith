Change Log
==========

1.1.0
-----

### Added

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
