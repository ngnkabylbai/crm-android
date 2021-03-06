package kz.mycrm.android.remote

import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.StaffJournal
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */
interface JournalService {

    @GET("v2/user/schedule")
    fun requestJournal(@Query("date")date: String,
                        @Query("division_id")id: String, @Query("staff")staff: IntArray, @Query("expand")expand: String): LiveData<ApiResponse<List<StaffJournal>>>

//    @GET("order")
//    fun requestOrders(@Query("access-token")token:String, @Query("company_customer_id")compCustId:Int,
//                      @Query("staff_id")staffId:Int, @Query("type")type:Int, @Query("status")status:Int): LiveData<ApiResponse<List<Order>>>
}