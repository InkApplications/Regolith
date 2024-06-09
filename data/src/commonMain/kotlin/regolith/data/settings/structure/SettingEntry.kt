package regolith.data.settings.structure

/**
 * Pairs a setting with its current value.
 */
interface SettingEntry<T, out S: Setting<T>> {
    val setting: S
    val value: T
}
