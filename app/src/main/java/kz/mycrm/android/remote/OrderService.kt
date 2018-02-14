package kz.mycrm.android.remote

import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Service
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by NKabylbay on 12/14/2017.
 */
interface OrderService {

    @GET("v2/order")
    fun requestAllOrders(@Query("staff_id")staffId: String,
                         @Query("expand")expand:String): LiveData<ApiResponse<List<Order>>>

    @GET("/v2/division/{divisionId}/staff/{staffId}/service?")
    fun requestServiceList(@Path("divisionId")divisionId: Int,
                           @Path("staffId")staffId: Int): LiveData<ApiResponse<List<Service>>>

}