package kz.mycrm.android.ui.main.info

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Service
import kz.mycrm.android.db.entity.UpdateOrder
import kz.mycrm.android.repository.OrderRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Constants
import kz.mycrm.android.util.Resource

/**
 * Created by asset on 12/11/17.
 */
class InfoViewModel: ViewModel() {

    private val orderRepository = OrderRepository(AppExecutors)

    private var updatedOrder: LiveData<Resource<Order>>
    private lateinit var updatingOrder: UpdateOrder
    private lateinit var staffId: String

    private val toUpdate = MutableLiveData<Boolean>()


    init {
        updatedOrder = Transformations.switchMap(toUpdate) {_-> requestUpdateOrder(staffId, updatingOrder)}
    }

    fun updateOrder(staffId: String, updatingOrder: UpdateOrder) {
        this.staffId = staffId
        this.updatingOrder = updatingOrder
        this.toUpdate.value = null
    }
    fun getUpdatedOrder(): LiveData<Resource<Order>> {
        return updatedOrder
    }

    fun getOrderById(id: String): LiveData<Order> {
        return MycrmApp.database.OrderDao().getOrderLiveDataById(id)
    }

    private fun requestUpdateOrder(orderId: String, body: UpdateOrder): LiveData<Resource<Order>> {
        return orderRepository.updateOrder(orderId, body)
    }

    fun getTestOrder(): Order {
        val mOrder = Order("1","2017-12-21 09:00:00","2017-12-21 09:50:00",
                Constants.orderDateTimeFormat.parse("2017-12-21 09:00:00"),
                "Иванов Иван Иванович", "+7 456 312 21 45")

        val list = ArrayList<Service>()
            list.add(Service("1","Первичный осмотр", "450"))
            list.add(Service("2","Вторичный осмотр", "450"))
            list.add(Service("3","Еще один осмотр", "450"))
        mOrder.services = list

        return mOrder
    }
}