package regolith.processes.cron

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone

/**
 * A job that is executed periodically on a defined schedule.
 */
interface CronJob {
    /**
     * Time(s) to run the job.
     */
    val schedule: Schedule

    /**
     * Job to be executed when [schedule] matches.
     */
    suspend fun runCron(time: LocalDateTime, zone: TimeZone)
}
