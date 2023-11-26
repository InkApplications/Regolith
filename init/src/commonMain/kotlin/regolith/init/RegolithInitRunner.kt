package regolith.init

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.reflect.KClass

/**
 * Implementation of the Init runner an target management.
 *
 * This should be the entry point in your application and run [initialize]
 * *immediately* on startup to kick off initializers.
 */
class RegolithInitRunner(
    /**
     * List of initializers to be run on startup.
     */
    private val initializers: List<Initializer>,

    /**
     * Callbacks that can be used to extend or change the functionality
     * of the initialization process.
     */
    private val callbacks: InitRunnerCallbacks = InitRunnerCallbacks.PrintCallbacks,

    /**
     * Scope to run initialization tasks in.
     */
    private val initializerScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
): InitRunner, TargetManager {
    private val targets: MutableStateFlow<List<InitTarget>> = MutableStateFlow(emptyList())

    override suspend fun <T : InitTarget> awaitTarget(targetClass: KClass<T>): T {
        return targets
            .map {
                it.filter { it::class == targetClass }
                    .map { it as? T }
                    .firstOrNull()
            }
            .filterNotNull()
            .first()
    }

    override fun postTarget(target: InitTarget) {
        targets.getAndUpdate {
            it + target
        }
    }

    override fun initialize(): Job {
        return initializerScope.launch {
            initializers.map { initializer ->
                async {
                    runCatching {
                        initializer.initialize(InitializerTargetManager(initializer, callbacks, this@RegolithInitRunner))
                    }.onFailure { error ->
                        callbacks.onInitializerError(initializer, error)
                    }.onSuccess {
                        callbacks.onInitializerComplete(initializer)
                    }
                }
            }.awaitAll()
            callbacks.onComplete()
        }
    }
}
