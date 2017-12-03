package kz.mycrm.android.util

import kz.mycrm.android.remote.RetrofitClient
import kz.mycrm.android.remote.TokenService
import kz.mycrm.android.remote.UserService

/**
 * Created by NKabylbay on 11/11/2017.
 */
object ApiUtils {

    private val baseUrl: String = "http://test.api.mycrm.kz/v2/"

    fun getTokenService() : TokenService {
        return RetrofitClient.getClient(baseUrl).create(TokenService::class.java)
    }

    fun getUserService(): UserService {
        return RetrofitClient.getClient(baseUrl).create(UserService::class.java)
    }
}