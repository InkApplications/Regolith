package regolith.data.settings

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.File

/**
 * Main entry-point for creating settings services on the JVM.
 */
class JvmSettingsModule(
    databaseFile: File,
    migrationScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    private val driver = JdbcSqliteDriver("jdbc:sqlite:${databaseFile.absolutePath}")

    /**
     * Read/write access to the settings database.
     */
    val settingsAccess = createDatabaseAccess(migrationScope, driver)
}
