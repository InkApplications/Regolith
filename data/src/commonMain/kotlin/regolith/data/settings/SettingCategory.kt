package regolith.data.settings

/**
 * A named group of settings.
 *
 * The [key] and [name] can be the same, provided that the name is
 * unique per category. This can be useful if the name will be generated
 * from localization tools.
 */
data class SettingCategory(
    val key: String,
    val name: String,
) {
    constructor(key: String): this(key, key)
}
