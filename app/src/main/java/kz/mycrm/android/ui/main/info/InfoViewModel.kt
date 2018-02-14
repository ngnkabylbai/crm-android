package kz.mycrm.android.ui.main.info

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Service
import kz.mycrm.android.repository.OrderRepository
import kz.mycrm.android.util.Constants

/**
 * Created by asset on 12/11/17.
 */
class InfoViewModel: ViewModel() {

    fun requestOrder(id: String): LiveData<Order> {
        return MycrmApp.database.OrderDao().getOrderLiveDataById(id)
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