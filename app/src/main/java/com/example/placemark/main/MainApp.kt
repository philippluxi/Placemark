package com.example.placemark.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import com.example.placemark.models.json.PlacemarkJSONStore
import com.example.placemark.models.PlacemarkStore
import com.example.placemark.models.firebase.PlacemarkFireStore
import com.example.placemark.room.PlacemarkStoreRoom

class MainApp : Application(), AnkoLogger {

    lateinit var placemarks: PlacemarkStore

    override fun onCreate() {
        super.onCreate()
        // placemarks = PlacemarkJSONStore(applicationContext)
        // placemarks = PlacemarkStoreRoom(applicationContext)
        placemarks = PlacemarkFireStore(applicationContext)
        info("Placemark started")
    }
}