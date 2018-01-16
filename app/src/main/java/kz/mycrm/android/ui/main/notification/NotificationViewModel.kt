package kz.mycrm.android.ui.main.notification

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.repository.NotificationRepository
import kz.mycrm.android.repository.DivisionRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Constants
import kz.mycrm.android.util.Resource
import java.util.*

/**
 * Created by lab on 11/25/17.
 */
class NotificationViewModel: ViewModel() {

    private val notificationRepository = NotificationRepository(AppExecutors)
    private val userRepository = DivisionRepository(AppExecutors)

    private val orderList: LiveData<Resource<List<Order>>>
    private val toRefresh = MutableLiveData<Boolean>()
    private var divisionId = 0
    private lateinit var staffId:String
    private val today = Constants.orderDateTimeFormat.format(Date())


    init {
        orderList = Transformations.switchMap(toRefresh) { _-> requestNotificationList()}
    }

//    TODO: Change type to Notification
    fun getToDaysNotifications(): LiveData<Resource<List<Order>>> {
        return orderList
    }

    private fun requestNotificationList(): LiveData<Resource<List<Order>>> {
        return notificationRepository.requestAllOrders(staffId, today)
    }

    fun setDivisionId(divisionId: Int) {
        this.divisionId = divisionId
        this.staffId = userRepository.getDivisionById(divisionId).staff?.id ?: ""
    }

    fun startRefresh() {
        toRefresh.value = null
    }
}