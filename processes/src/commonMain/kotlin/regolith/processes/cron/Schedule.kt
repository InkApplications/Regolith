package regolith.processes.cron

/**
 * Collection of instants that can be filtered down to form a schedule.
 *
 * Examples:
 *  - Every Hour: `Schedule(minutes = 0)`
 *  - Every Day at Midnight: `Schedule(minutes = 0, hours = 0)`
 *  - Every 10 minutes: `Schedule().withMinutes { it % 10 == 0 }`
 */
data class Schedule(
    val minutes: Set<Int> = (0..59).toSet(),
    val hours: Set<Int> = (0..23).toSet(),
    val days: Set<Int> = (1..31).toSet(),
    val months: Set<Int> = (1..12).toSet(),
) {
    inline fun withMinutes(matching: (Int) -> Boolean) = copy(
        minutes = (0..59).filter(matching).toSet()
    )

    inline fun withHours(matching: (Int) -> Boolean) = copy(
        hours = (0..23).filter(matching).toSet()
    )

    inline fun withDays(matching: (Int) -> Boolean) = copy(
        days = (1..31).filter(matching).toSet()
    )

    inline fun withMonths(matching: (Int) -> Boolean) = copy(
        months = (1..12).filter(matching).toSet()
    )
}
