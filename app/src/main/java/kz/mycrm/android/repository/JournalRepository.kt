package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.StaffJournal
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Constants
import kz.mycrm.android.util.Resource
import java.util.*

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */
class JournalRepository(private var appExecutors: AppExecutors) {

    private val notificationRepository = NotificationRepository(appExecutors)
    private val orderRepository = OrderRepository(appExecutors)

//    The function requests for a journal by a date, but returns a list of Orders
//    The journal is used only to retrieve the Orders
//    Fetched journal's Orders are inserted into the database
    fun requestJournal(date: String, divisionId: String, staffId: IntArray)
        : LiveData<Resource<List<Order>>> {

        return object : NetworkBoundResource<List<Order>, List<StaffJournal>>(appExecutors) {
                override fun saveCallResult(item: List<StaffJournal>) {
                    for(s in item) {
                        s.orders?.let {
                            orderRepository.deleteOrdersByDate(date)
                            for(order in s.orders!!) {
                                if (order.status == Constants.orderStatusEnabled
                                        || order.status == Constants.orderStatusFinished
                                        || order.status == Constants.orderStatusCanceled) {

                                    MycrmApp.database.OrderDao().insertOrder(order)
                                    MycrmApp.database.ServiceDao().insertServiceList(order.services)

                                    if (order.status == Constants.orderStatusEnabled
                                            && order.datetime.after(Date())) {
                                        notificationRepository.addNewNotification(order, divisionId, staffId[0].toString())
                                    }
                                }
                            }
                        }
                    }
                }

                override fun shouldFetch(data: List<Order>?): Boolean {
                    return true
                }

                override fun loadFromDb(): LiveData<List<Order>> {
    //                TODO: Complete order
                    return getOrders(date, divisionId, staffId[0].toString(), Constants.orderStatusEnabled,
                            Constants.orderStatusFinished, Constants.orderStatusCanceled)
                }

                override fun createCall(): LiveData<ApiResponse<List<StaffJournal>>> {
                    return ApiUtils.getJournalService().requestJournal(date, divisionId, staffId, "services")
                }
            }.asLiveData()
    }

    fun nukeTables() {
        MycrmApp.database.OrderDao().nukeOrder()
    }

    private fun getOrders(date:String, divisionId: String, staffId:String, statusEnabled: Int,
                          statusFinished: Int, statusCanceled: Int): LiveData<List<Order>> {
        return MycrmApp.database.OrderDao().getOrders("%$date%", divisionId, staffId,
                statusEnabled, statusFinished, statusCanceled)
    }
}
