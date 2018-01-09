package kz.mycrm.android.ui.main.journal

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.repository.JournalRepository
import kz.mycrm.android.repository.DivisionRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by Nurbek Kabylbay on 23.11.2017.
 */
class JournalViewModel: ViewModel() {

    private var journalRepository = JournalRepository(AppExecutors)
    private var userRepository = DivisionRepository(AppExecutors)

    private var divisionId = 0
    private var staffId = intArrayOf(0)
    private lateinit var date: String

    private val toRefresh = MutableLiveData<Boolean>()

    private val orderList: LiveData<Resource<List<Order>>>

    init {
        orderList = Transformations.switchMap(toRefresh) { _ -> requestJournal(date, divisionId, staffId)}
    }

    fun refreshData(date: String, divisionId: Int, staffId: IntArray) {
        this.divisionId = divisionId
        this.date = date
        this.staffId = staffId
        toRefresh.value = null
    }

    fun startRefresh() {
        toRefresh.value = null
    }

    fun getOrderLis(): LiveData<Resource<List<Order>>> {
        return orderList
    }

    private fun requestJournal(date: String, divisionId: Int, staffId: IntArray): LiveData<Resource<List<Order>>> {
        return journalRepository.requestJournal(date, divisionId, staffId)
    }

    fun getDivisionById(id: Int): Division {
        return userRepository.getDivisionById(id)
    }

    fun getDivisions(): List<Division> {
        return userRepository.getDivisionsList()
    }
}