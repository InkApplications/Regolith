package regolith.processes.daemon

/**
 * Exception thrown when the [DaemonInitializer] has reached the maximum runs
 * for a particular daemon.
 */
class MaxRunAttemptsReached: IllegalStateException("Daemon reached maximum run attempts")
