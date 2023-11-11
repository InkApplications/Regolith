package regolith.init.log

/**
 * Logger used by to log initialization events.
 */
interface InitRunnerLogger {
    /**
     * Log informational messages
     */
    fun log(message: String)

    /**
     * Log errors in the initialization process.
     */
    fun error(message: String, error: Throwable)
}
