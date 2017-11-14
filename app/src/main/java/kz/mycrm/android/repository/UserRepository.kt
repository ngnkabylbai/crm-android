package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.User
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

    fun getUserDivision(): LiveData<Resource<Division>>? {
        return object : NetworkBoundResource<Division, Division>(appExecutors) {
            override fun saveCallResult(item: Division) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun shouldFetch(data: Division?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun loadFromDb(): LiveData<Division> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createCall(): LiveData<ApiResponse<Division>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }.asLiveData()
    }
}