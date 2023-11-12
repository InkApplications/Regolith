package regolith.processes.daemon

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        daemons.forEach { daemon ->
            daemonScope.launch {
                runCatching { daemon.startDaemon() }
                    .onFailure { callbacks.onDaemonError(daemon, it) }
            }
            callbacks.onDaemonStarted(daemon)
        }
        targetManager.postTarget(DaemonTarget)
    }
}
