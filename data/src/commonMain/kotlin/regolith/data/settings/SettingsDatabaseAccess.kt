package regolith.data.settings

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import regolith.data.Settings
import regolith.data.settings.structure.*

internal class SettingsDatabaseAccess(
    private val database: Deferred<Settings>,
): SettingsAccess {
    override fun <STORED> observeSetting(setting: PrimitiveSetting<STORED>): Flow<STORED> {
        val queries = flow { emit(database.await().settingsQueries) }
        return when (setting) {
            is StringSetting -> queries.flatMapLatest { it.getStringValue(setting.key).asFlow() }
                .map {  it.executeAsOneOrNull() }
                .map { it?.stringValue }
            is LongSetting -> queries.flatMapLatest { it.getIntValue(setting.key).asFlow() }
                .map { it.executeAsOneOrNull() }
                .map { it?.intValue }
            is DoubleSetting -> queries.flatMapLatest { it.getRealValue(setting.key).asFlow() }
                .map { it.executeAsOneOrNull() }
                .map { it?.realValue }
            is BlobSetting -> queries.flatMapLatest { it.getBlobValue(setting.key).asFlow() }
                .map { it.executeAsOneOrNull() }
                .map { it?.blobValue }
        }.map { it as STORED }
    }

    override suspend fun <STORED> getSetting(setting: PrimitiveSetting<STORED>): STORED {
        return when (setting) {
            is StringSetting -> database.await().settingsQueries.getStringValue(setting.key)
                .executeAsOneOrNull()
                ?.stringValue
            is LongSetting -> database.await().settingsQueries.getIntValue(setting.key)
                .executeAsOneOrNull()
                ?.intValue
            is DoubleSetting -> database.await().settingsQueries.getRealValue(setting.key)
                .executeAsOneOrNull()
                ?.realValue
            is BlobSetting -> database.await().settingsQueries.getBlobValue(setting.key)
                .executeAsOneOrNull()
                ?.blobValue
        } as STORED
    }

    override suspend fun <STORED> writeSetting(setting: PrimitiveSetting<STORED>, value: STORED) {
        when (setting) {
            is StringSetting -> database.await().settingsQueries.setStringValue(
                key = setting.key,
                stringValue = value as String?,
            )
            is LongSetting -> database.await().settingsQueries.setIntValue(
                key = setting.key,
                intValue = value as Long?,
            )
            is DoubleSetting -> database.await().settingsQueries.setRealValue(
                key = setting.key,
                realValue = value as Double?,
            )
            is BlobSetting -> database.await().settingsQueries.setBlobValue(
                key = setting.key,
                blobValue = value as ByteArray?,
            )
        }
    }
}

internal fun createDatabaseAccess(scope: CoroutineScope, driver: SqlDriver): SettingsAccess {
    val database = scope.async {
        Settings.Schema.create(driver).await()
        Settings(driver)
    }

    return SettingsDatabaseAccess(database)
}
