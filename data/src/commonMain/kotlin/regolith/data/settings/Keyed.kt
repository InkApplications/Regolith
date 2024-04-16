package regolith.data.settings

/**
 * Object that identifies the key in a key/value pair.
 */
interface Keyed {
    /**
     * A unique key that identifies the setting.
     *
     * This key should never change in order to preserve data.
     */
    val key: String
}
