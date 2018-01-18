package kz.mycrm.android.ui.main.info

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Service
import kz.mycrm.android.util.Constants

/**
 * Created by asset on 12/11/17.
 */
class InfoViewModel: ViewModel() {

    fun requestOrder(id: String): LiveData<Order> {
        return MycrmApp.database.OrderDao().getOrderById(id)
    }

    fun getTestOrder(): Order {
        val mOrder = Order()
        mOrder.id = "1"
        mOrder.start = "2017-12-21 09:00:00"
        mOrder.end = "2017-12-21 09:50:00"
        mOrder.datetime = Constants.orderDateTimeFormat.parse(mOrder.start)
        mOrder.customerName = "Artur Abdalimov"
        mOrder.customerPhone = "+7 456 312 21 45"

        val service = Service()
        service.id = "1"
        service.serviceName = "Первичный осмотр"
        service.price = "450"
        mOrder.services = arrayListOf(service)

        return mOrder
    }
}