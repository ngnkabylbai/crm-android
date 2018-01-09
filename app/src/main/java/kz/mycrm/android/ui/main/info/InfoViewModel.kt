package kz.mycrm.android.ui.main.info

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Order

/**
 * Created by asset on 12/11/17.
 */
class InfoViewModel: ViewModel() {

    fun requestOrder(id: String): LiveData<Order> {
        return MycrmApp.database.OrderDao().getOrderById(id)
    }
}