package com.example.placemark.views.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.example.placemark.R
import com.example.placemark.helpers.readImageFromPath
import com.example.placemark.main.MainApp
import com.example.placemark.models.PlacemarkModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_placemark_list.toolbar
import kotlinx.android.synthetic.main.activity_placemark_maps.*

class PlacemarkMapView : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    lateinit var presenter: PlacemarkMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placemark_maps)
        setSupportActionBar(toolbar)
        presenter = PlacemarkMapPresenter(this)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            presenter.doPopulateMap(it)
        }
    }

    fun showPlacemark(placemark: PlacemarkModel) {
        currentTitle.text = placemark.title
        currentDescription.text = placemark.description
        currentImage.setImageBitmap(readImageFromPath(this, placemark.image))
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}