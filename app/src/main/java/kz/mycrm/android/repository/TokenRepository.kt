package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource


/**
 * Created by NKabylbay on 11/11/2017.
 */
class TokenRepository(private var appExecutors: AppExecutors) {

    fun requestToken(login: String, password: String): LiveData<Resource<Token>> {
        return object : NetworkBoundResource<Token, Token>(appExecutors) {
            override fun saveCallResult(item: Token) {
                if((getCount() == 0))
                    insertToken(item)
                else
                    updateToken(item)
            }

            override fun shouldFetch(data: Token?): Boolean {
                return (getCount() == 0)
            }

            override fun loadFromDb(): LiveData<Token> {
                return getToken()
            }

            override fun createCall(): LiveData<ApiResponse<Token>> {
                return ApiUtils.getTokenService().requestToken(login, password)
            }
        }.asLiveData()
    }

    fun getToken(): LiveData<Token> {
        return MycrmApp.database.TokenDao().getTokenLiveData()
    }

    private fun insertToken(token: Token) {
        MycrmApp.database.TokenDao().insertToken(token)
    }

    private fun updateToken(token: Token) {
        MycrmApp.database.TokenDao().updateToken(token)
    }

    private fun getCount() : Int {
        return MycrmApp.database.TokenDao().getCount()
    }

    fun nukeTables() {
        MycrmApp.database.TokenDao().nukeToken()
    }
}
