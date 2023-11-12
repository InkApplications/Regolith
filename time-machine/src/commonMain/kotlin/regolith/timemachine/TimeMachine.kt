package regolith.timemachine

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.datetime.LocalDateTime

/**
 * A time-repeating event system.
 */
interface TimeMachine {
    /**
     * Flow that emits every time a recurring time unit has passed.
     *
     * Note: Because this flow will be consumed as a suspending operation,
     * this provided *frequency* but not *accuracy* – that is, the results
     * can be expected at roughly incremental times, but are not guaranteed
     * to be resumed at their exact clock mark.
     */
    val ticks: Flow<LocalDateTime>
}

/**
 * Filter ticks to second-level frequency.
 */
val TimeMachine.secondTicks get() = ticks.distinctUntilChangedBy { it.second }

/**
 * Filter ticks to minute-level frequency.
 */
val TimeMachine.minuteTicks get() = ticks.distinctUntilChangedBy { it.minute }

/**
 * Filter ticks to hour-level frequency.
 */
val TimeMachine.hourTicks get() = ticks.distinctUntilChangedBy { it.hour }

/**
 * Filter ticks to day-level frequency.
 */
val TimeMachine.dayTicks get() = ticks.distinctUntilChangedBy { it.dayOfMonth }

/**
 * Filter ticks to month-level frequency.
 */
val TimeMachine.monthTicks get() = ticks.distinctUntilChangedBy { it.month }

/**
 * Filter ticks to year-level frequency.
 */
val TimeMachine.yearTicks get() = ticks.distinctUntilChangedBy { it.year }

