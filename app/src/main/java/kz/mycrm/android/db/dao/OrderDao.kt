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

    @Update
    fun updateOrder(order: Order)

    @Query("SELECT * FROM mOrder")
    fun getAllOrdersList(): LiveData<List<Order>>

    @Insert(onConflict = REPLACE)
    fun insertOrder(order: Order)

    @Query("SELECT * FROM mOrder WHERE datetime LIKE :arg0 AND division_id = :arg1 AND staff_id = :arg2")
    fun getOrders(date: String, divisionId: Int, staffId:Int):LiveData<List<Order>>

    @Query("SELECT * FROM mOrder WHERE datetime LIKE :arg0")
    fun getOrdersByDate(date: String): LiveData<List<Order>>

    @Query("SELECT * FROM mOrder WHERE order_id = :arg0")
    fun getOrderById(id: String): LiveData<Order>

    @Query("SELECT * FROM mOrder WHERE datetime>Date(:arg0) ORDER BY datetime")
    fun getAfter(date: String): LiveData<List<Order>>

    @Query("DELETE FROM mOrder")
    fun nukeOrder()
}