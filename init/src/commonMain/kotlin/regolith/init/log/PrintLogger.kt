package regolith.init.log

/**
 * Basic implementation of the init logger that sends message to sdout.
 */
object PrintLogger: InitRunnerLogger {
    override fun log(message: String) {
        println(message)
    }

    override fun error(message: String, error: Throwable) {
        println(message)
        error.printStackTrace()
    }
}
