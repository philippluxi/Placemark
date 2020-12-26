package com.example.placemark.views.placemark

import android.content.Intent
import org.jetbrains.anko.intentFor
import com.example.placemark.helpers.showImagePicker
import com.example.placemark.main.MainApp
import com.example.placemark.models.Location
import com.example.placemark.models.PlacemarkModel
import com.example.placemark.views.BasePresenter
import com.example.placemark.views.BaseView
import com.example.placemark.views.*
import com.example.placemark.views.location.EditLocationView


class PlacemarkPresenter(view: BaseView) : BasePresenter(view) {

    var placemark = PlacemarkModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var edit = false

    init {
        if (view.intent.hasExtra("placemark_edit")) {
            edit = true
            placemark = view.intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!
            view.showPlacemark(placemark)
        }
    }

    fun doAddOrSave(title: String, description: String) {
        placemark.title = title
        placemark.description = description
        if (edit) {
            app.placemarks.update(placemark)
        } else {
            app.placemarks.create(placemark)
        }
        view?.finish()
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        app.placemarks.delete(placemark)
        view?.finish()
    }

    fun doSelectImage() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }

    }

    fun doSetLocation() {
        if (edit == false) {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", defaultLocation)
        } else {
            view?.navigateTo(
                VIEW.LOCATION,
                LOCATION_REQUEST,
                "location",
                Location(placemark.lat, placemark.lng, placemark.zoom)
            )
        }
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                placemark.image = data.data.toString()
                view?.showPlacemark(placemark)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                placemark.lat = location.lat
                placemark.lng = location.lng
                placemark.zoom = location.zoom
            }
        }
    }
}