package com.example.myapplication.ui.login


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.BridegfyVictim.model.Failure
import com.example.myapplication.BridegfyVictim.model.OneOf
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

/**
 * This Fragment represents the base Location class, any fragment which needs access to the
 * native location feature will extend from this Fragment
 * */
abstract class NativeLocationFragment : Fragment() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    /**
     * [locationLiveData] will expose the current location obtained from the device,
     * [OneOf.Success] represents the location has been successfully obtained
     * [OneOf.Failure] is used if the location cannot be obtained due to lack of permission
     * or required setting is not available/enabled in the device
     * */
    val locationLiveData: MutableLiveData<OneOf<Location, Failure.LocationFailure>> =
        MutableLiveData()

    private val locationRequest = LocationRequest.create().apply {
        interval = 10
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
    }

    /**
     *call this method to update the location value in [locationLiveData].The execution is as follows,
     * [isLocationPermissionGranted] -> [checkRequiredLocationSettings] (if success) -> [fetchLastKnownLocation]
     * (if failure) -> open settings screen to enable location
     */
    fun updateLocation() {

        if (isLocationPermissionGranted()) {
            checkRequiredLocationSettings(
                successListener = {
                    fetchLastKnownLocation()

                },
                failureListener = { exception ->
                    if (exception is ResolvableApiException) {
                        // Location settings are not satisfied
                        try {
                            // Show the dialog by calling startIntentSenderForResult(),
                            // and check the result in onActivityResult().
                            startIntentSenderForResult(
                                exception.resolution.intentSender,
                                REQUEST_CHECK_SETTINGS,
                                null,
                                0,
                                0,
                                0,
                                null
                            )
                        } catch (sendEx: IntentSender.SendIntentException) {
                            //Ignore
                        }
                    }
                }
            )
        } else {
            requestLocationPermission()
        }
    }

    /**
     * check if the location permission is granted or not
     */
    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * request the runtime permission for location, for devices above android 6
     */
    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_CHECK
        )
    }

    /**
     * check if the current devices settings match the required settings needed to obtain the location
     * [successListener] is a lambda with a param [LocationSettingsResponse] will be executed if the current settings
     * match the required settings
     * [failureListener] will be invoked if the current settings and the required settings doesn't match
     */
    private fun checkRequiredLocationSettings(
        successListener: (LocationSettingsResponse) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        val locationSettingRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()

        val settingsClient = LocationServices.getSettingsClient(requireContext())

        settingsClient.checkLocationSettings(locationSettingRequest)
            .addOnSuccessListener(successListener)
            .addOnFailureListener(failureListener)
    }

    /**
     * this method is used to post the last know location using [fusedLocationProviderClient.lastLocation]
     * returns [OneOf.Success] if the location value is not null and [OneOf.Failure] if the operation fails
     * if the [Location] value returned is null, a new locationUpdateRequest is subscribed by calling [requestNewLocationUpdates]
     */
    private fun fetchLastKnownLocation() {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    locationLiveData.postValue(
                        OneOf.Success(
                            location,
                            OneOf.Success.DataState.FRESH_DATA
                        )
                    )
                } else {
                    requestNewLocationUpdates()
                }
            }
            .addOnFailureListener {
                locationLiveData.postValue(
                    OneOf.Failure(
                        Failure.LocationFailure.UnknownLocationFailure
                    )
                )
            }
    }

    /**
     * call this method if the [fusedLocationProviderClient.lastLocation] returns null.
     * this method will setup a new location update callback with single update for location.
     */
    private fun requestNewLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest.apply {
                numUpdates = 1
            },
            object : LocationCallback() {
                override fun onLocationResult(locations: LocationResult?) {
                    super.onLocationResult(locations)
                    locations?.let {
                        locationLiveData.postValue(
                            OneOf.Success(
                                it.lastLocation,
                                OneOf.Success.DataState.FRESH_DATA
                            )
                        )
                    } ?: run {
                        // this block is executed if the location is null
                        locationLiveData.postValue(
                            OneOf.Failure(
                                Failure.LocationFailure.UnknownLocationFailure
                            )
                        )
                    }
                }
            },
            null
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                if (resultCode == Activity.RESULT_OK) {
                    //the users has changed the current location settings to meet the requirements, retry getting location
                    updateLocation()
                } else {
                    //the user failed to update the location settings to meet the requirement
                    locationLiveData.postValue(
                        OneOf.Failure(
                            Failure.LocationFailure.LocationNotEnabledFailure
                        )
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            LOCATION_PERMISSION_CHECK -> {
                if (grantResults.isNotEmpty() and (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //the user has granted the location permission, hence retry to obtain the current location
                    updateLocation()
                } else {
                    //the user has denied the location permission
                    locationLiveData.postValue(
                        OneOf.Failure(
                            Failure.LocationFailure.LocationPermissionDeniedFailure
                        )
                    )
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CHECK_SETTINGS = 100
        private const val LOCATION_PERMISSION_CHECK = 101
    }
}

