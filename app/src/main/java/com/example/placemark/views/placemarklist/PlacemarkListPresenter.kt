package com.example.placemark.views.placemarklist

import com.example.placemark.views.map.PlacemarkMapView
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import com.example.placemark.main.MainApp
import com.example.placemark.models.PlacemarkModel
import com.example.placemark.views.*
import com.example.placemark.views.placemark.PlacemarkView

class PlacemarkListPresenter(view: BaseView) : BasePresenter(view) {

    fun doAddPlacemark() {
        view?.navigateTo(VIEW.PLACEMARK)
    }

    fun doEditPlacemark(placemark: PlacemarkModel) {
        view?.navigateTo(VIEW.PLACEMARK, 0, "placemark_edit", placemark)
    }

    fun doShowPlacemarksMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun loadPlacemarks() {
        view?.showPlacemarks(app.placemarks.findAll())
    }
}