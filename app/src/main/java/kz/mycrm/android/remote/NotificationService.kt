package kz.mycrm.android.remote

import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Nurbek Kabylbay on 12.02.2018.
 */
interface NotificationService {

    @POST("v2/user/push/key")
    @FormUrlEncoded
    fun sendNotificationKey(@Field("key")key: String): Call<ResponseBody>

}