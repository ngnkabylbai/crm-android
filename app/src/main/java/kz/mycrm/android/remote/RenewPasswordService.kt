package kz.mycrm.android.remote

import android.arch.lifecycle.LiveData
import retrofit2.Call
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Token
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Nurbek Kabylbay on 05.02.2018.
 */
interface RenewPasswordService {

    @POST("v2/user/forgot-password")
    @FormUrlEncoded
    fun requestSmsCode(@Field("username")phone: String): Call<ResponseBody>

    @POST("v2/user/change-password")
    @FormUrlEncoded
    fun requestRenewPassword(@Field("username")phone: String, @Field("code")code: String,
                             @Field("password")password: String): LiveData<ApiResponse<Token>>

}