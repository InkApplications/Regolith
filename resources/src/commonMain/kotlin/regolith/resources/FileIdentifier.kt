package regolith.resources

import kotlin.jvm.JvmInline

/**
 * Identifier used to load files.
 */
sealed interface FileIdentifier {
    /**
     * Local file, indicated by a [ResourceIdentifier]
     *
     * This is used for indicating an application resource file, rather
     * than an external file on the operating system.
     */
    @JvmInline
    value class AppFile(val id: ResourceIdentifier): FileIdentifier {
        constructor(id: String): this(ResourceIdentifier.IdString(id))
        constructor(id: Int): this(ResourceIdentifier.IdInt(id))

        override fun toString(): String = id.toString()
    }

    /**
     * External file, indicated by a path.
     */
    @JvmInline
    value class Path(val location: String): FileIdentifier {
        override fun toString(): String = location
    }

    /**
     * External file, indicated by a URI location.
     */
    @JvmInline
    value class Uri(val location: String): FileIdentifier {
        override fun toString(): String = location
    }
}
