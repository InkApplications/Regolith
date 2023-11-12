package regolith.init

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import regolith.init.log.EmptyLogger
import regolith.init.log.InitRunnerLogger
import kotlin.reflect.KClass

/**
 * Implementation of the Init runner an target management.
 *
 * This should be the entry point in your application and run [start]
 * *immediately* on startup to kick off initializers.
 */
class RegolithInitRunner(
    /**
     * List of initializers to be run on startup.
     */
    private val initializers: List<Initializer>,

    /**
     * Optional logger for monitoring initialization events.
     */
    private val logger: InitRunnerLogger = EmptyLogger,

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

    override suspend fun postTarget(target: InitTarget) {
        logger.log("${target::class.simpleName} reached")
        targets.getAndUpdate {
            it + target
        }
    }

    override fun start() {
        logger.log("Starting ${initializers.size} Initializers")
        initializerScope.launch {
            initializers.map { initializer ->
                async {
                    runCatching { initializer.run(this@RegolithInitRunner) }
                        .onFailure { error -> logger.error("${initializer::class.simpleName} failed", error) }
                        .onSuccess { logger.log("${initializer::class.simpleName} complete") }
                }
            }.awaitAll()
        }
    }
}
