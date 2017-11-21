package kz.mycrm.android.ui.division

import android.arch.lifecycle.*
import android.util.Log
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.repository.UserRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource

/**
 * Created by lab on 11/18/17.
 */
class DivisionViewModel : ViewModel(){

    private var userRepositoty = UserRepository(AppExecutors)

    fun requestUserDivisions(accessToken:String, expand: String?): LiveData<Resource<List<Division>>> {
        Logger.debug("token" + accessToken + " expand " + expand)
        return userRepositoty.getUserDivisions(accessToken, expand)
    }

    fun unpackResource(data:LiveData<Resource<List<Division>>>): LiveData<List<Division>> {
        return Transformations.map(data, {divisionList-> divisionList.data})
    }

    fun getDivisions(): LiveData<List<Division>> {
        return userRepositoty.getDivisions()
    }
}