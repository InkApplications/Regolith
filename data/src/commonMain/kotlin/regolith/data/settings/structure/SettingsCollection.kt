package regolith.data.settings.structure

import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * Wraps a collection to provide additional functionality and validation.
 */
class SettingsCollection(
    private val settings: Collection<Setting<*>>
): Collection<Setting<*>> by settings {
    init {
        settings.forEach { setting ->
            require(settings.none { it.key == setting.key }) {
                "Duplicate setting key: ${setting.key}"
            }
        }
    }

    /**
     * Get a single setting by its key.
     */
    fun findByKey(key: String): Setting<*>? {
        return settings.find { it.key == key }
    }

    /**
     * Group settings by their category.
     *
     * This returns settings grouped into pairs rather than a map so that
     * the pairs can be ordered by name.
     */
    fun groupByCategory(): List<Pair<SettingCategory?, List<Setting<*>>>> {
        return settings.groupBy { it.category }
            .map { (category, settings) -> category to settings }
            .sortedBy { (category, _) -> category?.name }
    }

    /**
     * Group settings by their level.
     *
     * This returns settings grouped into pairs rather than a map so that
     * the pairs can be ordered by level ordinal.
     */
    fun groupByLevel(): List<Pair<SettingLevel, List<Setting<*>>>> {
        return settings.groupBy { it.level }
            .map { (level, settings) -> level to settings }
            .sortedBy { (level, _) -> level.ordinal }
    }

    override fun toString(): String {
        return settings.map { it.name }
            .joinToString()
            .let { "SettingsCollection($it)" }
    }
}
