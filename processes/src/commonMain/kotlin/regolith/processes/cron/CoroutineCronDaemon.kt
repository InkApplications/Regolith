package regolith.processes.cron

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import regolith.processes.daemon.Daemon
import regolith.timemachine.InexactDurationMachine
import regolith.timemachine.minuteTicks
import kotlin.time.Duration.Companion.seconds

/**
 * Service that runs cron jobs using Coroutines and delay timers.
 *
 * Jobs run by this daemon are expected to have minute-level accuracy, and
 * should run within 20 seconds of their scheduled time.
 */
class CoroutineCronDaemon(
    private val jobs: List<CronJob>,
    clock: Clock = Clock.System,
    private val zone: TimeZone = TimeZone.currentSystemDefault(),
): Daemon {
    private val timeMachine = InexactDurationMachine(
        duration = 20.seconds,
        clock = clock,
        zone = zone,
    )

    override suspend fun startDaemon(): Nothing {
        if (jobs.isEmpty()) {
            suspendCancellableCoroutine<Nothing>{}
        }
        coroutineScope {
            jobs.map { cron ->
                async {
                    timeMachine.minuteTicks
                        .filter { it.minute in cron.schedule.minutes }
                        .filter { it.hour in cron.schedule.hours }
                        .filter { it.dayOfMonth in cron.schedule.days }
                        .filter { it.monthNumber in cron.schedule.months }
                        .collect { time ->
                            cron.runCron(time, zone)
                        }
                }
            }.awaitAll()
            throw IllegalStateException("Time machine completed producing ticks")
        }
    }
}
