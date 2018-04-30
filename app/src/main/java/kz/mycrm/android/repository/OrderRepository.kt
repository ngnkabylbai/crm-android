package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.UpdateOrder
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by Nurbek Kabylbay on 14.02.2018.
 */
class OrderRepository(private val appExecutors: AppExecutors) {

    fun updateOrder(orderId: String, body: UpdateOrder): LiveData<Resource<Order>> {
        return object : NetworkBoundResource<Order, Order>(appExecutors) {
            override fun saveCallResult(item: Order) {
                MycrmApp.database.OrderDao().insertOrder(item)
            }

            override fun shouldFetch(data: Order?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Order> {
                return MycrmApp.database.OrderDao().getOrderLiveDataById("")
            }

            override fun createCall(): LiveData<ApiResponse<Order>> {
                return ApiUtils.getOrderService().updateOrder(orderId, body, "services")
            }

        }.asLiveData()
    }

    fun requestOrder(orderId: String): LiveData<Resource<Order>> {
        return object : NetworkBoundResource<Order, Order>(appExecutors) {
            override fun saveCallResult(item: Order) {
                MycrmApp.database.OrderDao().insertOrder(item)
            }

            override fun shouldFetch(data: Order?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Order> {
                return MycrmApp.database.OrderDao().getOrderLiveDataById(orderId)
            }

            override fun createCall(): LiveData<ApiResponse<Order>> {
                return ApiUtils.getOrderService().requestOrder(orderId,"services")
            }

        }.asLiveData()
    }

    fun deleteOrdersByDate(datetime: String) {
        MycrmApp.database.OrderDao().deleteOrdersByDate(datetime)
    }

    fun getOrderById(id: String): Order {
        return MycrmApp.database.OrderDao().getOrderById(id)
    }
}
