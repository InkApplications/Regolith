package regolith.data.settings

import app.cash.sqldelight.driver.worker.WebWorkerDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.w3c.dom.Worker

/**
 * Main entry-point for creating settings services in JS.
 */
class JsSettingsModule(
    migrationScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val driver = WebWorkerDriver(
        Worker(
            js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
        )
    )

    /**
     * Read/write access to the settings database.
     */
    val settingsAccess = createDatabaseAccess(migrationScope, driver)
}
