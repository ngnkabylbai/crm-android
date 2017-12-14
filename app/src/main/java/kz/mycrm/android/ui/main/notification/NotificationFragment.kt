package kz.mycrm.android.ui.main.notification

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_notification.*
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.ui.main.MainViewModel
import kz.mycrm.android.util.Status
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lab on 11/25/17.
 */
class NotificationFragment : Fragment() {
    private lateinit var viewModel: NotificationViewModel
    private lateinit var sharedViewModel: MainViewModel

    private lateinit var currentNotificationAdapter: CurrentNotificationAdapter
    private lateinit var pastNotificationAdapter: PastNotificationAdapter

    private val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NotificationViewModel::class.java)

        currentNotificationAdapter = CurrentNotificationAdapter(activity)
        pastNotificationAdapter = PastNotificationAdapter(activity)

        rvCurrentNotifications.adapter = currentNotificationAdapter
        rvCurrentNotifications.layoutManager = LinearLayoutManager(activity)

//        val order = Order()
//        val customer = Customer()
//        customer.name = "Максат"
//        customer.lastname = "Нуржаусын"
//        val service = Service()
//        service.serviceName = "повторное посещение"
//
//        order.customer = customer
//        order.services = listOf(service)
//        order.start = "xxxx-xx-xx 15:00:00"
//
//        currentNotificationAdapter.add(order)

//        viewModel.getToDaysNotifications(dateTime).observe(this, Observer { notificationList->
//            if(notificationList != null && notificationList.isNotEmpty()) {
//                for(notification in notificationList)
//                    currentNotificationAdapter.add(notification)
//            }
//
//        })

        val divisionId = arguments.getInt("division_id")
        var orderArrayList = ArrayList<Order>()

        viewModel.getToken().observe(this, Observer { token ->
            viewModel.getDivisionById(divisionId).observe(activity, Observer { division ->
                viewModel.getToDaysNotifications(token!!.token, division?.staff!!.id).observe(this, Observer { resourceList ->
                    if (resourceList != null && resourceList.status != Status.ERROR) {
                        val orderList = resourceList.data
                        if (orderList != null) {
                            orderArrayList.clear()
                            for (order in orderList) {
                                orderArrayList.add(order)
                            }
                            orderArrayList = getFilteredList(orderArrayList)
                            currentNotificationAdapter.setListAndNotify(orderArrayList)
                        }
                    }
                })
            })
        })
    }

    private fun getFilteredList(list: ArrayList<Order>): ArrayList<Order> {
        val today = Date()
        val result = ArrayList<Order>()
        for(order in list) {
            val date = format.parse(order.datetime)
            if(date.after(today)) {
                result.add(order)
            }
        }

        return result
    }
}

