package kz.mycrm.android.ui.view.journal

import kz.mycrm.android.db.entity.Order

/**
 * Created by Nurbek Kabylbay on 21.01.2018.
 */
interface OrderEventClickListener {
    fun onOrderEventClicked(order: Order) {}
}