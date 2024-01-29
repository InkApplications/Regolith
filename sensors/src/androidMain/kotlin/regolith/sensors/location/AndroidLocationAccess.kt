package regolith.sensors.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationListenerCompat
import androidx.core.location.LocationManagerCompat
import inkapplications.spondee.measure.metric.meters
import inkapplications.spondee.spatial.GeoCoordinates
import inkapplications.spondee.spatial.latitude
import inkapplications.spondee.spatial.longitude
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

/**
 * Implements the standard location access for Android devices.
 *
 * Note: Android does not have a proper callback for location permission
 * changes, so the [onPermissionChange] method must be called manually any
 * time the location permissions may have changed.
 */
class AndroidLocationAccess(
    private val context: Context,
    private val locationManager: LocationManager,
    private val config: LocationUpdateConfig,
): LocationAccess {
    private val permissionsState = MutableStateFlow(checkPermissions())

    @Suppress("MissingPermission")
    private val locationFlow = callbackFlow {
        val listener = LocationListenerCompat { location ->
            trySendBlocking(LocationState.Known(
                coordinates = location.toGeoCoordinates(),
                accuracy = location.accuracy.meters,
            ))
        }

        val provider = firstSupportedProvider()

        when {
            !LocationManagerCompat.isLocationEnabled(locationManager) -> send(
                LocationState.Unknown(LocationError.Disabled)
            )
            provider == null -> send(
                LocationState.Unknown(LocationError.NotSupported)
            )
            checkPermissions() -> withContext(Dispatchers.Main) {
                locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)?.run {
                    send(LocationState.Known(
                        coordinates = toGeoCoordinates(),
                        accuracy = accuracy.meters,
                    ))
                }

                LocationManagerCompat.requestLocationUpdates(
                    locationManager,
                    provider.androidKey,
                    config.request,
                    listener,
                    Looper.getMainLooper()
                )
            }
        }

        awaitClose {
            locationManager.removeUpdates(listener)
        }
    }

    override val locationUpdates: Flow<LocationState> = permissionsState
        .flatMapLatest { permissions ->
            if (permissions) locationFlow
            else flowOf(LocationState.Unknown(LocationError.PermissionDenied))
        }

    /**
     * Report a permission change that may affect location access.
     */
    fun onPermissionChange() {
        permissionsState.value = checkPermissions()
    }

    private fun firstSupportedProvider(): LocationUpdateConfig.Provider? {
        return config.providers.firstOrNull {
            LocationManagerCompat.hasProvider(locationManager, it.androidKey)
                && locationManager.isProviderEnabled(it.androidKey)
        }
    }

    private fun checkPermissions(): Boolean {
        val permission = if (config.fineLocations) {
            Manifest.permission.ACCESS_FINE_LOCATION
        } else {
            Manifest.permission.ACCESS_COARSE_LOCATION
        }

        return ActivityCompat.checkSelfPermission(
            context,
            permission,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun Location.toGeoCoordinates() = GeoCoordinates(
        latitude = latitude.latitude,
        longitude = longitude.longitude,
    )
}
