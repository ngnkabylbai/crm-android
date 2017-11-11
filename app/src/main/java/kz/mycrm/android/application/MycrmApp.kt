package kz.mycrm.android.application

import android.app.Application
import android.arch.persistence.room.Room
import kz.mycrm.android.database.AppDatabase

/**
 * Created by NKabylbay on 11/11/2017.
 */
class MycrmApp : Application() {

    companion object {
        var database: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "MycrmDatabase").build()
    }
}