package kz.mycrm.android.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import kz.mycrm.android.db.entity.*

/**
 * Created by Nurbek Kabylbay on 06.12.2017.
 */
@Dao
interface JournalDao {

    @Insert
    fun insertJournal(journal: StaffJournal)

    @Update
    fun updateJournal(journal: StaffJournal)

    @Query("SELECT * FROM journal LIMIT 1")
    fun getJournal(): LiveData<StaffJournal>

    @Query("SELECT * FROM mOrder")
    fun getOrders():LiveData<List<Order>>

    @Query("SELECT * FROM customer")
    fun getCustomers():LiveData<List<Customer>>

    @Query("SELECT * FROM orderpayment")
    fun getOrderPayments():LiveData<List<OrderPayment>>

    @Query("SELECT * FROM service")
    fun getServices():LiveData<List<Service>>

}