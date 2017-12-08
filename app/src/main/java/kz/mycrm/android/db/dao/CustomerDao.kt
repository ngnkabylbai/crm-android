package kz.mycrm.android.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import kz.mycrm.android.db.entity.Customer
import kz.mycrm.android.db.entity.StaffJournal

/**
 * Created by asset on 12/8/17.
 */
@Dao
interface CustomerDao {

    @Insert
    fun insertCustomer(customer: Customer)

    @Update
    fun updateCustomer(customer: Customer)

    @Query("SELECT * FROM customer LIMIT 1")
    fun getCustomer(): LiveData<Customer>
}