package regolith.resources

import kotlin.jvm.JvmInline

/**
 * ID types used to look up strings from resources.
 */
sealed interface ResourceIdentifier {
    /**
     * Resources that are keyed using a string ID.
     */
    @JvmInline
    value class IdString(val key: String): ResourceIdentifier {
        override fun toString(): String = key
    }

    /**
     * Resources that are keyed using an integer ID.
     */
    @JvmInline
    value class IdInt(val key: Int): ResourceIdentifier {
        override fun toString(): String = key.toString()
    }
}
