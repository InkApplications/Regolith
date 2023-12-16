package regolith.processes.daemon

import kotlinx.datetime.Instant
import kotlin.time.Duration

/**
 * Information about a failed attempt to run a [Daemon].
 */
data class DaemonRunAttempt(
    /**
     * The attempt number of this run.
     *
     * This number is *not* zero-indexed.
     */
    val attempt: Int,

    /**
     * Timestamp the job started running.
     */
    val started: Instant,

    /**
     * Timestamp the job stopped running.
     */
    val ended: Instant,

    /**
     * The error thrown when the daemon exited.
     */
    val error: Throwable
) {
    /**
     * Amount of time the daemon was running.
     */
    val runtime: Duration = ended - started
}
