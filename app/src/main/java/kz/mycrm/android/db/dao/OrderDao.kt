package kz.mycrm.android.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import kz.mycrm.android.db.entity.Order

/**
 * Created by asset on 12/8/17.
 */
@Dao
interface OrderDao {

    @Insert(onConflict = REPLACE)
    fun insertOrders(orders: List<Order>)

    @Update
    fun updateOrder(order: Order)

    @Query("SELECT * FROM mOrder")
    fun getOrder(): LiveData<List<Order>>

    @Query("SELECT * FROM mOrder")
    fun getOrders():LiveData<List<Order>>
}