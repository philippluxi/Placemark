package com.example.placemark.views.placemarklist

import com.example.placemark.models.PlacemarkModel
import com.example.placemark.views.*

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