package regolith.sensors.location

/**
 * Possible errors that can occur when requesting location.
 *
 * These errors are not exhaustive, and may be extended in the future.
 */
abstract class LocationError private constructor() {
    /**
     * Indicates that this location provider is unable to provide location
     * on the current hardware.
     */
    object NotSupported: LocationError()

    /**
     * Indicates that the device's location services are disabled.
     */
    object Disabled: LocationError()

    /**
     * Indicates that the current application does not have location
     * permissions currently.
     */
    object PermissionDenied: LocationError()

    /**
     * Unknown error preventing location updates.
     */
    data class Unknown(val cause: Throwable): LocationError()
}
