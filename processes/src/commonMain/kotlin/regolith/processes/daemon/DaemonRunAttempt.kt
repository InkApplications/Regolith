package regolith.processes.daemon

import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

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

/**
 * The average number of failures for each minute in this collection of run attempts.
 */
val List<DaemonRunAttempt>.failuresPerMinute: Float get() {
    val started = firstOrNull()?.started ?: return 0f
    val ended = lastOrNull()?.ended ?: return 0f
    return size.toFloat() / ended.minus(started).inWholeMinutes
}

/**
 * The average run time for this collection of run attempts.
 */
val List<DaemonRunAttempt>.averageRuntime: Duration get() {
    return map { it.runtime.inWholeMilliseconds }.average().milliseconds
}
