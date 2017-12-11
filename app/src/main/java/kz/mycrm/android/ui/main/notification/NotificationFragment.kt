package kz.mycrm.android.ui.main.notification

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import kz.mycrm.android.R
import kz.mycrm.android.ui.main.MainViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

/**
 * Created by lab on 11/25/17.
 */
class NotificationFragment: Fragment() {
    private lateinit var viewModel: NotificationViewModel
    private lateinit var sharedViewModel: MainViewModel

    @BindView(R.id.rvCurrentNotifications)
    lateinit var rvCurrentNotifications: RecyclerView
//    @BindView(R.id.rvPastNotifications)
//    lateinit var rvPastNotifications: RecyclerView

    private lateinit var currentNotificationAdapter : CurrentNotificationAdapter
    private lateinit var pastNotificationAdapter : PastNotificationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButterKnife.bind(this, view)

        viewModel = ViewModelProviders.of(this).get(NotificationViewModel::class.java)

        currentNotificationAdapter = CurrentNotificationAdapter(activity)
        pastNotificationAdapter =  PastNotificationAdapter(activity)

        rvCurrentNotifications.adapter = currentNotificationAdapter
        rvCurrentNotifications.layoutManager = LinearLayoutManager(activity)

        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = Date()
        val dateTime = format.format(today)

        viewModel.getToDaysNotifications(dateTime).observe(this, Observer { notificationList->
            if(notificationList != null && notificationList.isNotEmpty()) {
                for(notification in notificationList)
                    currentNotificationAdapter.add(notification)
            }

        })

    }
}

