package com.example.placemark.views


import android.content.Intent

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.placemark.models.Location
import org.jetbrains.anko.AnkoLogger

import com.example.placemark.models.PlacemarkModel
import com.example.placemark.views.location.EditLocationView
import com.example.placemark.views.login.LoginView
import com.example.placemark.views.map.PlacemarkMapView
import com.example.placemark.views.placemark.PlacemarkView
import com.example.placemark.views.placemarklist.PlacemarkListView
import com.google.firebase.auth.FirebaseAuth

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
    LOCATION, PLACEMARK, MAPS, LIST, LOGIN
}

open abstract class BaseView : AppCompatActivity(), AnkoLogger {

    var basePresenter: BasePresenter? = null

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
        var intent = Intent(this, PlacemarkListView::class.java)
        when (view) {
            VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
            VIEW.PLACEMARK -> intent = Intent(this, PlacemarkView::class.java)
            VIEW.MAPS -> intent = Intent(this, PlacemarkMapView::class.java)
            VIEW.LIST -> intent = Intent(this, PlacemarkListView::class.java)
            VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
        }
        if (key != "") {
            intent.putExtra(key, value)
        }
        startActivityForResult(intent, code)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun init(toolbar: Toolbar, upEnabled: Boolean) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            toolbar.title = "${title}: ${user.email}"
        }
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showPlacemark(placemark: PlacemarkModel) {}
    open fun showPlacemarks(placemarks: List<PlacemarkModel>) {}
    open fun showProgress() {}
    open fun hideProgress() {}
    open fun showLocation(location: Location) {}
}