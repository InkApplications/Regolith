package regolith.data.settings

/**
 * Fields to allow a setting to be displayed in a UI
 */
interface Described {
    /**
     * A user-readable name for the setting.
     */
    val name: String

    /**
     * Visibility level of the setting. Used to show/hide advanced settings.
     */
    val level: SettingLevel

    /**
     * An optional user-readable category name that can be used for grouping.
     */
    val category: SettingCategory?

    /**
     * An optional user-readable description of the setting.
     */
    val description: String?
}
