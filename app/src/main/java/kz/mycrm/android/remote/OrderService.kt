package kz.mycrm.android.remote

import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Service
import kz.mycrm.android.db.entity.UpdateOrder
import retrofit2.http.*

/**
 * Created by NKabylbay on 12/14/2017.
 */
interface OrderService {

    @GET("v2/order")
    fun requestAllOrders(@Query("staff_id")staffId: String,
                         @Query("expand")expand:String): LiveData<ApiResponse<List<Order>>>

    @GET("/v2/division/{division_id}/staff/{staff_id}/service?")
    fun requestServiceList(@Path("division_id")divisionId: Int,
                           @Path("staff_id")staffId: Int): LiveData<ApiResponse<List<Service>>>


    @PUT("/v2/order/{order_id}")
    fun updateOrder(@Path("order_id")orderId: String, @Body body: UpdateOrder,
                    @Query("expand")expand:String): LiveData<ApiResponse<Order>>

    @GET("v2/order/{order_id}")
    fun requestOrder(@Path("order_id")orderId: String,
                     @Query("expand")expand:String): LiveData<ApiResponse<Order>>

}
