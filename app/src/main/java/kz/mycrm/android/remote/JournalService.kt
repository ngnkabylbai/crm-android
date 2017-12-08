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

    @GET("user/schedule")
    fun requestJournal(@Query("access-token")token: String, @Query("date")date: String,
                        @Query("division_id")id: Int, @Query("staff")staff: IntArray): LiveData<ApiResponse<StaffJournal>>
}