package regolith.processes.daemon

import kotlinx.coroutines.*
import kotlinx.datetime.Clock
import regolith.init.Initializer
import regolith.init.TargetManager

/**
 * Run daemons at app launch.
 */
class DaemonInitializer(
    /**
     * Collection of daemons to run.
     */
    private val daemons: List<Daemon>,
    /**
     * Callback interface for daemon events, useful for logging.
     */
    private val callbacks: DaemonCallbacks = DaemonCallbacks.PrintCallbacks,
    /**
     * Scope to run Daemon jobs in.
     */
    private val daemonScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    /**
     * Maximum number of times to attempt to run any daemon.
     *
     * This number will only be used if the Daemon returns [FailureSignal.Restart].
     * in its [Daemon.onFailure] method.
     *
     * Note: This number is *not* zero-indexed.
     */
    private val maxRunAttempts: Int = Int.MAX_VALUE,
    /**
     * Clock to use for measuring daemon runtimes.
     */
    private val clock: Clock = Clock.System,
): Initializer {
    override suspend fun initialize(targetManager: TargetManager) {
        val daemonJob = SupervisorJob()
        daemons.forEach { daemon ->
            daemonScope.launch(daemonJob) {
                runCatching { runDaemon(daemon) }
                    .onFailure { if (it is CancellationException) throw it }
                    .onFailure { error ->
                        callbacks.onPanic(daemon, error)
                        daemonJob.cancel(CancellationException("Daemon Panic, caused by ${daemon::class.simpleName}", error))
                    }
            }
            callbacks.onDaemonStarted(daemon)
        }
        targetManager.postTarget(DaemonTarget)
    }

    private suspend fun runDaemon(daemon: Daemon) {
        (1..maxRunAttempts).fold(emptyList<DaemonRunAttempt>()) { previousAttempts, attempt ->
            clock.now()
                .let { it to getDaemonError(daemon) }
                .let { (start, error) ->
                    DaemonRunAttempt(
                        attempt = attempt,
                        started = start,
                        ended = clock.now(),
                        error = error,
                    )
                }
                .also { callbacks.onDaemonError(daemon, it.error) }
                .let { previousAttempts + it }
                .also { attempts ->
                    when (daemon.onFailure(attempts)) {
                        is FailureSignal.Die -> return
                        is FailureSignal.Panic -> throw attempts.last().error
                        is FailureSignal.Restart -> callbacks.onDaemonRestart(daemon, attempts)
                    }
                }
        }
        throw MaxRunAttemptsReached()
    }

    private suspend fun getDaemonError(daemon: Daemon): Throwable {
        return runCatching { daemon.startDaemon() }
            .onFailure { if (it is CancellationException) throw it }
            .exceptionOrNull()
            ?: IllegalStateException("Daemon ${daemon::class.simpleName} returned normally")
    }
}
