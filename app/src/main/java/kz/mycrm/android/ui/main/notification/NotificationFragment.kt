package kz.mycrm.android.ui.main.notification

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_notification.*
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.util.Status
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lab on 11/25/17.
 */
class NotificationFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var viewModel: NotificationViewModel

    private lateinit var currentNotificationAdapter: CurrentNotificationAdapter

    private val datetimeFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private var divisionId = 0



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NotificationViewModel::class.java)

        currentNotificationAdapter = CurrentNotificationAdapter(activity)

        rvCurrentNotifications.adapter = currentNotificationAdapter
        rvCurrentNotifications.layoutManager = LinearLayoutManager(activity)
        rvCurrentNotifications.setHasFixedSize(true)

        divisionId = arguments.getInt("division_id")
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

        swipeRefreshContainer.setOnRefreshListener(this)
        swipeRefreshContainer.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)

        swipeRefreshContainer.post {
            loadNotifications()
        }
    }

    override fun onRefresh() {
        loadNotifications()
    }

    private fun loadNotifications() {
        swipeRefreshContainer.isRefreshing = true

        var orderArrayList = ArrayList<Order>()
        viewModel.getToken().observe(this, Observer { token ->
            viewModel.getDivisionLiveDataById(divisionId).observe(activity, Observer { division ->
                if(token?.token != null && division?.staff != null)
                    viewModel.getToDaysNotifications(token.token, division.staff!!.id).observe(this, Observer { resourceList ->
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

                        if(resourceList?.status == Status.SUCCESS || resourceList?.status == Status.SUCCESS)
                            swipeRefreshContainer.isRefreshing = false
                    })
            })
        })
    }

    private fun getFilteredList(list: ArrayList<Order>): ArrayList<Order> {
        val todayStr = datetimeFormat.format(Date())
        val today = datetimeFormat.parse(todayStr)
        val result = ArrayList<Order>()
        for(order in list) {
            val date = datetimeFormat.parse(order.datetime)
            if(date.equals(today) || date.after(today)) {
                result.add(order)
            }
        }

        Collections.sort(result)
        return result
    }
}

