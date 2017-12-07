package kz.mycrm.android.repository

import kz.mycrm.android.db.entity.StaffJournal
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource
import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.util.ApiUtils

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */
class JournalRepository(private var appExecutors: AppExecutors) {

//    TODO: Finish it
    fun requestJournal(token: String, date: String,id: Int, staff: IntArray)
        : LiveData<Resource<StaffJournal>> {

    return object : NetworkBoundResource<StaffJournal, StaffJournal>(appExecutors) {
            override fun saveCallResult(item: StaffJournal) {

            }

            override fun shouldFetch(data: StaffJournal?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<StaffJournal> {
                return getJournal()
            }

            override fun createCall(): LiveData<ApiResponse<StaffJournal>> {
                return ApiUtils.getJournalService().requestJournal(token, date, id, staff)
            }
        }.asLiveData()
    }

    private fun getJournal(): LiveData<StaffJournal> {
        return MycrmApp.database.JournalDao().getJournal()
    }

}