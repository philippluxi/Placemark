package com.example.placemark.views.placemarklist

import com.example.placemark.views.map.PlacemarkMapView
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import com.example.placemark.main.MainApp
import com.example.placemark.models.PlacemarkModel
import com.example.placemark.views.placemark.PlacemarkView

class PlacemarkListPresenter(val view: PlacemarkListView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun getPlacemarks() = app.placemarks.findAll()

    fun doAddPlacemark() {
        view.startActivityForResult<PlacemarkView>(0)
    }

    fun doEditPlacemark(placemark: PlacemarkModel) {
        view.startActivityForResult(
            view.intentFor<PlacemarkView>().putExtra("placemark_edit", placemark), 0
        )
    }

    fun doShowPlacemarksMap() {
        view.startActivity<PlacemarkMapView>()
    }
}