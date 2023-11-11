package regolith.init

/**
 * Task run when the application starts.
 *
 * An initializer is started *immediately* by the task runner on startup.
 * To delay initialization or depend on the output of another, await
 * a target from the target manager
 */
interface Initializer {
    suspend fun run(targetManager: TargetManager)
}
