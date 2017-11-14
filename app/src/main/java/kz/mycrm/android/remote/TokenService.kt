package kz.mycrm.android.remote

import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.database.entity.Token
import kz.mycrm.android.util.Resource
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by NKabylbay on 11/11/2017.
 */
interface TokenService {

    @POST("user/login")
    fun requestToken(@Body body: String): LiveData<ApiResponse<Token>>

}