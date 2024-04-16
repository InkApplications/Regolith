package regolith.data.settings

/**
 * Visibility of the setting.
 *
 * This value can be used to control which settings are available in
 * a settings panel.
 */
enum class SettingLevel {
    /**
     * Setting is visible to any user in the system.
     */
    User,

    /**
     * Setting is visible to users, but in an 'advanced' section or flagged
     * appropriately.
     */
    Advanced,

    /**
     * Setting should only be hidden from normal users, but visible for
     * development and debugging.
     */
    Dev,

    /**
     * Setting should be hidden entirely from the user interface and only
     * controlled programmatically.
     */
    Hidden;

    companion object {
        val DEFAULT = User
    }
}
