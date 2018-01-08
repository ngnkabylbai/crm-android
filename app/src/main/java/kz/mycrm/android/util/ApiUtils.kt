package kz.mycrm.android.util

import kz.mycrm.android.BuildConfig
import kz.mycrm.android.remote.*

/**
 * Created by NKabylbay on 11/11/2017.
 */
object ApiUtils {

    private val baseUrl: String = BuildConfig.DAR_API

    fun getTokenService() : TokenService {
        return RetrofitClient.getClient(baseUrl).create(TokenService::class.java)
    }

    fun getUserService(): UserService {
        return RetrofitClient.getClient(baseUrl).create(UserService::class.java)
    }

    fun getJournalService(): JournalService {
        return RetrofitClient.getClient(baseUrl).create(JournalService::class.java)
    }

    fun getOrderService(): OrderService {
        return RetrofitClient.getClient(baseUrl).create(OrderService::class.java)
    }
}