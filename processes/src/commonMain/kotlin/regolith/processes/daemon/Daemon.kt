package regolith.processes.daemon

/**
 * A service that runs forever.
 */
interface Daemon {
    suspend fun startDaemon(): Nothing
}
