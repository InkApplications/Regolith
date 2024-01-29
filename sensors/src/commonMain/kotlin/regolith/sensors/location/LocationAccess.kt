package regolith.sensors.location

import kotlinx.coroutines.flow.Flow

/**
 * Provides access to the current device's location.
 */
interface LocationAccess {
    /**
     * Updates whenever the device's location changes.
     *
     * The frequency and distinctness of these updates is determined by the
     * implementing class.
     */
    val locationUpdates: Flow<LocationState>
}
