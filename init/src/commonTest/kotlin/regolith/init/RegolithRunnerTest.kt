package regolith.init

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RegolithRunnerTest {
    @Test
    fun runInitializers() = runTest {
        val initializerSpy = InitializerSpy()
        val runner = RegolithInitRunner(
            initializers = listOf(initializerSpy),
            initializerScope = this,
        )

        runner.start()
        runCurrent()

        assertEquals(1, initializerSpy.runCount, "Initializer should be run once")
    }

    @Test
    fun suspendOnTarget() = runTest {
        val suspendingInitializer = object: Initializer {
            var runCount = 0
            override suspend fun run(targetManager: TargetManager) {
                targetManager.awaitTarget(TestTarget::class)
                runCount++
            }
        }
        val runner = RegolithInitRunner(
            initializers = listOf(suspendingInitializer),
            initializerScope = this,
        )
        runner.start()
        runCurrent()

        assertEquals(0, suspendingInitializer.runCount, "Initializer should be suspended until target is reached")
        runner.postTarget(TestTarget)
        runCurrent()
        assertEquals(1, suspendingInitializer.runCount, "Initializer resume after target is reached")
    }
}
