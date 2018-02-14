package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Service
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by Nurbek Kabylbay on 14.02.2018.
 */
class OrderRepository(private val appExecutors: AppExecutors) {

    fun requestServiceList(divisionId: Int, staffId: Int): LiveData<Resource<List<Service>>> {
        return object : NetworkBoundResource<List<Service>, List<Service>>(appExecutors) {
            override fun saveCallResult(item: List<Service>) {
                MycrmApp.database.ServiceDao().insertServiceList(item)
            }

            override fun shouldFetch(data: List<Service>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Service>> {
                return MycrmApp.database.ServiceDao().getServices()
            }

            override fun createCall(): LiveData<ApiResponse<List<Service>>> {
                return ApiUtils.getOrderService().requestServiceList(divisionId, staffId)
            }
        }.asLiveData()
    }

    fun getOrderById(id: String): Order {
        return MycrmApp.database.OrderDao().getOrderById(id)
    }
}