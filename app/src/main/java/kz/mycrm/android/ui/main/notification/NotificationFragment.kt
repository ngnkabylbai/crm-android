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
import kz.mycrm.android.ui.main.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lab on 11/25/17.
 */
class NotificationFragment: Fragment() {
    private lateinit var viewModel: NotificationViewModel
    private lateinit var sharedViewModel: MainViewModel

    private lateinit var currentNotificationAdapter : CurrentNotificationAdapter
    private lateinit var pastNotificationAdapter : PastNotificationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NotificationViewModel::class.java)

        currentNotificationAdapter = CurrentNotificationAdapter(activity)
        pastNotificationAdapter =  PastNotificationAdapter(activity)

        rvCurrentNotifications.adapter = currentNotificationAdapter
        rvCurrentNotifications.layoutManager = LinearLayoutManager(activity)

        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = Date()
        val dateTime = format.format(today)

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

        viewModel.getToDaysNotifications(dateTime).observe(this, Observer { notificationList->
            if(notificationList != null && notificationList.isNotEmpty()) {
                for(notification in notificationList)
                    currentNotificationAdapter.add(notification)
            }

        })

    }
}

