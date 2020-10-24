package com.example.placemark.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.placemark.R
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.placemark.models.PlacemarkModel


class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

  val placemarks = ArrayList<PlacemarkModel>()
  var placemark = PlacemarkModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)

    btnAdd.setOnClickListener {
      placemark.title = placemarkTitle.text.toString()
      if (placemark.title.isNotEmpty()) {
        info("add Button Pressed - Input Text: $placemark")
        placemarks.add(placemark)
        info("Placemark added to List")
        info("Current List: $placemarks")
      } else {
        toast("Please Enter a title")
      }
    }
  }
}