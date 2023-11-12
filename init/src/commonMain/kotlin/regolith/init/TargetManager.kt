package regolith.init

import kotlin.reflect.KClass

/**
 * Provides access to the list of completed targets.
 */
interface TargetManager {
    /**
     * Await a target of a specific type to be completed before continuing.
     */
    suspend fun <T: Target> awaitTarget(targetClass: KClass<T>): T

    /**
     * Inform the runner that a target has been reached.
     */
    suspend fun postTarget(target: Target)
}
