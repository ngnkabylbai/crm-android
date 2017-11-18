package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.User
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by NKabylbay on 11/11/2017.
 */
class UserRepository {

    private var appExecutors: AppExecutors

    constructor(appExecutors: AppExecutors) {
        this.appExecutors = appExecutors
    }

    fun getUserData(): LiveData<Resource<User>>? {
        return null
    }

    fun getUserDivisions(accessToken:String, expand:String?): LiveData<Resource<List<Division>>> {
        return object : NetworkBoundResource<List<Division>, List<Division>>(appExecutors) {
            override fun saveCallResult(item: List<Division>) {

            }

            override fun shouldFetch(data: List<Division>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Division>> {
                return MutableLiveData<List<Division>>()
            }

            override fun createCall(): LiveData<ApiResponse<List<Division>>> {
                return ApiUtils.getUserService().requestDivisions(accessToken, expand)
            }
        }.asLiveData()
    }
}