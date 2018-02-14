package kz.mycrm.android.ui.main.info.service

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Service
import kz.mycrm.android.repository.OrderRepository
import kz.mycrm.android.repository.ServiceRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by Nurbek Kabylbay on 14.02.2018.
 */
class AddServiceViewModel: ViewModel() {

    private val orderRepository = OrderRepository(AppExecutors)
    private val serviceRepository = ServiceRepository()

    fun requestServiceList(divisionId: Int, staffId: Int): LiveData<Resource<List<Service>>> {
        return orderRepository.requestServiceList(divisionId, staffId)
    }

    fun getOrderById(id: String): Order {
        return orderRepository.getOrderById(id)
    }

    fun getServiceArrayList(servicesId: ArrayList<String>): ArrayList<Service> {
        val result = ArrayList<Service>()
        servicesId.map { result.add(serviceRepository.getServiceById(it)) }

        return result
    }
}