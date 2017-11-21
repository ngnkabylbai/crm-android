package kz.mycrm.android.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import kz.mycrm.android.db.dao.DivisionDao
import kz.mycrm.android.db.dao.TokenDao
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.db.entity.User

/**
 * Created by NKabylbay on 11/11/2017.
 */
@Database(entities = arrayOf(Token::class, User::class, Division::class), version =  2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun TokenDao(): TokenDao
    abstract fun DivisionDao(): DivisionDao
}