package com.example.placemark.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import com.example.placemark.R
import com.example.placemark.main.MainApp
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import com.example.placemark.models.PlacemarkModel


class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

  var placemark = PlacemarkModel()
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)

    app = application as MainApp

    btnAdd.setOnClickListener {
      placemark.title = placemarkTitle.text.toString()
      placemark.description = description.text.toString()
      if (placemark.title.isNotEmpty()) {
        app.placemarks.add(placemark.copy())
        info("add Button Pressed: ${placemark}")
        for (i in app.placemarks.indices) {
          info("Placemark[$i]:${app.placemarks[i]}")
        }
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      } else {
        toast("Please Enter a title")
      }
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
}