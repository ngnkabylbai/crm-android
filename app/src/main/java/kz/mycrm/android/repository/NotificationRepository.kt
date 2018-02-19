package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Notification
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by NKabylbay on 12/14/2017.
 */
class NotificationRepository (private var appExecutors: AppExecutors?){

    fun requestAllOrders(staffId: String, thisDate:String): LiveData<Resource<List<Notification>>> {
        return object : NetworkBoundResource<List<Notification>, List<Notification>> (appExecutors!!) {

            override fun saveCallResult(item: List<Notification>) {
                TODO("Not implemented")
            }

            override fun shouldFetch(data: List<Notification>?): Boolean {
                return false
            }

            override fun loadFromDb(): LiveData<List<Notification>> {
                return MycrmApp.database.NotificationDao().getAllNotificationsByStaffId(staffId)
            }

            override fun createCall(): LiveData<ApiResponse<List<Notification>>> {
                TODO("Not implemented")
            }
        }.asLiveData()
    }

    fun sendNotificationKey(key: String) {
        ApiUtils.getNotificationService()
                .sendNotificationKey(key).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                Logger.debug("Notification key set successful: ${response?.message()}")
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                sendNotificationKey(key) // while will not be successful
            }
        })
    }
}
