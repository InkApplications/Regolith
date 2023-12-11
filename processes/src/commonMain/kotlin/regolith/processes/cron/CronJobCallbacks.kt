package regolith.processes.cron

/**
 * Events that occur while running cron jobs.
 */
interface CronJobCallbacks {
    /**
     * Invoked if a cron run fails.
     */
    suspend fun onCronJobError(job: CronJob, error: Throwable) {}

    /**
     * Empty implementation of [CronJobCallbacks] useful for partial delegation.
     */
    object Empty: CronJobCallbacks

    /**
     * Print cron job callbacks to stdout.
     */
    object PrintCallbacks: CronJobCallbacks {
        override suspend fun onCronJobError(job: CronJob, error: Throwable) {
            println("${job::class.simpleName} Failed to run")
            error.printStackTrace()
        }
    }
}
