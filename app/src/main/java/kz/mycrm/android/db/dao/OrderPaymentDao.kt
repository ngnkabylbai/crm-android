package kz.mycrm.android.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import kz.mycrm.android.db.entity.OrderPayment
import kz.mycrm.android.db.entity.StaffJournal

/**
 * Created by asset on 12/8/17.
 */
@Dao
interface OrderPaymentDao {

    @Insert
    fun insertOrderPayment(orderpayment: OrderPayment)

    @Update
    fun updateOrderPayment(orderpayment: OrderPayment)

    @Query("SELECT * FROM orderpayment LIMIT 1")
    fun getOrderPayment(): LiveData<OrderPayment>

    @Query("SELECT * FROM orderpayment")
    fun getOrderPayments():LiveData<List<OrderPayment>>
}