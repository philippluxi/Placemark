package com.example.placemark.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.placemark.models.PlacemarkModel

@Database(entities = arrayOf(PlacemarkModel::class), version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun placemarkDao(): PlacemarkDao
}