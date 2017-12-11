package kz.mycrm.android.ui.main.info

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.repository.JournalRepository
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.repository.UserRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by asset on 12/11/17.
 */
class InfoViewModel: ViewModel() {

    fun requestOrder(id: String): LiveData<Order> {
        return MycrmApp.database.OrderDao().getOrderById(id)
    }
}