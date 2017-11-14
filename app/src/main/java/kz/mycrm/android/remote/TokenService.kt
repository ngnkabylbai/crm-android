package kz.mycrm.android.remote

import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Token
import retrofit2.http.*

/**
 * Created by NKabylbay on 11/11/2017.
 */
interface TokenService {

    @POST("user/login")
    @FormUrlEncoded
    fun requestToken(@Field("username") login:String, @Field("password")password:String): LiveData<ApiResponse<Token>>

}