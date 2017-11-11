package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kz.mycrm.android.application.MycrmApp
import kz.mycrm.android.database.entity.Token

/**
 * Created by NKabylbay on 11/11/2017.
 */
class TokenRepository {

    fun hasToken() : LiveData<Boolean> {
        val value = MutableLiveData<Boolean>()
        value.value = MycrmApp.database?.TokenDao()?.getToken() != null
        return value
    }

    fun getToken(login: String, password: String): LiveData<Token>? {
        return MycrmApp.database?.TokenDao()?.getToken()
    }
}