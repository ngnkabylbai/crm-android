package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.User
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by NKabylbay on 11/11/2017.
 */
class UserRepository(private var appExecutors: AppExecutors) {

    fun getUserData(): LiveData<Resource<User>>? {
        return null
    }

    fun getUserDivisions(accessToken:String): LiveData<Resource<List<Division>>> {
        return object : NetworkBoundResource<List<Division>, List<Division>>(appExecutors) {
            override fun saveCallResult(item: List<Division>) {
                if ((getCount() == 0)){
                    insertDivision(item)
                } else{
                    updateDivision(item)
                }
            }

            override fun shouldFetch(data: List<Division>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Division>> {
                return getDivisions()
            }

            override fun createCall(): LiveData<ApiResponse<List<Division>>> {
                return ApiUtils.getUserService().requestDivisions(accessToken, "self_staff")
            }
        }.asLiveData()
    }

    fun getDivisions(): LiveData<List<Division>> {
        return MycrmApp.database.DivisionDao().getDivisions()
    }

    fun insertDivision(divisions: List<Division>) {
        MycrmApp.database.DivisionDao().insertDivision(divisions)
    }

    fun updateDivision(divisions: List<Division>) {
        MycrmApp.database.DivisionDao().updateDivision(divisions)
    }

    fun getCount() : Int {
        return MycrmApp.database.DivisionDao().getCount()
    }
}