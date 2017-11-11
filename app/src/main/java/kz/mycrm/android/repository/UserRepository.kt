package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import kz.mycrm.android.database.entity.User
import kz.mycrm.android.util.Resource

/**
 * Created by NKabylbay on 11/11/2017.
 */
class UserRepository {

    fun getUserData(): LiveData<Resource<User>>? {

        return null
    }
}