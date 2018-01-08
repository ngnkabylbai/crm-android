package kz.mycrm.android.repository

import kz.mycrm.android.db.entity.StaffJournal
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource
import android.arch.lifecycle.LiveData
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.Logger

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
                            for(o in s.orders!!)
                                MycrmApp.database.OrderDao().insertOrder(o)
                        }
                    }
                }

                override fun shouldFetch(data: List<Order>?): Boolean {
                    return true
                }

                override fun loadFromDb(): LiveData<List<Order>> {
    //                TODO: Complete order
                    return getOrders(date, divisionId, staffId[0])
                }

                override fun createCall(): LiveData<ApiResponse<List<StaffJournal>>> {
                    return ApiUtils.getJournalService().requestJournal(date, divisionId, staffId, "services")
                }
            }.asLiveData()
    }

//    fun requestOrders(token:String, compCustId:Int, staffId:Int, type:Int, status:Int): LiveData<Resource<List<Order>>> {
//        return object : NetworkBoundResource<List<Order>, List<Order>>(appExecutors){
//            override fun saveCallResult(item: List<Order>) {
//
//            }
//
//            override fun shouldFetch(data: List<Order>?): Boolean {
//                return true
//            }
//
//            override fun loadFromDb(): LiveData<List<Order>> {
//                return getOrders(token, compCustId, staffId, type, status)
//            }
//
//            override fun createCall(): LiveData<ApiResponse<List<Order>>> {
//                return ApiUtils.getJournalService().requestOrders(token, compCustId, staffId, type, status)
//            }
//        }.asLiveData()
//
//    }

    fun nukeTables() {
        MycrmApp.database.NukeDao().nukeOrder()
    }

    private fun getOrders(date:String, divisionId: Int, staffId:Int): LiveData<List<Order>> {
        return MycrmApp.database.OrderDao().getOrders("%$date%", divisionId, staffId)
    }

}