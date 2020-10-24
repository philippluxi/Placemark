package com.example.placemark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    info("Placemark Activity started...")
    setContentView(R.layout.activity_placemark)

    btnAdd.setOnClickListener {
      val placemarkTitle = placemarkTitle.text.toString()
      if (placemarkTitle.isNotEmpty()) {
        info("add Button Pressed: $placemarkTitle")
      } else {
        toast("Please Enter a title")
      }
    }
  }
}