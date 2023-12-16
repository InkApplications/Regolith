package regolith.processes.daemon

/**
 * A service that runs forever.
 */
interface Daemon {
    /**
     * Start the daemon.
     *
     * Note: This method cannot return, as deamons should continue to run
     * until they are stopped externally (ie. when the current coroutine
     * context is cancelled).
     */
    suspend fun startDaemon(): Nothing

    /**
     * Determine what to do if the daemon stops unexpectedly.
     *
     * This is invoked after any failure thrown by the [startDaemon] method.
     * The default behavior is [FailureSignal.Die].
     *
     * This method can be used as a hook for logic to run between retry
     * attempts, such as clearing data, or delaying before attempting to
     * restart.
     *
     * @param attempts Information about previous run failures.
     * @return A [FailureSignal] indicating an action to take after the failure.
     */
    suspend fun onFailure(attempts: List<DaemonRunAttempt>): FailureSignal {
        return FailureSignal.Die
    }
}
