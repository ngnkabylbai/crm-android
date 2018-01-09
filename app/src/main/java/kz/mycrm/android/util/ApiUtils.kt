package kz.mycrm.android.util

import kz.mycrm.android.BuildConfig
import kz.mycrm.android.remote.*

/**
 * Created by NKabylbay on 11/11/2017.
 */
object ApiUtils {

    private val baseUrl: String = BuildConfig.DAR_API
    private var tokenService: TokenService? = null
    private var userService: UserService? = null
    private var journalService: JournalService? = null
    private var orderService: OrderService? = null

    fun getTokenService() : TokenService {
        return tokenService ?: RetrofitClient.getClient(baseUrl).create(TokenService::class.java)
    }

    fun getUserService(): UserService {
        return userService ?: RetrofitClient.getClient(baseUrl).create(UserService::class.java)
    }

    fun getJournalService(): JournalService {
        return journalService ?: RetrofitClient.getClient(baseUrl).create(JournalService::class.java)
    }

    fun getOrderService(): OrderService {
        return orderService ?: RetrofitClient.getClient(baseUrl).create(OrderService::class.java)
    }
}