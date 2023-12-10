package regolith.resources

import kotlinx.coroutines.flow.Flow

/**
 * Interface for accessing file resources on the system.
 */
interface FileResources {
    /**
     * Loads the entire contents of a file as a string.
     *
     * This method should be used with caution, as the entire file is loaded
     * into memory in one operation and could cause memory issues.
     *
     * @param id Used to load the file.
     * @return The full contents of the file as a string.
     */
    suspend fun getFileContents(id: FileIdentifier): String

    /**
     * Loads the contents of a file as a sequence of lines.
     *
     * @param id Used to load the file.
     * @return A Flow that emits each line of the file.
     */
    fun getFileLines(id: FileIdentifier): Flow<String>
}
