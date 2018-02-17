package kz.mycrm.android.repository

import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.util.AppExecutors

/**
 * Created by Nurbek Kabylbay on 14.02.2018.
 */
class OrderRepository(private val appExecutors: AppExecutors) {

    fun getOrderById(id: String): Order {
        return MycrmApp.database.OrderDao().getOrderById(id)
    }
}