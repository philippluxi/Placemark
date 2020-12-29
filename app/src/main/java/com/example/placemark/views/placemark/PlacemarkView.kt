package com.example.placemark.views.placemark

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import com.example.placemark.R
import com.example.placemark.helpers.readImageFromPath
import com.example.placemark.models.Location
import com.example.placemark.models.PlacemarkModel
import com.example.placemark.views.BaseView
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_placemark.description
import kotlinx.android.synthetic.main.activity_placemark.placemarkTitle
import kotlinx.android.synthetic.main.card_placemark.*

class PlacemarkView : BaseView(), AnkoLogger {

    lateinit var presenter: PlacemarkPresenter
    var placemark = PlacemarkModel()
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placemark)

        init(toolbarAdd, false)

        presenter = initPresenter(PlacemarkPresenter(this)) as PlacemarkPresenter

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            presenter.doConfigureMap(map)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }

        chooseImage.setOnClickListener {
            presenter.cachePlacemark(placemarkTitle.text.toString(), description.text.toString())
            presenter.doSelectImage()
        }
    }

    override fun showPlacemark(placemark: PlacemarkModel) {
        if (placemarkTitle.text.isEmpty()) placemarkTitle.setText(placemark.title)
        if (description.text.isEmpty()) description.setText(placemark.description)
        Glide.with(this).load(placemark.image).into(placemarkImage)

        if (placemark.image != null) {
            chooseImage.setText(R.string.change_placemark_image)
        }
        this.showLocation(placemark.location)
    }

    override fun showLocation(location: Location) {
        lat.text = "%.6f".format(location.lat)
        lng.text = "%.6f".format(location.lng)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        if (presenter.edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_save -> {
                if (placemarkTitle.text.toString().isEmpty()) {
                    toast(R.string.enter_placemark_title)
                } else {
                    presenter.doAddOrSave(
                        placemarkTitle.text.toString(),
                        description.text.toString()
                    )
                }
            }
      R.id.item_cancel -> {
        presenter.doCancel()
      }
    }
    return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        presenter.doCancel()
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
        presenter.doResartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}