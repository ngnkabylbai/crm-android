package kz.mycrm.android.ui.main.notification

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Order

/**
 * Created by lab on 11/25/17.
 */
class NotificationViewModel: ViewModel() {

//    TODO: Change type to Notification
    fun getToDaysNotifications(datetime: String): LiveData<List<Order>> {
//        return MycrmApp.database.OrderDao().getOrdersByDate(datetime)
        return MycrmApp.database.OrderDao().getAllOrdersList()
    }
}