package kz.mycrm.android.ui.division

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.repository.UserRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by lab on 11/18/17.
 */
class DivisionViewModel : ViewModel(){

    private var userRepositoty = UserRepository(AppExecutors)

    fun requestUserDivisions(accessToken:String, expand: String?): LiveData<Resource<List<Division>>> {
        return userRepositoty.getUserDivisions(accessToken, expand)
    }
}