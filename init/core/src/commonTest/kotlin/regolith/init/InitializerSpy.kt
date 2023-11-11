package regolith.init

class InitializerSpy: Initializer {
    var runCount = 0
    override suspend fun run(targetManager: TargetManager) {
        runCount++
    }
}
