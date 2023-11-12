package regolith.init

import kotlin.reflect.KClass

/**
 * Internal class used to wrap an target manager in an initializer scope.
 */
internal class InitializerTargetManager(
    private val initializer: Initializer,
    private val callbacks: InitRunnerCallbacks,
    private val delegate: TargetManager,
): TargetManager by delegate {
    override suspend fun <T : InitTarget> awaitTarget(targetClass: KClass<T>): T {
        callbacks.onInitializerAwaitingTarget(initializer, targetClass)
        return delegate.awaitTarget(targetClass)
    }

    override suspend fun postTarget(target: InitTarget) {
        delegate.postTarget(target)
        callbacks.onTargetReached(target)
    }
}
