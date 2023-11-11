package regolith.init

/**
 * Runs initializers for the application
 */
interface InitRunner {
    /**
     * Invoked on application start to kick off all initializers.
     */
    fun start()
}
