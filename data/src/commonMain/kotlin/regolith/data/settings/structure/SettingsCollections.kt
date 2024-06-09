package regolith.data.settings.structure

import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * Get a single setting by its key.
 */
fun Collection<Setting<*>>.findByKey(key: String): Setting<*>? {
    return find { it.key == key }
}

/**
 * Group settings by their category.
 *
 * This returns settings grouped into pairs rather than a map so that
 * the pairs can be ordered by name.
 */
fun Collection<Setting<*>>.groupByCategory(): List<Pair<SettingCategory?, List<Setting<*>>>> {
    return groupBy { it.category }
        .map { (category, settings) -> category to settings }
        .sortedBy { (category, _) -> category?.name }
}

/**
 * Group settings by their level.
 *
 * This returns settings grouped into pairs rather than a map so that
 * the pairs can be ordered by level ordinal.
 */
fun Collection<Setting<*>>.groupByLevel(): List<Pair<SettingLevel, List<Setting<*>>>> {
    return groupBy { it.level }
        .map { (level, settings) -> level to settings }
        .sortedBy { (level, _) -> level.ordinal }
}
