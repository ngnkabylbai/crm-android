package kz.mycrm.android.util

import kz.mycrm.android.remote.*

/**
 * Created by NKabylbay on 11/11/2017.
 */
object ApiUtils {

    private var tokenService: TokenService? = null
    private var userService: UserService? = null
    private var journalService: JournalService? = null
    private var orderService: OrderService? = null
    private var appVersionService: AppVersionService? = null
    private var renewPasswordService: RenewPasswordService? = null
    private var notificationService: NotificationService? = null

    fun getTokenService() : TokenService {
        return tokenService ?: RetrofitClient.getClient().create(TokenService::class.java)
    }

    fun getUserService(): UserService {
        return userService ?: RetrofitClient.getClient().create(UserService::class.java)
    }

    fun getJournalService(): JournalService {
        return journalService ?: RetrofitClient.getClient().create(JournalService::class.java)
    }

    fun getOrderService(): OrderService {
        return orderService ?: RetrofitClient.getClient().create(OrderService::class.java)
    }

    fun getAppVersionService(): AppVersionService {
        return appVersionService ?: RetrofitClient.getClient().create(AppVersionService::class.java)
    }

    fun getRenewPasswordService(): RenewPasswordService {
        return renewPasswordService ?: RetrofitClient.getClient().create(RenewPasswordService::class.java)
    }

    fun getNotificationService(): NotificationService {
        return notificationService ?: RetrofitClient.getClient().create(NotificationService::class.java)
    }
}