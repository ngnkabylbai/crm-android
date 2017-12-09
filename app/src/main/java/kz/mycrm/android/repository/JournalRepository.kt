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
    fun requestJournal(token: String, date: String, divisionId:Int, staffId: IntArray)
        : LiveData<Resource<List<Order>>> {

        return object : NetworkBoundResource<List<Order>, List<StaffJournal>>(appExecutors) {
                override fun saveCallResult(item: List<StaffJournal>) {
                    for(s in item) {
                        MycrmApp.database.OrderDao().insertOrders(s.orders)
                    }
                }

                override fun shouldFetch(data: List<Order>?): Boolean {
                    return true
                }

                override fun loadFromDb(): LiveData<List<Order>> {
    //                TODO: Complete order
                    return getOrders(date,1,1,1,1)
                }

                override fun createCall(): LiveData<ApiResponse<List<StaffJournal>>> {
                    return ApiUtils.getJournalService().requestJournal(token, date, divisionId, staffId)
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

    private fun getOrders(datetime:String, compCustId:Int, staffId:Int, type:Int, status:Int): LiveData<List<Order>> {
        return MycrmApp.database.OrderDao().getOrders()
    }
}