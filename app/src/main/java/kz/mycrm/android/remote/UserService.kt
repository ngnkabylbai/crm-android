package kz.mycrm.android.remote

import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Division
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by NKabylbay on 11/16/2017.
 */
interface UserService {

    @GET("user/division")
    fun requestDivisions(@Query("token")token:String, @Query("expand")expand:String?): LiveData<ApiResponse<List<Division>>>
}