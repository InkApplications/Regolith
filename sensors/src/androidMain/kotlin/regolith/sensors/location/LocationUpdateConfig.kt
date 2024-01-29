package regolith.sensors.location

import android.location.LocationManager
import androidx.core.location.LocationRequestCompat

/**
 * Configures the location update frequency and specificity.
 *
 * By default, this configuration is as efficient as possible and will
 * only provide course location updates at passive intervals.
 */
data class LocationUpdateConfig(
    /**
     * Android location request configuration.
     */
    val request: LocationRequestCompat = LocationRequestCompat
        .Builder(LocationRequestCompat.PASSIVE_INTERVAL)
        .build(),

    /**
     * Location providers to be used when subscribing to location updates.
     */
    val providers: List<Provider> = listOf(Provider.Passive),

    /**
     * Whether to request highly granular locations.
     */
    val fineLocations: Boolean = false,
) {
    /**
     * Typed wrapper for Android's location providers.
     */
    @JvmInline
    value class Provider(val androidKey: String) {
        companion object {
            val Gps = Provider(LocationManager.GPS_PROVIDER)
            val Network = Provider(LocationManager.NETWORK_PROVIDER)
            val Passive = Provider(LocationManager.PASSIVE_PROVIDER)
        }
    }
}
