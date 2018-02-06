package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Nurbek Kabylbay on 05.02.2018.
 */
class PasswordRepository(private var appExecutors: AppExecutors) {


    fun renewPassword(phone: String, password: String, code: String): LiveData<Resource<Token>> {
        return object : NetworkBoundResource<Token, Token> (appExecutors) {
            override fun saveCallResult(item: Token) {
                Logger.debug("Renew password new token:" + item.token)
            }

            override fun shouldFetch(data: Token?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Token> {
                return getToken()
            }

            override fun createCall(): LiveData<ApiResponse<Token>> {
                return ApiUtils.getRenewPasswordService().requestRenewPassword(phone, code, password)
            }

        }.asLiveData()
    }

    fun requestSmsCode(phone: String) {
        ApiUtils.getRenewPasswordService().requestSmsCode(phone).enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                Logger.api("Request SMS: SUCCESS")
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Logger.api("Request SMS: FAILURE")
            }
        })
    }

    private fun getToken(): LiveData<Token> {
        return MycrmApp.database.TokenDao().getTokenLiveData()
    }
}
