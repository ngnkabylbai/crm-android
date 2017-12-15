package kz.mycrm.android.remote

import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Order
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by NKabylbay on 12/14/2017.
 */
interface OrderService {

    @GET("order")
    fun requestAllOrders(@Query("staff_id")staffId: String,
                         @Query("expand")expand:String): LiveData<ApiResponse<List<Order>>>

}