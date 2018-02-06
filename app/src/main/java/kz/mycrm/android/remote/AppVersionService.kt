package kz.mycrm.android.remote

import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.AppVersion
import retrofit2.http.GET

/**
 * Created by Nurbek Kabylbay on 06.02.2018.
 */
interface AppVersionService {

    @GET("v2/app/android")
    fun requestAppVersion(): LiveData<ApiResponse<AppVersion>>

}