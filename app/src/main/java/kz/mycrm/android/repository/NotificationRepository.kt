package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by NKabylbay on 12/14/2017.
 */
class NotificationRepository (private var appExecutors: AppExecutors){

    fun requestAllOrders(staffId: String, thisDate:String): LiveData<Resource<List<Order>>> {
        return object : NetworkBoundResource<List<Order>, List<Order>> (appExecutors) {

            override fun saveCallResult(item: List<Order>) {
                for(order in item)
                    MycrmApp.database.OrderDao().insertOrder(order)
            }

            override fun shouldFetch(data: List<Order>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Order>> {
                return MycrmApp.database.OrderDao().getAfter(thisDate)
            }

            override fun createCall(): LiveData<ApiResponse<List<Order>>> {
                return ApiUtils.getOrderService().requestAllOrders(staffId, "services")
            }
        }.asLiveData()
    }
}