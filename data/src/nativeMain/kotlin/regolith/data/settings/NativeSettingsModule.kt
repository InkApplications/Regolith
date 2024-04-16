package regolith.data.settings

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import regolith.data.Settings

/**
 * Main entry-point for creating settings services on native platforms.
 */
class NativeSettingsModule(
    databaseName: String = "settings.db",
    migrationScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    private val driver = NativeSqliteDriver(Settings.Schema.synchronous(), databaseName)

    /**
     * Read/write access to the settings database.
     */
    val settingsAccess = createDatabaseAccess(migrationScope, driver)
}
