package regolith.timemachine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

/**
 * Time machine that emits a tick roughly each [duration] period.
 */
class InexactDurationMachine(
    duration: Duration,
    clock: Clock = Clock.System,
    zone: TimeZone = TimeZone.currentSystemDefault(),
): TimeMachine {
    override val ticks: Flow<LocalDateTime> = flow {
        while (true) {
            emit(clock.now().toLocalDateTime(zone))
            delay(duration)
        }
    }
}
