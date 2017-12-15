package kz.mycrm.android.ui.main.notification

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.repository.NotificationRepository
import kz.mycrm.android.repository.UserRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by lab on 11/25/17.
 */
class NotificationViewModel: ViewModel() {

    private val notificationRepository = NotificationRepository(AppExecutors)
    private val userRepository = UserRepository(AppExecutors)

    private val orderList: LiveData<Resource<List<Order>>>
    private val toFetch = MutableLiveData<Boolean>()
    private var divisionId = 0

    init {
        orderList = Transformations.switchMap(toFetch) { _-> getToDaysNotifications()}
    }

//    TODO: Change type to Notification
    fun getToDaysNotifications(): LiveData<Resource<List<Order>>> {
        val staffId = userRepository.getDivisionById(divisionId).staff?.id ?: 0

        return notificationRepository.requestAllOrders(staffId.toString())
    }

    fun setDivisionId(divisionId: Int) {
        this.divisionId = divisionId
    }

    fun startRefresh() {
        toFetch.value = null
    }
}