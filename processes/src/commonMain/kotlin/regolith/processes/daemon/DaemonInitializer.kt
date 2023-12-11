package regolith.processes.daemon

import kotlinx.coroutines.*
import regolith.init.Initializer
import regolith.init.TargetManager

/**
 * Run daemons at app launch.
 */
class DaemonInitializer(
    private val daemons: List<Daemon>,
    private val callbacks: DaemonCallbacks = DaemonCallbacks.PrintCallbacks,
    private val daemonScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
): Initializer {
    override suspend fun initialize(targetManager: TargetManager) {
        val daemonJob = SupervisorJob()
        daemons.forEach { daemon ->
            daemonScope.launch(daemonJob) {
                kotlin.runCatching { runDaemon(daemon) }
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
        repeat(Int.MAX_VALUE) { attempt ->
            runCatching { daemon.startDaemon() }
                .onFailure { error ->
                    callbacks.onDaemonError(daemon, error)
                    when (daemon.onFailure(failure = error, attempt = attempt)) {
                        is FailureSignal.Die -> return
                        is FailureSignal.Panic -> throw error
                        is FailureSignal.Restart -> {}
                    }
                }
            callbacks.onDaemonRestart(daemon, attempt + 1)
        }
    }
}
