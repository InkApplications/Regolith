package regolith.processes.daemon

/**
 * Actions that can be taken after a daemon throws an error.
 */
interface FailureSignal {
    /**
     * Immediately attempt to restart the daemon service.
     */
    data object Restart: FailureSignal

    /**
     * Stop running the daemon quietly.
     */
    data object Die: FailureSignal

    /**
     * Throw an exception in the daemon runner, stopping all daemon execution.
     */
    data object Panic: FailureSignal
}
