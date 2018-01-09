package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by NKabylbay on 11/11/2017.
 */
class DivisionRepository(private var appExecutors: AppExecutors) {

    fun requestUserDivisionList(): LiveData<Resource<List<Division>>> {
        return object : NetworkBoundResource<List<Division>, List<Division>>(appExecutors) {
            override fun saveCallResult(item: List<Division>) {
                MycrmApp.database.DivisionDao().insertDivisionsList(item)
            }

            override fun shouldFetch(data: List<Division>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Division>> {
                return getDivisionsListLiveData()
            }

            override fun createCall(): LiveData<ApiResponse<List<Division>>> {
                return ApiUtils.getUserService().requestDivisions("self-staff")
            }
        }.asLiveData()
    }

    fun getDivisionsListLiveData(): LiveData<List<Division>> {
        return MycrmApp.database.DivisionDao().getDivisionsListLiveData()
    }

    fun getDivisionsList(): List<Division> {
        return MycrmApp.database.DivisionDao().getDivisionsList()
    }

    fun getDivisionById(id: Int): Division {
        return MycrmApp.database.DivisionDao().getDivisionById(id)
    }

    fun nukeTables() {
        MycrmApp.database.DivisionDao().nukeDivision()
    }
}