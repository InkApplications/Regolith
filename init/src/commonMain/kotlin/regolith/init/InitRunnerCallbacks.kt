package regolith.init

import kotlin.reflect.KClass

/**
 * Events produced by the init runner.
 */
interface InitRunnerCallbacks {
    /**
     * Invoked when an initializer completes running.
     */
    fun onInitializerComplete(initializer: Initializer)

    /**
     * Invoked when an initializer produces an error
     */
    fun onInitializerError(initializer: Initializer, error: Throwable)

    /**
     * Invoked when an initializer is paused awaiting the completion of a target
     */
    fun onInitializerAwaitingTarget(initializer: Initializer, target: KClass<out InitTarget>)

    /**
     * Invoked when a target is reached
     */
    fun onTargetReached(target: InitTarget)

    /**
     * Invoked when all initializers have completed.
     */
    fun onComplete()

    /**
     * Empty implementation of [InitRunnerCallbacks], useful for partial delegation.
     */
    object Empty: InitRunnerCallbacks {
        override fun onInitializerComplete(initializer: Initializer) {}
        override fun onInitializerError(initializer: Initializer, error: Throwable) {}
        override fun onInitializerAwaitingTarget(initializer: Initializer, target: KClass<out InitTarget>) {}
        override fun onTargetReached(target: InitTarget) {}
        override fun onComplete() {}
    }

    /**
     * Prints all initialization events to stdout.
     */
    object PrintCallbacks: InitRunnerCallbacks {
        override fun onInitializerComplete(initializer: Initializer) {
            println("${initializer::class.simpleName}: Complete")
        }

        override fun onInitializerError(initializer: Initializer, error: Throwable) {
            println("${initializer::class.simpleName}: Error")
            error.printStackTrace()
        }

        override fun onInitializerAwaitingTarget(initializer: Initializer, target: KClass<out InitTarget>) {
            println("${initializer::class.simpleName}: Awaiting ${target::class.simpleName}")
        }

        override fun onTargetReached(target: InitTarget) {
            println("${target::class.simpleName}: Reached")
        }

        override fun onComplete() {
            println("All initializers completed")
        }
    }
}
