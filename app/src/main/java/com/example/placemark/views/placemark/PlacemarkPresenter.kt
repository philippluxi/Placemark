package com.example.placemark.views.placemark

import android.annotation.SuppressLint
import android.content.Intent
import com.example.placemark.helpers.checkLocationPermissions
import com.example.placemark.helpers.createDefaultLocationRequest
import com.example.placemark.helpers.isPermissionGranted
import com.example.placemark.helpers.showImagePicker
import com.example.placemark.models.Location
import com.example.placemark.models.PlacemarkModel
import com.example.placemark.views.BasePresenter
import com.example.placemark.views.BaseView
import com.example.placemark.views.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class PlacemarkPresenter(view: BaseView) : BasePresenter(view) {

    var placemark = PlacemarkModel()
    var defaultLocation = Location(48.983307948993094, 12.105706251194382, 15f)
    var edit = false
    var map: GoogleMap? = null
    var locationService: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(view)
    val locationRequest = createDefaultLocationRequest()
    var locationManualyChanged = false

    init {
        if (view.intent.hasExtra("placemark_edit")) {
            edit = true
            placemark = view.intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!
            view.showPlacemark(placemark)
        } else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
        }
    }

    fun doAddOrSave(title: String, description: String) {
        placemark.title = title
        placemark.description = description
        doAsync {
            if (edit) {
                app.placemarks.update(placemark)
            } else {
                app.placemarks.create(placemark)
            }
            uiThread {
                view?.finish()
            }
        }
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        doAsync {
            app.placemarks.delete(placemark)
            uiThread {
                view?.finish()
            }
        }
    }

    fun doSelectImage() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }

    }

    fun cachePlacemark(title: String, description: String) {
        placemark.title = title
        placemark.description = description
    }

    fun doSetLocation() {
        locationManualyChanged = true
        view?.navigateTo(
            VIEW.LOCATION,
            LOCATION_REQUEST,
            "location",
            Location(placemark.location.lat, placemark.location.lng, placemark.location.zoom)
        )
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                placemark.image = data.data.toString()
                view?.showPlacemark(placemark)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                placemark.location = location
                locationUpdate(location)
            }
        }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(placemark.location)
    }

    fun locationUpdate(location: Location) {
        placemark.location = location
        map?.clear()
        map?.uiSettings?.isZoomControlsEnabled = true
        val options =
            MarkerOptions().title(placemark.title)
                .position(LatLng(placemark.location.lat, placemark.location.lng))
        map?.addMarker(options)
        map?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(placemark.location.lat, placemark.location.lng),
                placemark.location.zoom
            )
        )
        view?.showLocation(placemark.location)
    }

    override fun doRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            // permissions denied, so use the default location
            locationUpdate(defaultLocation)
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(Location(it.latitude, it.longitude))
        }
    }

    @SuppressLint("MissingPermission")
    fun doResartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    if (!locationManualyChanged) {
                        locationUpdate(Location(l.latitude, l.longitude))
                    }
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }
}