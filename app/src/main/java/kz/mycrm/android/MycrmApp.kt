package kz.mycrm.android

import android.app.Application
import android.arch.persistence.room.Room
import kz.mycrm.android.db.AppDatabase

/**
 * Created by NKabylbay on 11/11/2017.
 */
class MycrmApp : Application() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "MycrmDatabase")
                .allowMainThreadQueries() // TODO: remove after debug
                .fallbackToDestructiveMigration()
                .build()
    }
}