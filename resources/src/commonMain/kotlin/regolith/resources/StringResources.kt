package regolith.resources

/**
 * Service to look up localized strings in an application.
 */
interface StringResources {
    /**
     * Get a localized string by its ID.
     *
     * @param id The ID of the string to look up.
     * @return The localized string associated with the ID.
     */
    fun getString(id: ResourceIdentifier): String

    /**
     * Get a localized string by ID with parameter substitution.
     *
     * @param id The ID of the string to look up.
     * @param params The parameters to substitute into the string.
     * @return The localized string with parameters substituted.
     */
    fun getString(id: ResourceIdentifier, vararg params: Any): String
}

/**
 * Convenience method for looking up a string by a string ID.
 *
 * @see StringResources.getString
 */
fun StringResources.getString(id: String): String {
    return ResourceIdentifier.IdString(id).let(::getString)
}

/**
 * Convenience method for looking up a string by an integer ID.
 *
 * @see StringResources.getString
 */
fun StringResources.getString(id: Int): String {
    return ResourceIdentifier.IdInt(id).let(::getString)
}

/**
 * Convenience method for looking up a string by a string ID with parameter substitution.
 *
 * @see StringResources.getString
 */
fun StringResources.getString(id: String, vararg params: Any?): String {
    return ResourceIdentifier.IdString(id).let(::getString)
}

/**
 * Convenience method for looking up a string by an integer ID with parameter substitution.
 *
 * @see StringResources.getString
 */
fun StringResources.getString(id: Int, vararg params: Any?): String {
    return ResourceIdentifier.IdInt(id).let(::getString)
}
