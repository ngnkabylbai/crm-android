package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.NotificationToken
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

/**
 * Created by NKabylbay on 12/14/2017.
 */
class NotificationRepository (private var appExecutors: AppExecutors?){

    fun requestAllOrders(staffId: String, thisDate:String): LiveData<Resource<List<Order>>> {
        return object : NetworkBoundResource<List<Order>, List<Order>> (appExecutors!!) {

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

    private fun getNotificationToken(): NotificationToken {
        return MycrmApp.database.NotificationDao().getNotificationToken()
    }

    fun updateNotificationToken(newNotificationToken: String) {
        MycrmApp.database.NotificationDao().nukeNotificationToken()
        MycrmApp.database.NotificationDao().insertNotificationToken(NotificationToken(newNotificationToken))
    }

    fun sendNotificationKey() {
        val key = getNotificationToken().notificationToken

        ApiUtils.getNotificationService()
                .sendNotificationKey(key).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                Logger.debug("Notification key set successful: ${response?.message()}")
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                sendNotificationKey() // while will not be successful
            }
        })
    }
}
