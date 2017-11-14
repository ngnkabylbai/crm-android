package kz.mycrm.android.util

import kz.mycrm.android.remote.RetrofitClient
import kz.mycrm.android.remote.TokenService

/**
 * Created by NKabylbay on 11/11/2017.
 */
object ApiUtils {

    private val baseTokenUrl: String = "https://private-anon-55d088c7b0-apimycrm.apiary-proxy.com/v2/"

    fun getTokenService() : TokenService {
        return RetrofitClient.getClient(baseTokenUrl).create(TokenService::class.java)
    }
}