package regolith.sensors.location

import inkapplications.spondee.measure.Length
import inkapplications.spondee.spatial.GeoCoordinates

/**
 * Possible results from a location query.
 */
sealed interface LocationState {
    /**
     * Result when device's location is not known.
     */
    data class Unknown(
        /**
         * The reason the location is unknown.
         */
        val error: LocationError
    ): LocationState

    /**
     * Result when the device's location is known.
     */
    data class Known(
        /**
         * Latitude and longitude of the device.
         */
        val coordinates: GeoCoordinates,

        /**
         * The approximate accuracy of the location [coordinates].
         */
        val accuracy: Length,
    ): LocationState
}
