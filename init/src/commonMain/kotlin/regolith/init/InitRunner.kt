package regolith.init

import kotlinx.coroutines.Job

/**
 * Runs initializers for the application
 */
interface InitRunner {
    /**
     * Invoked on application start to kick off all initializers.
     */
    fun initialize(): Job
}
