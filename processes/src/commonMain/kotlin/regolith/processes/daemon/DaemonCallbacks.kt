package regolith.processes.daemon

/**
 * Events that occur during daemon initialization/running.
 */
interface DaemonCallbacks {
    /**
     * Invoked after a daemon is started.
     */
    fun onDaemonStarted(daemon: Daemon)

    /**
     * Invoked when a daemon throws an exception.
     */
    fun onDaemonError(daemon: Daemon, error: Throwable)

    /**
     * Empty implementation of [DaemonCallbacks] useful for partial delegation.
     */
    object Empty: DaemonCallbacks {
        override fun onDaemonStarted(daemon: Daemon) {}
        override fun onDaemonError(daemon: Daemon, error: Throwable) {}
    }

    /**
     * Print daemon callbacks to stdout.
     */
    object PrintCallbacks: DaemonCallbacks {
        override fun onDaemonStarted(daemon: Daemon) {
            println("${daemon::class.simpleName} Started")
        }

        override fun onDaemonError(daemon: Daemon, error: Throwable) {
            println("${daemon::class.simpleName} Failed")
            error.printStackTrace()
        }
    }
}
