package regolith.processes.daemon

/**
 * A service that runs forever.
 */
interface Daemon {
    suspend fun start(): Nothing
}
