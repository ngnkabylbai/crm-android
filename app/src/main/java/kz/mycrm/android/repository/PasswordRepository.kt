package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.OtpInfo
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Constants.millisToRefreshOtp
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by Nurbek Kabylbay on 05.02.2018.
 */
class PasswordRepository(private var appExecutors: AppExecutors) {


    fun renewPassword(phone: String, password: String, code: String): LiveData<Resource<Token>> {
        return object : NetworkBoundResource<Token, Token> (appExecutors) {
            override fun saveCallResult(item: Token) {
                Logger.debug("Renew password new token:" + item.token)
                val tokenCount = MycrmApp.database.TokenDao().getCount()

                if(tokenCount == 0) {
                    MycrmApp.database.TokenDao().insertToken(item)
                } else {
                    MycrmApp.database.TokenDao().updateToken(item)
                }
            }

            override fun shouldFetch(data: Token?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Token> {
                return MycrmApp.database.TokenDao().getTokenLiveData()
            }

            override fun createCall(): LiveData<ApiResponse<Token>> {
                return ApiUtils.getRenewPasswordService().requestRenewPassword(phone, code, password)
            }

        }.asLiveData()
    }

    fun requestSmsCode(phone: String) {
        val isNeededToRefresh = (getMillisToRefreshOtp(phone) == millisToRefreshOtp)

        if(isNeededToRefresh) {
            val newOtpInfo = OtpInfo(phone, Date().time)
            MycrmApp.database.OtpInfoDao().insertOtpInfo(newOtpInfo)

        ApiUtils.getRenewPasswordService().requestSmsCode(phone).enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                Logger.api("Request SMS: SUCCESS")
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Logger.api("Request SMS: FAILURE")
            }
        })
        }

    }

    fun requestCodeValidation(phone: String, code: String): LiveData<Resource<Array<String>>> {
        return object : NetworkBoundResource<Array<String>,Array<String>> (appExecutors) {
            override fun saveCallResult(item: Array<String>) {
                Logger.debug("Code validation response:" + item)
            }

            override fun shouldFetch(data: Array<String>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Array<String>> {
                return MycrmApp.database.DummyObjectDao().getDummyString()
            }

            override fun createCall(): LiveData<ApiResponse<Array<String>>> {
                return ApiUtils.getRenewPasswordService().requestCodeValidation(phone, code)
            }

        }.asLiveData()
    }

    fun getMillisToRefreshOtp(phone: String): Long {
        val nowMillis = Date().time
        val lastOtpInfo = MycrmApp.database.OtpInfoDao().getLastInfo(phone)

        return if(lastOtpInfo == null) {
                    millisToRefreshOtp
                } else if((nowMillis - lastOtpInfo.lastSentTime) >= millisToRefreshOtp) {
                    millisToRefreshOtp
                } else {
                    millisToRefreshOtp - (nowMillis - lastOtpInfo.lastSentTime)
                }
    }
}
