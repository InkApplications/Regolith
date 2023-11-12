package regolith.timemachine

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

/**
 * A time-machine that ticks roughly every second.
 */
class SecondFrequency(
    clock: Clock = Clock.System,
    zone: TimeZone = TimeZone.currentSystemDefault(),
): TimeMachine by InexactDurationMachine(1.seconds, clock)

/**
 * A time-machine that ticks roughly every minute.
 */
class MinuteFrequency(
    clock: Clock = Clock.System,
    zone: TimeZone = TimeZone.currentSystemDefault(),
): TimeMachine by InexactDurationMachine(1.minutes, clock)

/**
 * A time-machine that ticks roughly every hour.
 */
class HourFrequency(
    clock: Clock = Clock.System,
    zone: TimeZone = TimeZone.currentSystemDefault(),
): TimeMachine by InexactDurationMachine(1.hours, clock)

/**
 * A time-machine that ticks roughly every day.
 */
class DayFrequency(
    clock: Clock = Clock.System,
    zone: TimeZone = TimeZone.currentSystemDefault(),
): TimeMachine by InexactDurationMachine(1.hours * 24, clock)
