package com.circuitqueest.app

import android.app.Application
import androidx.room.Room
import com.circuitqueest.app.data.db.AppDatabase

class CircuitQueestApp : Application() {

    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "circuitqueest-db"
        ).build()
    }
}
