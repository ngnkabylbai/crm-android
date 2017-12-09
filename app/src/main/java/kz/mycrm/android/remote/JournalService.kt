package kz.mycrm.android.remote

import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.StaffJournal
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */
interface JournalService {

    @GET("user/schedule")
    fun requestJournal(@Query("access-token")token: String, @Query("date")date: String,
                        @Query("division_id")id: Int, @Query("staff")staff: IntArray): LiveData<ApiResponse<List<StaffJournal>>>

//    @GET("order")
//    fun requestOrders(@Query("access-token")token:String, @Query("company_customer_id")compCustId:Int,
//                      @Query("staff_id")staffId:Int, @Query("type")type:Int, @Query("status")status:Int): LiveData<ApiResponse<List<Order>>>
}