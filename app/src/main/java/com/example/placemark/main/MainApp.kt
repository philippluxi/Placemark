package com.example.placemark.main

import android.app.Application
import com.example.placemark.models.PlacemarkMemStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import com.example.placemark.models.PlacemarkModel

class MainApp : Application(), AnkoLogger {

    val placemarks = PlacemarkMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Placemark started")
    }
}