package regolith.data.settings.structure

import regolith.data.settings.Described
import regolith.data.settings.Keyed

/**
 * Metadata used in all settings types.
 *
 * @param STORED The primitive type used when writing this data to storage.
 */
sealed interface Setting<STORED>: Keyed, Described
