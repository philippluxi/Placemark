package com.example.placemark.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.example.placemark.models.PlacemarkModel
import com.example.placemark.views.*

class PlacemarkMapPresenter(view: BaseView) : BasePresenter(view) {

    fun doPopulateMap(map: GoogleMap, placemarks: List<PlacemarkModel>) {
        map.uiSettings.isZoomControlsEnabled = true
        placemarks.forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val tag = marker.tag as Long
        val placemark = app.placemarks.findById(tag)
        if (placemark != null) view?.showPlacemark(placemark)

    }

    fun loadPlacemarks() {
        view?.showPlacemarks(app.placemarks.findAll())
    }
}