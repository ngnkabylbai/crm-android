package kz.mycrm.android.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import kz.mycrm.android.database.dao.TokenDao
import kz.mycrm.android.database.entity.Token
import kz.mycrm.android.database.entity.User

/**
 * Created by NKabylbay on 11/11/2017.
 */
@Database(entities = arrayOf(Token::class, User::class), version =  1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun TokenDao(): TokenDao
}