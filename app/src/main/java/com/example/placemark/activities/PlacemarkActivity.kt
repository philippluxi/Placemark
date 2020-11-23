package com.example.placemark.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import com.example.placemark.R
import com.example.placemark.helpers.readImage
import com.example.placemark.helpers.showImagePicker
import com.example.placemark.helpers.readImageFromPath
import com.example.placemark.main.MainApp
import com.example.placemark.models.Location
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import com.example.placemark.models.PlacemarkModel
import org.jetbrains.anko.intentFor


class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

  var placemark = PlacemarkModel()
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)

    app = application as MainApp

    var edit = false

    if (intent.hasExtra("placemark_edit")) {
      edit = true
      placemark = intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!
      placemarkTitle.setText(placemark.title)
      description.setText(placemark.description)
      btnAdd.setText(R.string.button_savePlacemark)
      placemarkImage.setImageBitmap(readImageFromPath(this, placemark.image))
      if (placemark.image != null) {
        chooseImage.setText(R.string.change_placemark_image)
      }
    }

    btnAdd.setOnClickListener {
      placemark.title = placemarkTitle.text.toString()
      placemark.description = description.text.toString()
      if (placemark.title.isEmpty()) {
        toast(R.string.enter_placemark_title)
      } else {
        if (edit) {
          app.placemarks.update(placemark.copy())
        } else {
          app.placemarks.create(placemark.copy())
        }
        info("add Button Pressed: ${placemark}")
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }
    }

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }

    placemarkLocation.setOnClickListener {
      val location = Location(48.983329, 12.105644, 15f)
      if (placemark.zoom != 0f) {
        location.lat = placemark.lat
        location.lng = placemark.lng
        location.zoom = placemark.zoom
      }
      startActivityForResult(
        intentFor<MapActivity>().putExtra("location", location),
        LOCATION_REQUEST
      )
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_placemark, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          placemark.image = data.data.toString()
          placemarkImage.setImageBitmap(readImage(this, resultCode, data))
          chooseImage.setText(R.string.change_placemark_image)
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras?.getParcelable<Location>("location")!!
          placemark.lat = location.lat
          placemark.lng = location.lng
          placemark.zoom = location.zoom
        }
      }
    }
  }
}