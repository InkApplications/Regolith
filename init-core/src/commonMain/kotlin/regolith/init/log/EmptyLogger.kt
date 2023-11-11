package regolith.init.log

/**
 * Dummy implementation of the init logger that does nothing.
 */
object EmptyLogger: InitRunnerLogger {
    override fun log(message: String) = Unit
    override fun error(message: String, error: Throwable) = Unit
}
