package kz.mycrm.android.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import kz.mycrm.android.db.dao.*
import kz.mycrm.android.db.entity.*
import kz.mycrm.android.util.MyTypeConverters

/**
 * Created by NKabylbay on 11/11/2017.
 */
@Database(entities = arrayOf(Token::class, Division::class,
        Customer::class, Order::class, OrderPayment::class, Service::class), version =  16)
@TypeConverters(MyTypeConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun TokenDao(): TokenDao
    abstract fun DivisionDao(): DivisionDao
    abstract fun CustomerDao(): CustomerDao
    abstract fun OrderDao(): OrderDao
    abstract fun OrderPaymentDao(): OrderPaymentDao
    abstract fun ServiceDao(): ServiceDao
    abstract fun NukeDao() : NukeDao
}