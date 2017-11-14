package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.application.MycrmApp
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource


/**
 * Created by NKabylbay on 11/11/2017.
 */
class TokenRepository(private var appExecutors: AppExecutors) {

    fun hasToken(): LiveData<Boolean> {
        val value = MutableLiveData<Boolean>()
        value.value = getToken().value != null
        return value
    }

    fun getToken(): LiveData<Token> {
        return MycrmApp.database?.TokenDao()?.getToken() ?: MutableLiveData<Token>()
    }

    fun requestToken(login: String, password: String): LiveData<Resource<Token>> {
        return object : NetworkBoundResource<Token, Token>(appExecutors) {
            override fun saveCallResult(item: Token) {
//                TODO: cant get this datacls
                MycrmApp.database?.TokenDao()?.insertToken(item)
            }

            override fun shouldFetch(data: Token?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Token> {
                return getToken()
            }

            override fun createCall(): LiveData<ApiResponse<Token>> {
                return ApiUtils.getTokenService().requestToken(login, password)
            }
        }.asLiveData()
    }
}
