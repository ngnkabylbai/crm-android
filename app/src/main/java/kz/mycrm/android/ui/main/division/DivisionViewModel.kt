package kz.mycrm.android.ui.main.division

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.repository.DivisionRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource

/**
 * Created by lab on 11/18/17.
 */
class DivisionViewModel : ViewModel(){

    private var userRepository = DivisionRepository(AppExecutors)
    private var divisionList: LiveData<Resource<List<Division>>>
    private val toRefresh =  MutableLiveData<Boolean>()

    init {
        divisionList = Transformations.switchMap(toRefresh) { _ -> loadUserDivisions()}
    }

    private fun loadUserDivisions(): LiveData<Resource<List<Division>>> {
        Logger.debug("Loading UserDivisions...")
        return userRepository.requestUserDivisionList()
    }

    fun getResourceDivisionsList(): LiveData<Resource<List<Division>>> {
        return divisionList
    }

    fun startRefresh() {
        toRefresh.value = null
    }
}