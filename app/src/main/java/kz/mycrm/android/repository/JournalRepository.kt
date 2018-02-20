package kz.mycrm.android.repository

import kz.mycrm.android.db.entity.StaffJournal
import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Notification
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.util.*

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */
class JournalRepository(private var appExecutors: AppExecutors) {

//    The function requests for a journal by a date, but returns a list of Orders
//    The journal is used only to retrieve the Orders
//    Fetched journal's Orders are inserted into the database
    fun requestJournal(date: String, divisionId:Int, staffId: IntArray)
        : LiveData<Resource<List<Order>>> {

        return object : NetworkBoundResource<List<Order>, List<StaffJournal>>(appExecutors) {
                override fun saveCallResult(item: List<StaffJournal>) {
                    for(s in item) {
                        s.orders?.let {
                            for(order in s.orders!!)
                                if(order.status == Constants.orderStatusEnabled
                                        || order.status == Constants.orderStatusFinished
                                        || order.status == Constants.orderStatusCanceled) {
                                    MycrmApp.database.OrderDao().insertOrder(order)
                                    MycrmApp.database.ServiceDao().insertServiceList(order.services)

                                    if(order.status == Constants.orderStatusEnabled) {
                                        val notification = Notification()
                                        notification.staffId = staffId[0].toString()
                                        notification.divisionId = divisionId.toString()
                                        notification.orderId = order.id
                                        notification.title = order.customerName

                                        var body = ""
                                        for(i in 0 until order.services.size) {
                                            body += order.services[i].serviceName
                                            if(i != order.services.size - 1)
                                                body += ", "
                                        }

                                        notification.body = body


                                                notification.datetime = order.datetime.time

                                        MycrmApp.database.NotificationDao().insertNotification(notification)
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
                    return getOrders(date, divisionId, staffId[0],
                            Constants.orderStatusEnabled, Constants.orderStatusFinished)
                }

                override fun createCall(): LiveData<ApiResponse<List<StaffJournal>>> {
                    return ApiUtils.getJournalService().requestJournal(date, divisionId, staffId, "services")
                }
            }.asLiveData()
    }

    fun nukeTables() {
        MycrmApp.database.OrderDao().nukeOrder()
    }

    private fun getOrders(date:String, divisionId: Int, staffId:Int, statusEnabled: Int,
                          statusFinished: Int): LiveData<List<Order>> {
        return MycrmApp.database.OrderDao().getOrders("%$date%", divisionId, staffId, statusEnabled, statusFinished)
    }
}
