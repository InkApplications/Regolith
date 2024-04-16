package regolith.data.settings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import regolith.data.settings.structure.DataSetting
import regolith.data.settings.structure.PrimitiveSetting

/**
 * Provides read/write access to settings.
 */
interface SettingsAccess {
    /**
     * Observe changes to a setting.
     */
    fun <STORED> observeSetting(setting: PrimitiveSetting<STORED>): Flow<STORED>

    /**
     * Get the current value of a setting.
     */
    suspend fun <STORED> getSetting(setting: PrimitiveSetting<STORED>): STORED

    /**
     * Write a new value to a setting.
     */
    suspend fun <STORED> writeSetting(setting: PrimitiveSetting<STORED>, value: STORED)
}

/**
 * Get the current value of a data setting.
 */
suspend fun <STORED, DATA> SettingsAccess.getSetting(setting: DataSetting<STORED, DATA>): DATA {
    return getSetting(setting.toPrimitive()).let { setting.dataTransformer.transform(it) }
}

/**
 * Write a new value to a data setting.
 */
suspend fun <STORED, DATA> SettingsAccess.writeSetting(setting: DataSetting<STORED, DATA>, value: DATA) {
    writeSetting(setting.toPrimitive(), setting.dataTransformer.reverseTransform(value))
}

/**
 * Observe changes to a data setting.
 */
fun <STORED, DATA> SettingsAccess.observeSetting(setting: DataSetting<STORED, DATA>): Flow<DATA> {
    return observeSetting(setting.toPrimitive()).map { setting.dataTransformer.transform(it) }
}
