package regolith.processes.daemon

/**
 * Events that occur during daemon initialization/running.
 */
interface DaemonCallbacks {
    /**
     * Invoked after a daemon is started.
     */
    fun onDaemonStarted(daemon: Daemon) {}

    /**
     * Invoked when a daemon throws an exception.
     */
    fun onDaemonError(daemon: Daemon, error: Throwable) {}

    /**
     * Invoked before a daemon is restarted after an error.
     */
    fun onDaemonRestart(daemon: Daemon, attempt: Int) {}

    /**
     * Invoked when a deamon throws an error and is set to [FailureSignal.Panic].
     *
     * This callback indicates that all daemons are about to be canceled.
     */
    fun onPanic(daemon: Daemon, error: Throwable) {}

    /**
     * Empty implementation of [DaemonCallbacks] useful for partial delegation.
     */
    object Empty: DaemonCallbacks

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

        override fun onDaemonRestart(daemon: Daemon, attempt: Int) {
            println("${daemon::class.simpleName} Restarting. Attempt $attempt")
        }

        override fun onPanic(daemon: Daemon, error: Throwable) {
            println("${daemon::class.simpleName} Panic. Stopping all daemons.")
        }
    }
}
