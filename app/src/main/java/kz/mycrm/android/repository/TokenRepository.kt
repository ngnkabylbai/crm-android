package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Entity
import android.util.Log
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.application.MycrmApp
import kz.mycrm.android.database.entity.Division
import kz.mycrm.android.database.entity.Token
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by NKabylbay on 11/11/2017.
 */
class TokenRepository {

    private var appExecutors: AppExecutors

    constructor(appExecutors: AppExecutors) {
        this.appExecutors = appExecutors
    }

//    TODO: a bug
    fun hasToken() : LiveData<Boolean> {
        val value = MutableLiveData<Boolean>()
        value.value = MycrmApp.database?.TokenDao()?.getToken() != null
        return value
    }

    fun getToken(): LiveData<Token> {
        return MycrmApp.database?.TokenDao()?.getToken() ?: MutableLiveData<Token>()
    }

    fun requestToken(login: String, password: String): LiveData<Resource<Token>> {
        return object : NetworkBoundResource<Token, Token>(appExecutors) {
            override fun processResponse(response: ApiResponse<Token>?): Token {
                return super.processResponse(response)
            }

            override fun saveCallResult(item: Token) {

            }

            override fun shouldFetch(data: Token?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Token> {
                return getToken()
            }

            override fun createCall(): LiveData<ApiResponse<Token>> {
        //        val paramObject = JSONObject()
        //        paramObject.put("username", login)
        //        paramObject.put("password", password)

                val paramObject = "{\"username\": \"+7 701 381 71 15\",  \"password\": \"password\"}"

                return ApiUtils.getTokenService().requestToken(login, password)
            }
        }.asLiveData()
    }
}
