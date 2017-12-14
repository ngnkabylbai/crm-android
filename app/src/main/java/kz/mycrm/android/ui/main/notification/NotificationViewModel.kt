package kz.mycrm.android.ui.main.notification

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.repository.NotificationRepository
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by lab on 11/25/17.
 */
class NotificationViewModel: ViewModel() {

    private val notificationRepostitory = NotificationRepository(AppExecutors)
    private val tokenRepostiory = TokenRepository(AppExecutors)

//    TODO: Change type to Notification
    fun getToDaysNotifications(accessToken: String, staffId: String): LiveData<Resource<List<Order>>> {
//        return MycrmApp.database.OrderDao().getAllOrdersList()
        return notificationRepostitory.requestAllOrders(accessToken, staffId)
    }

    fun getToken(): LiveData<Token> {
        return MycrmApp.database.TokenDao().getToken()
    }

    fun getDivisionById(divisionId: Int): LiveData<Division> {
        return MycrmApp.database.DivisionDao().getDivisionById(divisionId)
    }
}