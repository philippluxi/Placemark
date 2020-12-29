package com.example.placemark.views.placemarklist

import com.example.placemark.models.PlacemarkModel
import com.example.placemark.views.*
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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
        doAsync {
            val placemarks = app.placemarks.findAll()
            uiThread {
                view?.showPlacemarks(placemarks)
            }
        }
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.placemarks.clear()
        view?.navigateTo(VIEW.LOGIN)
    }
}