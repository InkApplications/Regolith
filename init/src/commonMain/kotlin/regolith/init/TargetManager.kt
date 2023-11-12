package regolith.init

import kotlin.reflect.KClass

/**
 * Provides access to the list of completed targets.
 */
interface TargetManager {
    /**
     * Await a target of a specific type to be completed before continuing.
     *
     * @param tag A unique identifier for the service awaiting the target.
     * @return The target data, once completed.
     */
    suspend fun <T: InitTarget> awaitTarget(targetClass: KClass<T>): T

    /**
     * Inform the runner that a target has been reached.
     */
    suspend fun postTarget(target: InitTarget)
}
